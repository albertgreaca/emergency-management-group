package de.unisaarland.cs.se.selab.emergencies

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType

/** Class for the Emergencies
 */
class Emergency(
    val id: Int,
    val tick: Int,
    val road: Road,
    val type: EmergencyType,
    val severity: Int,
    val handleTime: Int,
    val maxDuration: Int,
    var originalResources: Resource
) {

    var base: Base? = null
    val assignedVehicles: MutableList<Vehicle> = mutableListOf()
    var alreadyHandled: Int = 0
    var handlingStarted: Boolean = false
    var resolved: Boolean = false
    var firsttick: Boolean = true
    var currentResources =
        Resource(
            originalResources.vehicles.toMutableList(),
            originalResources.waterAmount,
            originalResources.criminalAmount,
            originalResources.patientAmount,
            originalResources.patientAmount
        )

    /**
     * @return adds a vehicle to the vehicle list
     */
    fun addVehicle(v: Vehicle) {
        assignedVehicles.add(v)
    }

    /**
     * @return removes a vehicle from the vehicle list
     */
    fun removeVehicle(v: Vehicle): VehicleType {
        assignedVehicles.remove(v)
        return v.vehicleType
    }
}
