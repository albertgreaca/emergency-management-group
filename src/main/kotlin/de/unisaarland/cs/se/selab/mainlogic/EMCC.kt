package de.unisaarland.cs.se.selab.mainlogic

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.bases.Hospital
import de.unisaarland.cs.se.selab.bases.PoliceStation
import de.unisaarland.cs.se.selab.bases.departments.AmbulanceDepartment
import de.unisaarland.cs.se.selab.bases.departments.FireDepartment
import de.unisaarland.cs.se.selab.bases.departments.PoliceDepartment
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.emergencies.EmergencyUtils
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.resources.Request
import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckLadder
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle

/**
 * This is the class responsible for simulating the different phases of a tick
 */
object EMCC {
    var policeDepartment: PoliceDepartment? = null
    var fireDepartment: FireDepartment? = null
    var ambulanceDepartment: AmbulanceDepartment? = null
    val startingEmergencies: MutableList<Emergency> = mutableListOf()
    val handledEmergencies: MutableList<Emergency> = mutableListOf()
    val resolvedOrFailedEmergencies: MutableList<Emergency> = mutableListOf()
    val activeEvents: MutableList<Event> = mutableListOf()
    val startingEvents: MutableList<Event> = mutableListOf()
    var nextRequestId: Int = 1
    val requests: MutableList<Request> = mutableListOf()

    // Global Counters
    var i = 0
    var k = 0
    var l = 0

    const val divisor = 300

    /**
     * notifies all observers about new emergencies, this initiates the emergency phase
     */
    fun notifyObservers() {
        startingEmergencies.sortBy { it.id }
        for (em in startingEmergencies) {
            if (em.firsttick) {
                Simulation.statistics.ongoingEmergencies++
                Simulation.statistics.receivedEmergencies++
                when (em.type) {
                    EmergencyType.CRIME -> policeDepartment?.update(em)
                    EmergencyType.FIRE, EmergencyType.ACCIDENT -> fireDepartment?.update(em)
                    else -> ambulanceDepartment?.update(em)
                }
                em.firsttick = false
            }
        }
    }

    /**
     * adds a department to the list of observers
     */

    /**
     * adds an emergency to the startingEmergencies list
     */
    fun addStartingEmergency(em: Emergency) {
        startingEmergencies.add(em)
    }

    /**
     * orders the starting emergencies by severity, then by ID
     */
    fun orderEmergencies() {
        startingEmergencies.sortWith(compareByDescending<Emergency> { it.severity }.thenBy { it.id })
    }

    /**
     * Updates Next Bases of all bases
     */
    fun updatenextBases() {
        policeDepartment?.updatenextBases()
        ambulanceDepartment?.updatenextBases()
        fireDepartment?.updatenextBases()
    }

    /**
     * allocates assets for each starting emergency
     */
    fun allocateAssets() {
        // iterate over
        k = 0
        while (k < startingEmergencies.size) {
            // get the base assigned to the emergency
            val em = startingEmergencies[k]
            val emBase = requireNotNull(em.base)

            // base tries to allocate resources for emergency
            emBase.requestResources(em)

            var ok: Boolean = true
            // if needed, base tries to reallocate resources from other emergencies
            if (!em.currentResources.isEmpty()) {
                val newre = emBase.reallocateResources(em)
                if (!newre.isEqual(em.currentResources)) {
                    ok = false
                    em.currentResources = newre
                }
            }
            k++

            // if there are remaining resources after reallocating, a request to the next base has to be created
            if (!em.currentResources.isEmpty()) {
                makeRequest(em)
            }
        }
    }

