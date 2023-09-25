package de.unisaarland.cs.se.selab

/** Class for the Emergencies
 */
class Emergency(
    private val id: Int,
    private val tick: Int,
    private val road: Road,
    private val type: EmergencyType,
    private val severity: Int,
    private val handleTime: Int,
    private val maxDuration: Int,
    private var resources: Resource
) {

    private var base: Base? = null
    private val assignedVehicles: MutableList<Vehicle> = mutableListOf()

    /**
     * @return Id
     */
    fun getId(): Int {
        return id
    }

    /**
     * @return tick
     */
    fun getTick(): Int {
        return tick
    }

    /**
     * @return Base if already assigned
     */
    fun getBase(): Base? {
        return base
    }

    /**
     * @return road where the emergency takes place
     */
    fun getRoad(): Road {
        return road
    }

    /**
     * @return Type of the emergency
     */
    fun getType(): EmergencyType {
        return type
    }

    /**
     * @return severity
     */
    fun getSeverity(): Int {
        return severity
    }

    /**
     * @return handle time
     */
    fun getHandleTime(): Int {
        return handleTime
    }

    /**
     * @return max. duration
     */
    fun getMaxDuration(): Int {
        return maxDuration
    }

    /**
     * @return needed resources
     */
    fun getResources(): Resource {
        return resources
    }

    /**
     * @return assigned vehicles as a list
     */
    fun getAssignedVehicles(): MutableList<Vehicle> {
        return assignedVehicles
    }

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
        // TODO
    }
}
