package de.unisaarland.cs.se.selab

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
    var resources: Resource,
) {

    var base: Base? = null
    val assignedVehicles: MutableList<Vehicle> = mutableListOf()
    var handlingStarted: Boolean = false
    var resolved: Boolean = false

    /**
     * @return adds a vehicle to the vehicle list
     */
    fun addVehicle(v: Vehicle) {
        assignedVehicles.add(v)
    }

    /**
     * @return removes a vehicle from the vehicle list
     */
    fun removeVehicle(v: Vehicle) {
        assignedVehicles.remove(v)
    }

    /**
     * @return updates state of the emergency
     */
    fun updateEmergency() {
        TODO("implement")
    }
}