    /**
     * creates a request for each type of base with the resources that are still missing after allocation/reallocation
     */
    private fun makeRequest(em: Emergency) {
        val emBase = requireNotNull(em.base)
        val policeResources = em.currentResources.filterPoliceResources()
        val fireResources = em.currentResources.filterFireResources()
        val ambulanceResources = em.currentResources.filterAmbulanceResources()

        val nextPoliceBase = if (policeResources.isEmpty()) null else emBase.getNextPoliceBase(emBase)
        val nextFireBase = if (fireResources.isEmpty()) null else emBase.getNextFireBase(emBase)
        val nextAmbulanceBase = if (ambulanceResources.isEmpty()) null else emBase.getNextHospital(emBase)
        // make a request for the missing police resources

        val bases = (
            mutableListOf(
                nextPoliceBase,
                nextFireBase,
                nextAmbulanceBase
            ).filter { !(it == null) } as List<Base>
            ).sortedBy { it.id }

        for (b in bases) {
            emBase.makeRequest(em, b)
        }
    }

    /**
     * handles all requests made in the allocation phase of the current tick
     */
    fun processRequests() {
        while (requests.isNotEmpty()) {
            i = 0
            while (i < requests.size) {
                // try to allocate all requested resources
                requests[i].processingBase.requestResources(requests[i].emergency)

                // if we have resources left, make another request to the next closest base
                if (!requests[i].emergency.currentResources.isEmpty()) {
                    delegateRequest(requests[i])
                } else {
                    requests.removeAt(i)
                }
            }
        }
    }

    /**
     * creates a new request to the next closest base if the current request could not be handled by the current base
     */
    private fun delegateRequest(request: Request) {
        when (request.processingBase) {
            is PoliceStation -> {
                // calculate the next closest police base and make a request to this base
                val nextBase = request.getRequestingBase().getNextPoliceBase(request.processingBase)
                if (nextBase != null) {
                    requests.remove(request)
                    request.getRequestingBase().makeRequest(request.emergency, nextBase)
                } else {
                    requests.remove(request)
                    Logger.logRequestFailed(request.emergency.id)
                }
            }

            is Hospital -> {
                // calculate the next closest ambulance base and make a request to this base
                val nextBase = request.getRequestingBase().getNextHospital(request.processingBase)
                if (nextBase != null) {
                    requests.remove(request)
                    request.getRequestingBase().makeRequest(request.emergency, nextBase)
                } else {
                    requests.remove(request)
                    Logger.logRequestFailed(request.emergency.id)
                }
            }

            else -> {
                // calculate the next closest fire base and make a request to this base
                val nextBase = request.getRequestingBase().getNextFireBase(request.processingBase)
                if (nextBase != null) {
                    requests.remove(request)
                    request.getRequestingBase().makeRequest(request.emergency, nextBase)
                } else {
                    requests.remove(request)
                    Logger.logRequestFailed(request.emergency.id)
                }
            }
        }
    }

    /**
     * updates the state of all driving assets
     */
    fun updateAssets() {
        val newlyArrivedAssets: MutableList<Pair<Int, Int>> = mutableListOf()
        val toremove: MutableList<Vehicle> = mutableListOf()
        for (em in Simulation.emergencies) {
            for (vec in em.assignedVehicles) {
                moveAndLogAsset(vec, newlyArrivedAssets, toremove)
            }
            em.assignedVehicles.removeAll(toremove)
        }
        newlyArrivedAssets.sortBy { it.first }
        for ((aid, vid) in newlyArrivedAssets) {
            Logger.logAssetArrival(aid, vid)
        }
    }

    private fun moveAndLogAsset(
        vec: Vehicle,
        newlyArrivedAssets: MutableList<Pair<Int, Int>>,
        toremove: MutableList<Vehicle>
    ) {
        // move each vehicle that is currently driving
        if (vec.position == null) {
            return
        }
        if (requireNotNull(vec.position).arrivalTicks == 0 && !requireNotNull(vec.position).startedThisTick) {
            return
        }
        requireNotNull(vec.position).startedThisTickZero = requireNotNull(vec.position).startedThisTick
        vec.move()
        // if a vehicle arrived at an emergency after moving, log it
        if (!requireNotNull(vec.position).isDrivingBack && requireNotNull(vec.position).arrivalTicks == 0) {
            newlyArrivedAssets.add(
                Pair(
                    vec.id,
                    requireNotNull(
                        requireNotNull(vec.position).vertexList[requireNotNull(vec.position).vertexList.size - 1]
                    ).id
                )
            )
        }
        // if a vehicle arrived back at its base after moving, log it
        if (requireNotNull(vec.position).isDrivingBack && requireNotNull(vec.position).arrivalTicks == 0) {
            newlyArrivedAssets.add(
                Pair(
                    vec.id,
                    requireNotNull(
                        requireNotNull(vec.position).vertexList[requireNotNull(vec.position).vertexList.size - 1]
                    ).id
                )
            )
            toremove.add(vec)
            helperfunction(vec)
            vec.targetEmergency = null
            vec.position = null
        }
    }

