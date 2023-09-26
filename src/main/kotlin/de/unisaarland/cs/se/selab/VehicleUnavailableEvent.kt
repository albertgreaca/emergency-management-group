package de.unisaarland.cs.se.selab

class VehicleUnavailableEvent(
    override val id: Int,
    override var tick: Int,
    override var duration: Int,
    var vehicle: Vehicle
) : Event(
    id,
    tick,
    duration
) {
    override fun executeStart(): Boolean {
        if (vehicle.available) {
            vehicle.available = false
            EMCC.moveFromStartingToActive(this)
            Logger.logEventTriggered(id)
            return true
        }
        tick++
        return false
    }

    override fun stopEvent() {
        vehicle.available = true
        Logger.logEventEnded(id)
    }
}
