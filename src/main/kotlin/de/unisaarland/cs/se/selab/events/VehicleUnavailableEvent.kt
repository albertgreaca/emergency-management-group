package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.Logger
import de.unisaarland.cs.se.selab.Vehicle

/** Class for the vehicle unavailable event
 */
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
    /**
     * starts event for the first time
     * makes vehicle unavailable if already possible
     */
    override fun executeStart(): Boolean {
        if (vehicle.available) {
            vehicle.available = false
            Logger.logEventTriggered(id)
            return true
        }
        tick++
        return false
    }

    /**
     *stops the event
     * makes vehicle available again
     * logs
     */
    override fun stopEvent() {
        vehicle.available = true
        Logger.logEventEnded(id)
    }
}
