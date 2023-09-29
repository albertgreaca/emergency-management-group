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

    companion object {
        const val maxCriminalCapacity = 4
        const val maxWaterCapacity = 2400
    }

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
    fun requestResources(em: Emergency) {
        val r = em.resources
        val neededVehicles = r.vehicles

        // create a list of all vehicles in the base which could potentially be allocated
        val potentialVehicles = requireNotNull(em.base).vehicles.filter {
            it.available &&
                it.vehicleType in neededVehicles
        }.toMutableList()

        val vehiclesToAllocate: MutableList<Vehicle> = mutableListOf()
        for (i in min(neededVehicles.size, potentialVehicles.size) downTo 0) {
            vehiclesToAllocate.clear()
            val res = trySendThisNumberOfAssets(i, em, potentialVehicles)
            if (res != null) {
                break
            }
            vehiclesToAllocate.addAll(requireNotNull(res))
        }

        // allocate all vehicles in the list
        for (vehicle in vehiclesToAllocate) {
            // calculate and set the position of the vehicle
            vehicle.position = Dijkstra.dijkstraHeight(this.location.id, em.road, vehicle.vehicleHeight)
            // reduce the available staff of the vehicle
            this.staff -= vehicle.staffCapacity
            // set the target emergency of the vehicle
            vehicle.targetEmergency = em
            // remove the vehicle type from the list of needed vehicle types
            em.resources.vehicles.remove(vehicle.vehicleType)
        }

        // var availableBaseVehicles = this.vehicles.filter { it.available }.toMutableList()
        // val vehicTypesToRequest = mutableListOf<VehicleType>()
        // TODO : implement
        /*for (vt in neededVehicles) {
            // Todo : something happening here
            var p = 0
            p += 0
        }*/
        // Todo : Implement actual allocation of vehicles in vehiclesToAllocate
        /*for (vehicle in vehiclesToAllocate) {
            var p = 0
            p += 0
        }*/
        //return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * @returns the ordered list of vehicles to be allocated if allocation of n vehicles is possible, null otherwise
     */
    fun trySendThisNumberOfAssets(k: Int, em: Emergency, vehicles: MutableList<Vehicle>): MutableList<Vehicle>? {
        val resource = em.resources
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
            if (checkCombination(em, cur, )) {
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
    fun checkCombination(em: Emergency, vehicles: MutableList<Vehicle>): Boolean {
        val resource = em.resources
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
        if (resource.criminalAmount - fittingCriminals >
            maxCriminalCapacity * (
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
            val pos = Dijkstra.dijkstraHeight(this.location.id, em.road, vec.vehicleHeight)
            if (requireNotNull(pos).arrivalTicks + Simulation.currentTick + em.handleTime > em.tick + em.maxDuration) {
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
     * helper function to check available vehicles for corresponding type
     */
    private fun helperVehicleCompare(v: VehicleType, vehicleList: List<Vehicle>): Vehicle? {
        for (vehicle in vehicleList) {
            if (vehicle.vehicleType == v) {
                return vehicle
            }
        }
        return null
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
