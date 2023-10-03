package de.unisaarland.cs.se.selab.emergencies

import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.VehicleType

/**
 * this class contains helper functions for the Emergency class
 */
class EmergencyUtils {

    /**
     * @returns number of patients that could be transported with the currently allocated vehicles
     */
    fun potentialPatients(em: Emergency): Int {
        var patientAmount = 0
        for (v in em.assignedVehicles) {
            if (v is Ambulance && !v.patientOnBoard) {
                patientAmount++
            }
        }
        return patientAmount
    }

    /**
     * @returns amount of water that could be transported with the currently allocated vehicles
     */
    fun potentialWater(em: Emergency): Int {
        var waterAmount = 0
        for (v in em.assignedVehicles) {
            if (v is FireTruckWater) {
                waterAmount += v.waterTransported
            }
        }
        return waterAmount
    }

    /**
     * @returns number of criminals that could be transported with the currently allocated vehicles
     */
    fun potentialCriminals(em: Emergency): Int {
        var criminalAmount = 0
        for (v in em.assignedVehicles) {
            if (v is PoliceCar) {
                criminalAmount += v.criminalsStillFitting
            }
        }
        return criminalAmount
    }

    /**
     * @returns list of allocated vehicle types (containing duplicates)
     */
    fun allocatedVehicleTypes(em: Emergency): MutableList<VehicleType> {
        val types: MutableList<VehicleType> = mutableListOf()
        for (v in em.assignedVehicles) {
            types.add(v.vehicleType)
        }
        return types
    }

    /**
     * @returns the
     */
    fun differenceNeededAllocated(em: Emergency): Resource {
        val patientsNeeded = em.originalResources.patientAmount - potentialPatients(em)
        val waterNeeded = em.originalResources.waterAmount - potentialWater(em)
        val criminalsNeeded = em.originalResources.criminalAmount - potentialCriminals(em)
        return Resource(
            em.currentResources.vehicles,
            waterNeeded,
            criminalsNeeded,
            patientsNeeded,
            em.currentResources.ladderLength
        )
    }
}
