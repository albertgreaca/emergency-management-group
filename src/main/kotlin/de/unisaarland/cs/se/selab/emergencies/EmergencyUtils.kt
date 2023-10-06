package de.unisaarland.cs.se.selab.emergencies

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType

/**
 * this class contains helper functions for the Emergency class
 */
class EmergencyUtils {

    companion object {
        const val divisor = 300
    }

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

    private fun updateWaterTruckResources(em: Emergency) {
        val waterTrucks2 = em.assignedVehicles.filter { it is FireTruckWater }.sortedBy { it.id }
        if (!waterTrucks2.isEmpty()) {
            var l = 0
            val waterTrucks = waterTrucks2 as MutableList<FireTruckWater>
            var waterToDistribute = em.originalResources.waterAmount
            while (waterToDistribute > 0) {
                if (waterToDistribute >= waterTrucks[l].waterTransported) {
                    waterToDistribute -= waterTrucks[l].waterTransported
                    waterTrucks[l].waterTransported = 0
                    l++
                } else {
                    waterTrucks[l].waterTransported -= waterToDistribute
                    waterToDistribute = 0
                }
            }
        }
    }

    private fun updatePoliceCarResources(em: Emergency) {
        val policeCars2 = em.assignedVehicles.filter { it is PoliceCar }.sortedBy { it.id }
        if (!policeCars2.isEmpty()) {
            val policeCars = policeCars2 as MutableList<PoliceCar>
            var criminalsToDistribute = em.originalResources.criminalAmount
            var l = 0
            while (criminalsToDistribute > 0) {
                if (criminalsToDistribute >= policeCars[l].criminalsStillFitting) {
                    criminalsToDistribute -= policeCars[l].criminalsStillFitting
                    policeCars[l].transportedCriminals = policeCars[l].criminalCapacity
                    l++
                } else {
                    policeCars[l].transportedCriminals += criminalsToDistribute
                    criminalsToDistribute = 0
                }
            }
        }
    }

    private fun updateAmbulanceResources(em: Emergency) {
        val ambulances2 = em.assignedVehicles.filter { it is Ambulance }.sortedBy { it.id }
        if (!ambulances2.isEmpty()) {
            val ambulances = ambulances2 as MutableList<Ambulance>
            var l = 0
            var patientsToDistribute = em.originalResources.patientAmount
            while (patientsToDistribute > 0) {
                if (!ambulances[l].patientOnBoard) {
                    patientsToDistribute -= 1
                    ambulances[l].patientOnBoard = true
                }
                l++
            }
        }
    }

    /**
     *
     */
    fun updateResourcesOfAssets(em: Emergency) {
        // water
        updateWaterTruckResources(em)
        // criminals
        updatePoliceCarResources(em)
        // patients
        updateAmbulanceResources(em)
    }

    private fun updateAmbulanceBaseWaitingTicks(em: Emergency) {
        val fullAmbulances = em.assignedVehicles.filter { it is Ambulance && it.patientOnBoard }
        for (a in fullAmbulances) {
            a.baseWaitingTicks = 2
        }
    }

    private fun updateWaterTruckBaseWaitingTicks(em: Emergency) {
        val emptyWaterTrucks = em.assignedVehicles.filter { it is FireTruckWater && it.waterTransported == 0 }
            as List<FireTruckWater>
        for (a in emptyWaterTrucks) {
            a.baseWaitingTicks = a.waterCapacity / divisor + 1
        }
    }

    private fun updatePoliceCarBaseWaitingTicks(em: Emergency) {
        val fullPoliceCars = em.assignedVehicles.filter { it is PoliceCar && it.criminalsStillFitting == 0 }
            as List<PoliceCar>
        for (a in fullPoliceCars) {
            a.baseWaitingTicks = 3
        }
    }

    /**
     *
     */
    fun updateBaseWaitingTicksOfAssets(em: Emergency) {
        updateAmbulanceBaseWaitingTicks(em)
        updatePoliceCarBaseWaitingTicks(em)
        updateWaterTruckBaseWaitingTicks(em)
    }

    /**
     *
     */
    fun checkCombinationDecider(em: Emergency, cur: MutableList<Vehicle>, withArrivalTime: Boolean, b: Base): Boolean {
        return if (withArrivalTime) b.checkCombination(em, cur) else b.checkCombinationWithoutArrivalTime(em, cur)
    }
}
