package de.unisaarland.cs.se.selab.emergencies

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
    fun updateWaterTruckResources(em: Emergency) {
        val waterTrucks2 = em.assignedVehicles.filter { it is FireTruckWater }.sortedBy { it.id }
        if (!waterTrucks2.isEmpty()) {
            val waterTrucks = waterTrucks2 as MutableList<FireTruckWater>
            var waterToDistribute = em.originalResources.waterAmount
            while (waterToDistribute > 0) {
                if (waterToDistribute >= waterTrucks[0].waterTransported) {
                    waterToDistribute -= waterTrucks[0].waterTransported
                    waterTrucks[0].waterTransported = 0
                    waterTrucks.removeAt(0)
                } else {
                    waterTrucks[0].waterTransported -= waterToDistribute
                    waterToDistribute = 0
                }
            }
        }
    }

    /**
     * @returns the
     */
    fun updatePoliceCarResources(em: Emergency) {
        val policeCars2 = em.assignedVehicles.filter { it is PoliceCar }.sortedBy { it.id }
        if (!policeCars2.isEmpty()) {
            val policeCars = policeCars2 as MutableList<PoliceCar>
            var criminalsToDistribute = em.originalResources.criminalAmount
            while (criminalsToDistribute > 0) {
                if (criminalsToDistribute >= policeCars[0].criminalsStillFitting) {
                    criminalsToDistribute -= policeCars[0].criminalsStillFitting
                    policeCars[0].transportedCriminals = policeCars[0].criminalCapacity
                    policeCars.removeAt(0)
                } else {
                    policeCars[0].transportedCriminals += criminalsToDistribute
                    criminalsToDistribute = 0
                }
            }
        }
    }

    /**
     * @returns the
     */
    fun updateAmbulanceResources(em: Emergency) {
        val ambulances2 = em.assignedVehicles.filter { it is Ambulance }.sortedBy { it.id }
        if (!ambulances2.isEmpty()) {
            val ambulances = ambulances2 as MutableList<Ambulance>
            var patientsToDistribute = em.originalResources.patientAmount
            while (patientsToDistribute > 0) {
                if (!ambulances[0].patientOnBoard) {
                    patientsToDistribute -= 1
                    ambulances[0].patientOnBoard = true
                }
                ambulances.removeAt(0)
            }
        }
    }
}
