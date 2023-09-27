package de.unisaarland.cs.se.selab

/**
 * This is the class responsible for simulating the different phases of a tick
 */
object EMCC {
    val observers: MutableList<EmergencyObserver> = mutableListOf()
    val startingEmergencies: MutableList<Emergency> = mutableListOf()
    val handledEmergencies: MutableList<Emergency> = mutableListOf()
    val resolvedOrFailedEmergencies: MutableList<Emergency> = mutableListOf()
    val activeEvents: MutableList<Event> = mutableListOf()
    val startingEvents: MutableList<Event> = mutableListOf()
    var nextRequestId: Int = 1
    val requests: MutableList<Request> = mutableListOf()

    /**
     * increases the ID of the next request by 1
     */
    fun increaseNextRequestId() {
        nextRequestId++
    }

    /**
     * notifies all observers about new emergencies, this initiates the emergency phase
     */
    fun notifyObservers() {
        for (o in observers) {
            o.update(startingEmergencies)
        }
    }

    /**
     * adds a department to the list of observers
     */
    fun addObserver(ob: EmergencyObserver) {
        observers.add(ob)
    }

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
        startingEmergencies.sortWith(compareBy({ it.severity }, { it.id }))
    }

    /**
     * allocates assets for each starting emergency
     */
    fun allocateAssets() {
        // iterate over emergencies
        for (em in startingEmergencies) {
            // get the base assigned to the emergency
            var emBase = em.base!!

            // base tries to allocate resources for emergency, returns what is left
            var resourcesAfterAllocating = emBase.requestResources(em)

            // update the resources in the emergency
            em.resources.updateDifference(resourcesAfterAllocating)

            // base tries to reallocate resources from other emergencies, returns what is left
            var resourcesAfterReallocating = emBase.reallocateResources(em)

            // update the resources in the emergency
            em.resources.updateDifference(resourcesAfterReallocating)

            // if there are remaining resources after reallocating, a request to the next base has to be created
            if (!em.resources.isEmpty()) {
                var policeResources = em.resources.filterPoliceResources()
                var fireResources = em.resources.filterFireResources()
                var ambulanceResources = em.resources.filterAmbulanceResources()

                // make a request for the missing police resources
                if (!policeResources.isEmpty()) {
                    var nextPoliceBase = emBase.getNextPoliceBase(emBase)
                    if (nextPoliceBase != null) {
                        emBase.makeRequest(em, nextPoliceBase)
                    }
                }

                // make a request for the missing police resources
                if (!fireResources.isEmpty()) {
                    var nextFireBase = emBase.getNextFireBase(emBase)
                    if (nextFireBase != null) {
                        emBase.makeRequest(em, nextFireBase)
                    }
                }

                if (!ambulanceResources.isEmpty()) {
                    var nextAmbulanceBase = emBase.getNextHospital(emBase)
                    if (nextAmbulanceBase != null) {
                        emBase.makeRequest(em, nextAmbulanceBase)
                    }
                }
            }
        }
    }

    /**
     * handles all requests made in the allocation phase of the current tick
     */
    fun processRequests() {
        while (!requests.isEmpty()) {
        }
    }

    /**
     * adds an event to the startingEvents list
     */
    fun addStartingEvent(event: Event) {
        startingEvents.add(event)
    }

    /**
     * removes an event from the startingEvents list
     */
    fun removeStartingEvent(event: Event) {
        startingEvents.remove(event)
    }

    /**
     * adds an event to the activeEvents list
     */
    fun addActiveEvent(event: Event) {
        activeEvents.add(event)
    }

    /**
     * removes an event from the activeEvents list
     */
    fun removeActiveEvent(event: Event) {
        activeEvents.remove(event)
    }

    /**
     * moves an event from the startingEvents list to the activeEvents list
     */
    fun moveFromStartingToActive(event: Event) {
        removeStartingEvent(event)
        addActiveEvent(event)
    }

    /**
     * moves an event from the activeEvents list to the startingEvents list
     */
    fun moveFromActiveToStarting(event: Event) {
        removeActiveEvent(event)
        addStartingEvent(event)
    }

    /**
     * updates the state of all driving assets
     */
    fun updateAssets() {
        val newlyArrivedAssets: MutableList<Pair<Int, Int>> = mutableListOf()
        for (em in Simulation.emergencies) {
            for (vec in em.assignedVehicles) {
                // move each vehicle that is currently driving
                if (vec.position != null && vec.position!!.arrivalTicks != 0) {
                    vec.move()
                    // if a vehicle arrived at an emergency after moving, log it
                    if (!vec.position!!.isDrivingBack && vec.position!!.arrivalTicks == 0) {
                        newlyArrivedAssets.add(Pair(vec.id, vec.position!!.destinationVertex!!.id))
                    }
                    // if a vehicle arrived back at its base after moving, log it
                    if (vec.position!!.isDrivingBack && vec.position!!.arrivalTicks == 0) {
                        newlyArrivedAssets.add(Pair(vec.id, vec.position!!.destinationVertex!!.id))
                        vec.targetEmergency!!.assignedVehicles.remove(vec)
                        vec.targetEmergency = null
                        vec.position = null
                    }
                }
            }
        }
        newlyArrivedAssets.sortBy { it.first }
        for ((aid, vid) in newlyArrivedAssets) {
            Logger.logAssetArrival(aid, vid)
        }
    }

    /**
     * updates the state of all emergencies
     */
    fun updateEmergencies() {
        // update all emergencies who allocated all resources in this tick
        for (em in startingEmergencies) {
            if (em.resources.isEmpty()) {
                startingEmergencies.remove(em)
                handledEmergencies.add(em)
            }
        }
        // update all emergencies whose handling started in this tick
        updateHandlingStartedEmergencies()
        // update all emergencies that were resolved in this tick
        updateResolvedEmergencies()
        // update all emergencies that failed in this tick
        updateFailedEmergencies()
        // if all assets assigned to an emergency returned to their bases, we don't need to track it anymore
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
        var newlyHandlingStartedEmergencies: MutableList<Emergency> = mutableListOf()
        for (em in handledEmergencies) {
            if (!em.handlingStarted && em.resources.isEmpty()) {
                var allArrived = true
                for (vec in em.assignedVehicles) {
                    allArrived = if (vec.position!!.arrivalTicks == 0) allArrived else false
                }
                if (allArrived) {
                    em.handlingStarted = true
                    newlyHandlingStartedEmergencies.add(em)
                }
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
            }
        }
        newlyResolvedEmergencies.sortBy { it.id }
        for (resolvedEm in newlyResolvedEmergencies) {
            Logger.logEmergencyResolved(resolvedEm.id)
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

        for (em in handledEmergencies) {
            if (em.tick + em.maxDuration >= Simulation.currentTick) {
                resolvedOrFailedEmergencies.add(em)
                newlyFailedEmergencies.add(em)
                for (vec in em.assignedVehicles) {
                    vec.sendBackToBase()
                }
            }
        }
        newlyFailedEmergencies.sortBy { it.id }
        for (resolvedEm in newlyFailedEmergencies) {
            Logger.logEmergencyFailed(resolvedEm.id)
        }
    }

    /**
     * updates starting and ending events
     */
    fun updateEvents(): Boolean {
        var eventsChanged = false
        // first handle the ending events
        for (event in activeEvents) {
            if (event.tick + event.duration == Simulation.currentTick) {
                removeActiveEvent(event)
                event.stopEvent()
                eventsChanged = true
            }
        }
        // then handle the starting events
        for (event in startingEvents) {
            if (event.executeStart()) {
                eventsChanged = true
            }
        }
        return eventsChanged
    }

    /**
     * reroutes all vehicles that are currently driving
     */
    fun rerouteVehicles() {
        var numberOfReroutedVehicles = 0
        for (em in Simulation.emergencies) {
            for (vec in em.assignedVehicles) {
                if (vec.reroutable()) {
                    val wasRerouted = vec.reroute()
                    numberOfReroutedVehicles = if (wasRerouted) numberOfReroutedVehicles + 1 else numberOfReroutedVehicles
                }
            }
        }
        Logger.logAssetsRerouted(numberOfReroutedVehicles)
        Simulation.statistics.increaseRerouted(numberOfReroutedVehicles)
    }
}
