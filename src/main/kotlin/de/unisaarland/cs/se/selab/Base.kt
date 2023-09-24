class Base(private val id: Int, private var staff: Int, private val location: Vertex,
           private val vehicles: MutableList<Vehicle>) {

    private val nextBases: MutableList<Base> = mutableListOf()

    fun getId(): Int {
        return id
    }

    fun getStaff(): Int {
        return staff
    }

    fun getLocation(): Vertex {
        return location
    }

    fun getVehicles(): MutableList<Vehicle> {
        return vehicles
    }

    fun getNextBases(): MutableList<Base> {
        return nextBases
    }

    fun reduceStaff(capacity: Int) {
        staff -= capacity
    }

    fun addVehicle(v: Vehicle) {
        vehicles.add(v)
    }

    fun requestResources(em: Emergency): Resource {
        val r = em.getResources()
        val neededVehicles = r.getVehicles()
        var water = r.getWaterAmount()
        var criminals = r.getCriminalAmount()
        var patients = r.getPatientAmount()
        var neededVehiclesCopy = neededVehicles.toMutableList()

        //TODO: implement
    }

    fun reallocateResources(em: Emergency): Resource {
        //TODO: implement
    }

    fun calculateNextBases() {
        //TODO: implement

        // recalculates the distances of the other bases to this base
        // updates 'nextBases' with new distances and sorts the list
        // this calculation has to be made once per tick since new events might change the distances of the next bases
    }

    fun getNextBase(b: Base): Base? {
        //TODO: implement
    }

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
  