package de.unisaarland.cs.se.selab.bases

import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyUtils
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.resources.Request
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.utils.Position
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import kotlin.math.max
import kotlin.math.min

/**
 * Bases of the departments
 */
open class Base(
    val id: Int,
    var staff: Int,
    val location: Vertex,
    val vehicles: MutableList<Vehicle>,
) {

    companion object {
        const val MaxCriminalCapacity = 4
        const val maxWaterCapacity = 2400
        const val ladder40 = 40
        const val ladder30 = 30
    }

    val nextBases: MutableList<Base> = mutableListOf()

    /**
     * add Vehicle to Base Vehicle List
     */
    fun addVehicle(v: Vehicle) {
        vehicles.add(v)
    }

    /**
     * checks if the needed Resource for the Emergency are
     * available
     * @return the stuff that is still missing
     */
    fun requestResources(em: Emergency) {
        val r = em.currentResources
        val neededVehicles = r.vehicles

        // create a list of all vehicles in the base which could potentially be allocated
        val potentialVehicles = vehicles.filter {
            it.available &&
                it.vehicleType in neededVehicles
        }.toMutableList()

        val vehiclesToAllocate: MutableList<Vehicle> = mutableListOf()
        for (i in min(neededVehicles.size, potentialVehicles.size) downTo 0) {
            vehiclesToAllocate.clear()
            val res = trySendThisNumberOfAssets(i, em, potentialVehicles)
            if (res != null) {
                vehiclesToAllocate.addAll(requireNotNull(res))
                break
            }
        }

        val loggerlist = mutableListOf<Vehicle>()

        // allocate all vehicles in the list
        for (vehicle in vehiclesToAllocate) {
            allocateVehicle(vehicle, em, loggerlist)
        }
        loggerlist.sortBy { it.id }
        for (ve in loggerlist) {
            Logger.logAssetAllocation(ve.id, em.id, requireNotNull(ve.position).arrivalTicks)
        }
    }

    /**
     * allocates Vehicles
     */
    fun allocateVehicle(vehicle: Vehicle, em: Emergency, loggerlist: MutableList<Vehicle>) {
        // calculate and set the position of the vehicle
        vehicle.position = Dijkstra.dijkstraHeight(this.location.realid, em.road, vehicle.vehicleHeight)
        // set position to started this tick
        requireNotNull(vehicle.position).startedThisTick = true
        // reduce the available staff of the vehicle
        this.staff -= vehicle.staffCapacity
        // set the target emergency of the vehicle
        vehicle.targetEmergency = em
        // remove the vehicle type from the list of needed vehicle types
        em.currentResources.vehicles.remove(vehicle.vehicleType)
        // reduce needed patients/water/criminals for special vehicles
        allocateWhen(em, vehicle)
        // set vehicle availability false
        vehicle.available = false
        // add vehicle to list in emergency
        em.addVehicle(vehicle)
        loggerlist.add(vehicle)
    }

    private fun allocateWhen(em: Emergency, vehicle: Vehicle) {
        when (vehicle) {
            is FireTruckWater -> em.currentResources.waterAmount = max(
                0,
                em.currentResources.waterAmount -
                    vehicle.waterCapacity
            )
            is Ambulance -> em.currentResources.patientAmount = max(0, em.currentResources.patientAmount - 1)
            is PoliceCar -> em.currentResources.criminalAmount = max(
                0,
                em.currentResources.criminalAmount -
                    vehicle.criminalCapacity
            )
        }
    }

    /**
     * @returns the ordered list of vehicles to be allocated if allocation of n vehicles is possible, null otherwise
     */
    fun trySendThisNumberOfAssets(k: Int, em: Emergency, vehicles: MutableList<Vehicle>): MutableList<Vehicle>? {
        vehicles.sortBy { it.id }

        // try each combination of n vehicles starting with the lowest id's
        val n = vehicles.size
        val tries = IntArray(k) { it }
        val cur: MutableList<Vehicle> = mutableListOf()
        while (true) {
            cur.clear()
            for (i in 0..k - 1) {
                cur.add(vehicles[tries[i]])
            }
            if (checkCombination(em, cur)) {
                return cur
            }
            var pos = -1
            for (i in k - 1 downTo 0) {
                if (tries[i] != n - (k - i)) {
                    pos = i
                    break
                }
            }
            if (pos == -1) {
                break
            }
            tries[pos]++
            for (i in pos + 1..k - 1) {
                tries[i] = tries[i - 1] + 1
            }
        }
        return null
    }

    /**
     * @returns true if the combination of vehicles can fulfill every constraint of the resource, false otherwise
     */
    open fun checkCombination(em: Emergency, vehicles: MutableList<Vehicle>): Boolean {
        var validCombination = true
        val resource = em.currentResources
        var staffNeeded = 0
        var fittingCriminals = 0
        var fittingWater = 0
        var numberOfPoliceCars = 0
        var numberOfWaterTrucks = 0
        for (vec in vehicles) {
            staffNeeded += vec.staffCapacity
            if (vec is PoliceCar) {
                fittingCriminals += vec.criminalCapacity
                numberOfPoliceCars++
            }
            if (vec is FireTruckWater) {
                fittingWater += vec.waterCapacity
                numberOfWaterTrucks++
            }
        }
        if (staffNeeded > this.staff) validCombination = false
        if (resource.criminalAmount - fittingCriminals >
            MaxCriminalCapacity * (
                resource.countInstancesOf(VehicleType.POLICE_CAR) -
                    numberOfPoliceCars
                )
        ) {
            return false
        }
        if (resource.waterAmount - fittingWater >
            maxWaterCapacity * (
                resource.countInstancesOf(VehicleType.FIRE_TRUCK_WATER) -
                    numberOfWaterTrucks
                )
        ) {
            return false
        }

        // for each vehicle-type, check if there are more vehicles in the combination that required
        for (vehicleType in VehicleType.values()) {
            if (resource.countInstancesOf(vehicleType) < vehicles.count { it.vehicleType == vehicleType }) {
                return false
            }
        }

        // check if all vehicles arrive in time using dijkstra
        for (vec in vehicles) {
            val pos = Dijkstra.dijkstraHeight(this.location.realid, em.road, vec.vehicleHeight)
            if (requireNotNull(pos).arrivalTicks + Simulation.currentTick + em.handleTime > em.tick + em.maxDuration) {
                return false
            }
        }
        return validCombination
    }

    /**
     * Initiates reallocation phase, looks through own vehicles if can be reallocated to emergency
     * @return a Resource with what's still missing
     */
    fun reallocateResources(em: Emergency): Resource {
        // list of all successfully reallocated vehicles for logging
        val reallocatedList = mutableListOf<Vehicle>()

        // list of all reallocatable vehicles
        val reallocatableVehics = this.vehicles.filter { it.reallocatable(em) }

        // copy of the needed vehicle types so that we have two separate lists for iterating and removing
        val copyOfNeededTypes = em.currentResources.vehicles.toMutableList()

        // for each vehic type in resource
        for (vt in copyOfNeededTypes) {
            innerloop(reallocatableVehics, vt, em, reallocatedList)
        }

        // log all reallocations ordered by asset id
        reallocatedList.sortBy { it.id }
        for (v in reallocatedList) {
            Logger.logAssetReallocation(v.id, em.id)
        }
        return em.currentResources
    }
    private fun innerloop(
        reallocatableVehics: List<Vehicle>,
        vt: VehicleType,
        em: Emergency,
        reallocatedList: MutableList<Vehicle>
    ) {
        for (vehic in reallocatableVehics) {
            // if vehicle has wrong type skip to next vehicle
            if (vehic.vehicleType != vt) continue

            // calculate new position of vehicle
            val height = vehic.vehicleHeight
            val curpos = requireNotNull(vehic.position)
            var pos: Position?
            if (curpos.roadList.isEmpty() || curpos.destinationVertex == null) {
                val road1: Road = em.road
                curpos.destinationVertex = this.location
                pos = Dijkstra.dijkstraReroute(
                    road1,
                    0,
                    0,
                    requireNotNull(curpos.destinationVertex),
                    em.road,
                    height
                )
            } else {
                pos = Dijkstra.dijkstraReroute(
                    curpos.roadList[0],
                    curpos.distanceFromStart,
                    curpos.distanceFromEnd,
                    requireNotNull(curpos.destinationVertex),
                    em.road,
                    height
                )
            }

            // if vehicle cannot arrive in time then skip to next vehicle
            val res = requireNotNull(pos).arrivalTicks +
                Simulation.currentTick + em.handleTime > em.tick + em.maxDuration
            if (!res) {
                // set the vehicle's position to the calculated position
                vehic.position = pos

                // remove the vehicle from the assigned vehicles of previous emergency
                requireNotNull(vehic.targetEmergency).assignedVehicles.remove(vehic)

                // add the vehicle to the assigned vehicles of new emergency
                em.assignedVehicles.add(vehic)

                // if previous emergency was in handledEmergencies, move it back to startingEmergencies
                if (requireNotNull(vehic.targetEmergency) in EMCC.handledEmergencies) {
                    EMCC.handledEmergencies.remove(vehic.targetEmergency)
                    EMCC.startingEmergencies.add(requireNotNull(vehic.targetEmergency))
                }

                // transfer the resources needed from new emergency to previous emergency
                transferResources(requireNotNull(vehic.targetEmergency), em, vehic)

                // set the vehicle's target emergency to the new emergency
                vehic.targetEmergency = em

                // add vehicle to logging list
                reallocatedList.add(vehic)
                return
            }
        }
    }
    private fun transferResources(oldem: Emergency, newem: Emergency, vehic: Vehicle) {
        // add vehicle type to current resource of previous emergency
        oldem.currentResources.addVehicle(vehic.vehicleType)

        // remove vehicle type from current resource of new emergency
        newem.currentResources.vehicles.remove(vehic.vehicleType)

        when (vehic) {
            is PoliceCar -> updateResourcePoliceCar(oldem, newem, vehic)
            is FireTruckWater -> updateResourceWaterTruck(oldem, newem, vehic)
            is Ambulance -> updateResourceAmbulance(oldem, newem)
        }
    }

    private fun updateResourcePoliceCar(oldem: Emergency, newem: Emergency, vehic: PoliceCar) {
        if (!EMCC.resolvedOrFailedEmergencies.contains(oldem)) {
            // calculate the difference between originally needed criminals and number of criminals that can be
            // transported with remaining vehicles of the old emergency
            val emUt = EmergencyUtils()
            oldem.currentResources.criminalAmount = max(
                0,
                oldem.originalResources.criminalAmount -
                    emUt.potentialCriminals(oldem)
            )
        }
        // criminals still needed for newem decrease by vehic.criminalsStillFitting but cannot be less than 0
        newem.currentResources.criminalAmount = max(
            0,
            newem.currentResources.criminalAmount -
                vehic.criminalsStillFitting
        )
    }

    private fun updateResourceAmbulance(oldem: Emergency, newem: Emergency) {
        if (!EMCC.resolvedOrFailedEmergencies.contains(oldem)) {
            // calculate the difference between originally needed patients and number of patients that can be
            // transported with remaining vehicles of the old emergency
            val emUt = EmergencyUtils()
            oldem.currentResources.patientAmount = oldem.originalResources.patientAmount - emUt.potentialPatients(oldem)
        }
        // patients still needed for newem decrease by 1 but cannot be less than 0
        newem.currentResources.patientAmount = max(0, newem.currentResources.patientAmount - 1)
    }

    private fun updateResourceWaterTruck(oldem: Emergency, newem: Emergency, vehic: FireTruckWater) {
        if (!EMCC.resolvedOrFailedEmergencies.contains(oldem)) {
            // calculate the difference between originally needed water and amount of water that can be
            // transported with remaining vehicles of the old emergency
            val emUt = EmergencyUtils()
            oldem.currentResources.waterAmount = oldem.originalResources.waterAmount - emUt.potentialWater(oldem)
        }
        // water still needed for newem decreases by the water transported by the vehicle but cannot be less than 0
        newem.currentResources.waterAmount = max(0, newem.currentResources.waterAmount - vehic.waterTransported)
    }

    /**
     * Each tick due to events recalculates the new distance of the other bases
     * updates list of next bases
     */
    fun calculateNextBases() {
        val nextcalculatedBases = Dijkstra.dijkstraRequest(this.location.realid)
        this.nextBases.clear()
        this.nextBases.addAll(nextcalculatedBases)
        return
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextPoliceBase(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base is PoliceStation && ok) {
                return base
            }
        }
        return null
    }

    /**
     * returns next Base of Type Hospital
     */
    fun getNextHospital(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base is Hospital && ok) {
                return base
            }
        }
        return null
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextFireBase(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base !is Hospital && base !is PoliceStation && ok) {
                return base
            }
        }
        return null
    }

    /**
     * send a request to the next closest Base
     * adds request to Requestlist in EMCC
     */
    fun makeRequest(em: Emergency, nextBase: Base) {
        // create a request
        val req = Request(EMCC.nextRequestId, em, nextBase)
        // add it to the list of requests
        EMCC.requests.add(req)
        // log that a new request was made
        Logger.logAssetRequest(req.id, nextBase.id, em.id)
        // count up the id of the next request
        EMCC.nextRequestId++
    }
}
