package de.unisaarland.cs.se.selab


class Resource(private val vehicles: MutableList<Pair<VehicleType, Int?>>, private var waterAmount: Int,
               private var criminalAmount: Int, private var patientAmount: Int) {

    public fun getVehicles(): MutableList<Pair<VehicleType, Int?>> {
        return vehicles
    }

    public fun getWaterAmount(): Int {
        return waterAmount
    }

   public fun getCriminalAmount(): Int {
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
//TODO
    }
}