    private fun helperfunction(vec: Vehicle) {
        if (vec is FireTruckLadder) {
            vec.available = true
        }
        if (vec !is FireTruckWater && vec !is Ambulance && vec !is PoliceCar) {
            vec.available = true
        }
        if (vec is FireTruckWater) {
            vec.baseWaitingTicks = (vec.waterCapacity - vec.waterTransported) / divisor + 1
            if ((vec.waterCapacity - vec.waterTransported) % divisor != 0) {
                vec.baseWaitingTicks++
            }
        }
        if (vec is Ambulance && vec.patientOnBoard) {
            vec.baseWaitingTicks = 2
        }
        if (vec is PoliceCar && vec.transportedCriminals > 0) {
            vec.baseWaitingTicks = 3
        }
    }

    /**
     * updates the state of all emergencies
     */
    fun updateEmergencies() {
        // update all emergencies who allocated all resources in this tick
        val listtoremove = mutableListOf<Emergency>()
        for (em in startingEmergencies) {
            if (em.currentResources.isEmpty()) {
                listtoremove.add(em)
                handledEmergencies.add(em)
            }
        }
        startingEmergencies.removeAll(listtoremove)
        // update all emergencies whose handling started in this tick
        updateHandlingStartedEmergencies()
        // update all emergencies that were resolved in this tick
        updateResolvedEmergencies()
        // update all emergencies that failed in this tick
        updateFailedEmergencies()
        // if all assets assigned to an emergency returned to their bases, we don't need to track it anymore
        var l = 0
        while (l < resolvedOrFailedEmergencies.size) {
            val em = resolvedOrFailedEmergencies[l]
            if (em.assignedVehicles.isEmpty()) {
                resolvedOrFailedEmergencies.remove(em)
            } else {
                l++
            }
        }
        for (em in resolvedOrFailedEmergencies) {
            if (em.assignedVehicles.isEmpty()) {
                resolvedOrFailedEmergencies.remove(em)
            }
        }
    }

    /**
     * updates the state of all emergencies that were started handling
     * an emergency starts handling if:
     * -it has not started handling before
     * -all needed resources are allocated
     * -all allocated resources have arrived
     */
    private fun updateHandlingStartedEmergencies() {
        val newlyHandlingStartedEmergencies: MutableList<Emergency> = mutableListOf()
        for (em in handledEmergencies) {
            if (em.handlingStarted || !em.currentResources.isEmpty()) continue
            var allArrived = true
            for (vec in em.assignedVehicles) {
                allArrived =
                    if (requireNotNull(vec.position).arrivalTicks == 0 &&
                        !requireNotNull(vec.position?.startedThisTick)
                    ) {
                        allArrived
                    } else {
                        false
                    }
            }
            if (allArrived) {
                em.handlingStarted = true
                newlyHandlingStartedEmergencies.add(em)
                val emUt = EmergencyUtils()
                emUt.updateResourcesOfAssets(em)
                emUt.updateBaseWaitingTicksOfAssets(em)
            }
        }
        newlyHandlingStartedEmergencies.sortBy { it.id }
        for (emergency in newlyHandlingStartedEmergencies) {
            Logger.logEmergencyHandlingStart(emergency.id)
        }
    }

