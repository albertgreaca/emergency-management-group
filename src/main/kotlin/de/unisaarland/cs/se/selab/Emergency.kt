package de.unisaarland.cs.se.selab

class Emergency(private val id: Int, private val tick: Int, private val road: Road, private val type: EmergencyType,
                private val severity: Int, private val handleTime: Int, private val maxDuration: Int, private var resources: Resource) {

    private var base: Base? = null
    private val assignedVehicles: MutableList<Vehicle> = mutableListOf()

    fun getId(): Int {
        return id
    }

    fun getTick(): Int {
        return tick
    }

    fun getBase(): Base? {
        return base
    }

    fun getRoad(): Road {
        return road
    }

    fun getType(): EmergencyType {
        return type
    }

    fun getSeverity(): Int {
        return severity
    }

    fun getHandleTime(): Int {
        return handleTime
    }

    fun getMaxDuration(): Int {
        return maxDuration
    }

    fun getResources(): Resource {
        return resources
    }

    fun getAssignedVehicles(): MutableList<Vehicle> {
        return assignedVehicles
    }

    fun addVehicle(v: Vehicle): {
        assignedVehicles.add(v)
    }

    fun removeVehicle(v: Vehicle): {
        assignedVehicles.remove(v)
    }

    fun updateEmergency() {
        //TODO
    }
}