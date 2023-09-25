package de.unisaarland.cs.se.selab

/**
 * Bases of the departments
 */
class Base(
    private val id: Int,
    private var staff: Int,
    private val location: Vertex,
    private val vehicles: MutableList<Vehicle>
) {

    private val nextBases: MutableList<Base> = mutableListOf()

    /**
     * @return ID of Base
     */
    fun getId(): Int {
        return id
    }

    /**
     * @return Staff Number
     */
    fun getStaff(): Int {
        return staff
    }

    /**
     * @return Location
     */
    fun getLocation(): Vertex {
        return location
    }

    /**
     * @return Vehicle List
     */
    fun getVehicles(): MutableList<Vehicle> {
        return vehicles
    }

    /**
     * @return NextBases
     */
    fun getNextBases(): MutableList<Base> {
        return nextBases
    }

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
        val r = em.getResources()
        val neededVehicles = r.getVehicles()
        var water = r.getWaterAmount()
        var criminals = r.getCriminalAmount()
        var patients = r.getPatientAmount()
        var neededVehiclesCopy = neededVehicles.toMutableList()

        // TODO: implement
        return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * Initiates reallocation phase, looks through own vehicles if can be reallocated to emergency
     * @return a Resource with what's still missing
     */
    fun reallocateResources(em: Emergency): Resource {
        // TODO: implement
        return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * Each tick due to events recalculates the new distance of the other bases
     * updates list of next bases
     */
    fun calculateNextBases() {
        // TODO: implement

        // recalculates the distances of the other bases to this base
        // updates 'nextBases' with new distances and sorts the list
        // this calculation has to be made once per tick since new events might change the distances of the next bases
    }

    /**
     * @return the next Base
     */
    fun getNextBase(b: Base): Base? {
        // TODO: implement
        return null
    }

    /**
     * send a request to the next closest Base
     * adds request to Requestlist in EMCC
     */
    fun makeRequest(em: Emergency, nextBase: Base) {
        // create a request
        val req = Request(EMCC.getNextRequestId(), em, nextBase)
        // add it to the list of requests
        EMCC.getRequests().add(req)
        // log that a new request was made
        Logger.logAssetRequest(req.getId(), nextBase.getId(), em.getId())
        // count up the id of the next request
        EMCC.increaseNextRequestId()
    }
}
