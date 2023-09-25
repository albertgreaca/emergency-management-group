class Resource(
    private val vehicles: MutableList<Pair<VehicleType, Int?>>,
    private var waterAmount: Int,
    private var criminalAmount: Int,
    private var patientAmount: Int
) {

    fun getVehicles(): MutableList<Pair<VehicleType, Int?>> {
        return vehicles
    }

    fun getWaterAmount(): Int {
        return waterAmount
    }

    fun getCriminalAmount(): Int {
        return criminalAmount
    }

    fun getPatientAmount(): Int {
        return patientAmount
    }

    fun isEmpty(): Boolean {
        return vehicles.isEmpty() && (waterAmount == 0) && (criminalAmount == 0) && (patientAmount == 0)
    }

    fun addVehicle(v: Pair<VehicleType, Int?>) {
        vehicles.add(v)
    }

    fun updateDifference(resource: Resource) {
        // TODO
        // compare WaterAmount
        // this assumes first resource always has more?
        // abs fix?
        val waterDifference = this.getWaterAmount() - resource.getWaterAmount()
        // compare CriminalAmount
        // abs fix?
        val criminalDifference = this.getCriminalAmount() - resource.getCriminalAmount()
        // compare patientAmount
        // abs fix?
        val patientDifference = this.getPatientAmount() - resource.getPatientAmount()
        // compare List of needed Vehicles
        // am I supposed to create a new resource?
        // what is the Int for?
        var originalNeededVehicles = this.getVehicles()
        var newNeededVehicles = resource.getVehicles()
    }
}
