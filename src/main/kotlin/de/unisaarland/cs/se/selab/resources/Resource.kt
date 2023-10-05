package de.unisaarland.cs.se.selab.resources

import de.unisaarland.cs.se.selab.vehicles.VehicleType
import kotlin.math.max

/**
 * Resource class to save needs for emergency
 */
class Resource(
    var vehicles: MutableList<VehicleType>,
    var waterAmount: Int,
    var criminalAmount: Int,
    var patientAmount: Int,
    var ladderLength: Int
) {

    /**
     * check if Resource is empty
     */
    fun isEmpty(): Boolean {
        // how about the ladder length here?
        return vehicles.isEmpty() && waterAmount == 0 && criminalAmount == 0 && patientAmount == 0
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
        val waterDifference = max(this.waterAmount - resource.waterAmount, 0)
        // compare CriminalAmount
        // abs fix?
        val criminalDifference = max(this.criminalAmount - resource.criminalAmount, 0)
        // compare patientAmount
        // abs fix?
        val patientDifference = max(this.patientAmount - resource.patientAmount, 0)
        // compare Ladder Length
        // the weird let thing was a detekt fix I have no clue what it does
        val ladderDifference = if (resource.ladderLength == 0) {
            0
        } else {
            this.ladderLength - resource.ladderLength
        }
        // compare List of needed Vehicles
        // am I supposed to create a new resource?
        // what is the Int for?
        val originalNeededVehicles = this.vehicles
        val newNeededVehicles = resource.vehicles
        val diffNeededVehicles: MutableList<VehicleType>
        diffNeededVehicles = if (originalNeededVehicles.size >= newNeededVehicles.size) {
            listDifference(originalNeededVehicles, newNeededVehicles)
        } else {
            listDifference(newNeededVehicles, originalNeededVehicles)
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
        val resultList: MutableList<VehicleType> = mutableListOf()
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

    /**
     * filters out all resources that police stations can provide
     */
    fun filterPoliceResources(): Resource {
        val policeResource = Resource(mutableListOf(), 0, this.criminalAmount, 0, 0)
        for (type in this.vehicles) {
            when (type) {
                VehicleType.POLICE_CAR, VehicleType.K9_POLICE_CAR, VehicleType.POLICE_MOTORCYCLE ->
                    policeResource.addVehicle(type)
                else -> {
                }
            }
        }
        return policeResource
    }

    /**
     * filters out all resources that fire stations can provide
     */
    fun filterFireResources(): Resource {
        val fireResource = Resource(mutableListOf(), this.waterAmount, 0, 0, this.ladderLength)
        for (type in this.vehicles) {
            when (type) {
                VehicleType.FIREFIGHTER_TRANSPORTER, VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_LADDER -> fireResource.addVehicle(type)
                else -> {
                }
            }
        }
        return fireResource
    }

    /**
     * filters out all resources that ambulance stations can provide
     */
    fun filterAmbulanceResources(): Resource {
        val ambulanceResource = Resource(mutableListOf(), 0, 0, this.patientAmount, 0)
        for (type in this.vehicles) {
            when (type) {
                VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR -> ambulanceResource.addVehicle(type)
                else -> {
                }
            }
        }
        return ambulanceResource
    }

    /**
     * filters out all resources that stations of the given kind can provide
     */
    fun filterResourcesOfKind(kind: Int): Resource? {
        return when (kind) {
            0 -> {
                filterFireResources()
            }
            1 -> {
                filterAmbulanceResources()
            }
            2 -> {
                filterPoliceResources()
            }
            else -> {
                null
            }
        }
    }

    /**
     * checks if equal
     */
    fun isEqual(other: Any?): Boolean {
        if (other is Resource) {
            val vehiclesEqual = this.resListisEqual(other)
            val waterEqual = this.waterAmount == other.waterAmount
            val patientEqual = this.patientAmount == other.patientAmount
            val criminalEqual = this.criminalAmount == other.criminalAmount
            val ladderEqual = this.ladderLength == other.ladderLength
            return vehiclesEqual && waterEqual && patientEqual && criminalEqual && ladderEqual
        }
        return false
    }

    private fun resListisEqual(compare: Resource): Boolean {
        val startlist = this.vehicles
        val comparelist = compare.vehicles
        val copylist = comparelist.toMutableList()
        if (!(startlist.size == comparelist.size)) {
            return false
        }
        for (vt in startlist) {
            for (type in comparelist) {
                if (vt == type) {
                    copylist.remove(type)
                }
            }
        }
        if (copylist.isEmpty()) {
            return true
        }
        return false
    }

    /**
     * counts instances of the given vehicle type
     */
    fun countInstancesOf(t: VehicleType): Int {
        var count = 0
        for (type in vehicles) {
            if (type == t) count++
        }
        return count
    }
}
