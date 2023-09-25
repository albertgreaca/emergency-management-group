package de.unisaarland.cs.se.selab

/**
 * Resource class to save needs for emergency
 */
class Resource(
    private val vehicles: MutableList<VehicleType>,
    private var waterAmount: Int,
    private var criminalAmount: Int,
    private var patientAmount: Int,
    private var ladderLength: Int?
) {
    /**
     * @returns Vehicles
     */
    fun getVehicles(): MutableList<VehicleType> {
        return vehicles
    }

    /**
     * @return WaterAmount
     */
    fun getWaterAmount(): Int {
        return waterAmount
    }

    /**
     * @return CriminalAmount
     */
    fun getCriminalAmount(): Int {
        return criminalAmount
    }

    /**
     * @return PatientAmount
     */
    fun getPatientAmount(): Int {
        return patientAmount
    }

    /**
     * @return ladderLength
     */
    fun getLadderLength(): Int? {
        return ladderLength
    }

    /**
     * check if Resource is empty
     */
    fun isEmpty(): Boolean {
        // how about the ladder length here?
        return vehicles.isEmpty() && (waterAmount == 0) && (criminalAmount == 0) && (patientAmount == 0)
    }

    /**
     * add Vehicles to List of Vehicles
     */
    fun addVehicle(v: VehicleType) {
        vehicles.add(v)
    }

    /**
     * comparison of two resources,
     * @return the stuff that is still missing
     */
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
        // the weird let thing was a detekt fix I have no clue what it does
        val ladderDifference = resource.getLadderLength()?.let { this.getLadderLength()?.minus(it) }
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
        return Resource(diffNeededVehicles, waterDifference, criminalDifference, patientDifference, ladderDifference)
    }

    /**
     * helper function returning the difference of two lists
     */
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
