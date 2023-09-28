package de.unisaarland.cs.se.selab

import kotlin.math.min

/**
 * Bases of the departments
 */
open class Base(
    val id: Int,
    var staff: Int,
    val location: Vertex,
    val vehicles: MutableList<Vehicle>
) {

    private val nextBases: MutableList<Base> = mutableListOf()

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
    fun requestResources(em: Emergency): Resource {
        val r = em.resources
        val neededVehicles = r.vehicles
        var water = r.waterAmount
        var criminals = r.criminalAmount
        var patients = r.patientAmount

        // create a list of all vehicles in the base which could potentially be allocated
        val potentialVehicles = requireNotNull(em.base).vehicles.filter { it.available
                && it.vehicleType in neededVehicles }.toMutableList()

        var vehiclesToAllocate: MutableList<Vehicle>? = mutableListOf()
        for (i in min(neededVehicles.size, potentialVehicles.size)..0) {
            vehiclesToAllocate = canSendThisNumberOfAssets(i, em.resources, potentialVehicles)
            if (vehiclesToAllocate != null) {
                break
            }
        }

        // if we could not allocate anything, return the initial resource
        if (vehiclesToAllocate == null) {
            return em.resources
        }

        // allocate all vehicles in the list
        for (vehicle in vehiclesToAllocate) {

        }




        // var availableBaseVehicles = this.vehicles.filter { it.available }.toMutableList()
        val vehicTypesToRequest = mutableListOf<VehicleType>()
        // TODO : implement
        for (vt in neededVehicles) {
            // Todo : something happening here
            var p = 0
            p += 0
        }
        // Todo : Implement actual allocation of vehicles in vehiclesToAllocate
        for (vehicle in vehiclesToAllocate) {
            var p = 0
            p += 0
        }
        return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * @returns the ordered list of vehicles to be allocated if allocation of n vehicles is possible, null otherwise
     */
    fun canSendThisNumberOfAssets(n: Int, resource: Resource, vehicles: MutableList<Vehicle>): MutableList<Vehicle>? {
        vehicles.sortBy { it.id }

    // try each combination of n vehicles starting with the lowest id's

    }


    /**
     * @returns true if the combination of vehicles can fulfill every constraint of the resource, false otherwise
     */
    fun checkCombination(resource: Resource, vehicles: MutableList<Vehicle>): Boolean {
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
                fittingWater += vec.getWaterAmount()
                numberOfWaterTrucks++
            }
        }
        if (staffNeeded > this.staff) return false
        if (resource.criminalAmount - fittingCriminals > 4 * (resource.countInstancesOf(VehicleType.POLICE_CAR) -
                    numberOfPoliceCars)) {
            return false
        }
        if (resource.waterAmount - fittingWater > 2400 * (resource.countInstancesOf(VehicleType.FIRE_TRUCK_WATER) -
                    numberOfWaterTrucks)) {
            return false
        }

        // for each vehicle-type, check if there are more vehicles in the combination that required
        for (vehicleType in VehicleType.values()) {
            if (resource.countInstancesOf(vehicleType) < vehicles.count { it.vehicleType == vehicleType }) {
                return false
            }
        }
        return true
    }

    /**
     * Initiates reallocation phase, looks through own vehicles if can be reallocated to emergency
     * @return a Resource with what's still missing
     */
    fun reallocateResources(em: Emergency): Resource {
        // TODO : implement
        em.id
        // Only vehicles that are unavailable can be reallocated
        // -> filter List for this
        // also filter if target emergency severity is lower (bcs only then you can reallocate)
        val allvehics = this.vehicles
        val unavailableVehics = allvehics.filter { it.available == false }
        val ontwVehics = unavailableVehics.filter { it.targetEmergency != null }
        val reallocableVehics = ontwVehics.filter {
            requireNotNull(it.targetEmergency).severity < em.severity
        }.toMutableList()
        // get emergencies resource
        val neededVehicles = em.resources.vehicles
        val vehicTypesToRequest = mutableListOf<VehicleType>()
        // for each vehic type in resource
        for (vt in neededVehicles) {
            // check type special vs normal
            // check if it is in filtered list
            val vehic = helperVehicleCompare(vt, reallocableVehics)
            if (vehic != null) {
                // is staffed already
                val height = vehic.vehicleHeight
                val pos = Dijkstra.dijkstraHeight(this.location.id, em.road, height)
                // only thing we need is dijkstra
                // look if it arrives in time
                if (requireNotNull(pos).arrivalTicks +
                    Simulation.currentTick + em.handleTime > em.tick + em.maxDuration
                ) {
                    // no
                    // abort
                    vehicTypesToRequest.add(vt)
                } else {
                    // yes
                    // change position, remove from needed list
                    vehic.position = pos
                    vehic.targetEmergency = em
                }
            } else {
                vehicTypesToRequest.add(vt)
            }
        }
        return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * Each tick due to events recalculates the new distance of the other bases
     * updates list of next bases
     */
    fun calculateNextBases() {
        // TODO : implement
        val nextcalculatedBases = Dijkstra.dijkstraRequest(this.location.id)
        this.nextBases.clear()
        this.nextBases.addAll(nextcalculatedBases)
        return
        // recalculates the distances of the other bases to this base
        // updates 'nextBases' with new distances and sorts the list
        // this calculation has to be made once per tick since new events might change the distances of the next bases
    }

    /**
     * @return the next Base
     */
    fun getNextBase(b: Base): Base? {
        b.id
        if (this.nextBases.isEmpty()) {
            return null
        } else {
            val returnBase = this.nextBases[0]
            this.nextBases.removeAt(0)
            return returnBase
        }
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextPoliceBase(b: Base): Base? {
        var nextPoliceBase = getNextBase(b)
        if (nextPoliceBase == null) {
            return null
        }
        while (!(nextPoliceBase is PoliceStation)) {
            nextPoliceBase = getNextPoliceBase(requireNotNull(nextPoliceBase))
        }
        return nextPoliceBase
    }

    /**
     * returns next Base of Type Hospital
     */
    fun getNextHospital(b: Base): Base? {
        var nextHospital: Base? = getNextBase(b) ?: return null
        while (nextHospital !is Hospital) {
            nextHospital = getNextHospital(requireNotNull(nextHospital))
        }
        return nextHospital
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextFireBase(b: Base): Base? {
        var nextFireBase: Base? = getNextBase(b) ?: return null
        while (nextFireBase is PoliceStation || nextFireBase is Hospital) {
            nextFireBase = getNextFireBase(nextFireBase)
        }
        return nextFireBase
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
        Logger.logAssetRequest(req.getId(), nextBase.id, em.id)
        // count up the id of the next request
        EMCC.nextRequestId++
    }
}
