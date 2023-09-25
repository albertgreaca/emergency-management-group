package de.unisaarland.cs.se.selab

class Resource(
    private val vehicles: MutableList<VehicleType>,
    private var waterAmount: Int,
    private var criminalAmount: Int,
    private var patientAmount: Int,
    private var ladderLength: Int?
) {

    fun getVehicles(): MutableList<VehicleType> {
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

    fun addVehicle(v: VehicleType) {
        vehicles.add(v)
    }

    fun updateDifference(resource: Resource): Resource {
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
        // compare Ladder Length
        // TODO: implement ladder difference
        // val ladderDifference = this.
        // compare List of needed Vehicles
        // am I supposed to create a new resource?
        // what is the Int for?
        var originalNeededVehicles = this.getVehicles()
        var newNeededVehicles = resource.getVehicles()
        var diffNeededVehicles: MutableList<VehicleType>
        if (originalNeededVehicles.size >= newNeededVehicles.size) {
            diffNeededVehicles = listDifference(originalNeededVehicles, newNeededVehicles)
        } else {
            diffNeededVehicles = listDifference(newNeededVehicles, originalNeededVehicles)
        }
        return Resource(diffNeededVehicles, waterDifference, criminalDifference, patientDifference, null)
    }

    fun listDifference(
        firstList: MutableList<VehicleType>,
        secondList: MutableList<VehicleType>
    ): MutableList<VehicleType> {
        var resultList: MutableList<VehicleType> = mutableListOf()
        if (firstList.isEmpty()) {
            return resultList
        }
        for (vehicle in firstList) {
            if (secondList.contains(vehicle)) {
                secondList.remove(vehicle)
            } else {
                resultList.add(vehicle)
            }
        }
        return resultList
    }
}