    /**
     * updates the state of all emergencies that were resolved in this tick
     * an emergency is newly resolved if:
     * -it is not already in the list of resolved emergencies
     * -its handling already started
     * -the amount of ticks that it was handled already equal its required handle time
     * ------------------------------------------------
     * if an emergency was newly resolved, we have to:
     * -move it to list of resolved emergencies
     * -send all assigned assets back to their bases
     * -log that the emergency was resolved
     */
    private fun updateResolvedEmergencies() {
        val newlyResolvedEmergencies: MutableList<Emergency> = mutableListOf()

        for (em in handledEmergencies) {
            if (em.handlingStarted && em.alreadyHandled == em.handleTime) {
                resolvedOrFailedEmergencies.add(em)
                newlyResolvedEmergencies.add(em)
                for (vec in em.assignedVehicles) {
                    vec.sendBackToBase()
                }
            } else if (em.handlingStarted) {
                em.alreadyHandled++
            }
        }
        newlyResolvedEmergencies.sortBy { it.id }
        for (resolvedEm in newlyResolvedEmergencies) {
            Logger.logEmergencyResolved(resolvedEm.id)
            Simulation.statistics.ongoingEmergencies--
            Simulation.statistics.resolvedEmergencies++
        }
    }

    /**
     * updates the state of all emergencies that failed in this tick
     * an emergency failed in this tick if:
     * -it has not been resolved yet (is not in the list of resolved emergencies)
     * -there is no more time to resolve it (-> the emergency's starting tick plus its maxDuration is greater equal the
     * current tick of the simulation)
     */
    private fun updateFailedEmergencies() {
        val newlyFailedEmergencies: MutableList<Emergency> = mutableListOf()

        for (em in startingEmergencies) {
            if (em.tick + em.maxDuration - em.handleTime <= Simulation.currentTick) {
                resolvedOrFailedEmergencies.add(em)
                newlyFailedEmergencies.add(em)
                for (vec in em.assignedVehicles) {
                    vec.sendBackToBase()
                }
            }
        }
        /*for (em in handledEmergencies) {
            if (em.tick + em.maxDuration <= Simulation.currentTick) {
                resolvedOrFailedEmergencies.add(em)
                newlyFailedEmergencies.add(em)
                for (vec in em.assignedVehicles) {
                    vec.sendBackToBase()
                }
            }
        }*/
        newlyFailedEmergencies.sortBy { it.id }
        for (resolvedEm in newlyFailedEmergencies) {
            Logger.logEmergencyFailed(resolvedEm.id)
            Simulation.statistics.ongoingEmergencies--
            Simulation.statistics.failedEmergencies++
        }
        handledEmergencies.removeAll(resolvedOrFailedEmergencies)
    }

    /**
     * updates starting and ending events
     */
    fun updateEvents(): Boolean {
        var eventsChanged = false
        // first handle the ending events
        val removelistActive = mutableListOf<Event>()
        for (event in activeEvents) {
            if (event.tick + event.duration == Simulation.currentTick) {
                removelistActive.add(event)
                event.stopEvent()
                eventsChanged = true
            }
        }
        activeEvents.removeAll(removelistActive)
        // then handle the starting events
        val removelist = mutableListOf<Event>()
        for (event in startingEvents) {
            if (event.executeStart()) {
                removelist.add(event)
                activeEvents.add(event)
                eventsChanged = true
            }
        }
        startingEvents.removeAll(removelist)
        return eventsChanged
    }

    /**
     * reroutes all vehicles that are currently driving
     */
    fun rerouteVehicles() {
        var numberOfReroutedVehicles = 0
        Simulation.emergencies.sortBy { it.tick }
        for (em in Simulation.emergencies) {
            if (em.tick > Simulation.currentTick) {
                break
            }
            for (vec in em.assignedVehicles) {
                if (!vec.reroutable()) continue
                val wasRerouted = vec.reroute()
                numberOfReroutedVehicles = if (wasRerouted) {
                    numberOfReroutedVehicles + 1
                } else {
                    numberOfReroutedVehicles
                }
            }
        }
        Logger.logAssetsRerouted(numberOfReroutedVehicles)
        Simulation.statistics.reroutedAssets += numberOfReroutedVehicles
    }
}
