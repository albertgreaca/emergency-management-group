package de.unisaarland.cs.se.selab
/**
 * Bases of the departments
 */
open class Base(
    val id: Int,
    var staff: Int,
    val location: Vertex,
    val vehicles: MutableList<Vehicle>
) {

    private var nextBases: MutableList<Base> = mutableListOf()

    /**
     * reduce Staff capacity of base
     */
    fun reduceStaff(capacity: Int) {
        staff -= capacity
    }

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
        water += 0
        var criminals = r.criminalAmount
        criminals += 0
        var patients = r.patientAmount
        patients += 0
        val neededVehiclesCopy = neededVehicles.toMutableList()
        neededVehiclesCopy.isEmpty()
        // var availableBaseVehicles = this.vehicles.filter { it.available }.toMutableList()
        val vehiclesToallocate = mutableListOf<Vehicle>()
        val vehicTypestoRequest = mutableListOf<VehicleType>()
        // TODO : implement
        for (vt in neededVehicles) {
            // Todo : something happening here
            var p = 0
            p += 0
        }
        // Todo : Implement actual allocation of vehicles in vehiclesToAllocate
        for (vehicle in vehiclesToallocate) {
            var p = 0
            p += 0
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
        val vehicTypestoRequest = mutableListOf<VehicleType>()
        // for each vehic type in resource
        for (vt in neededVehicles) {
            // check type special vs normal
            // check if it is in filtered list
            var vehic = helperVehicleCompare(vt, reallocableVehics)
            if (vehic != null) {
                // is staffed already
                val height = vehic.vehicleHeight
                var pos = Dijkstra.dijkstraHeight(this.location.id, em.road, height)
                // only thing we need is dijkstra
                // look if it arrives in time
                if (requireNotNull(pos).arrivalTicks +
                    Simulation.currentTick + em.handleTime > em.tick + em.maxDuration
                ) {
                    // no
                    // abort
                    vehicTypestoRequest.add(vt)
                } else {
                    // yes
                    // change position, remove from needed list
                    vehic.position = pos
                    vehic.targetEmergency = em
                }
            } else {
                vehicTypestoRequest.add(vt)
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
        this.nextBases = nextcalculatedBases
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
