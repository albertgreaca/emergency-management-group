package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.vehicles.Vehicle

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
        vehicle.affectedByEvent = true
        if (vehicle.available) {
            vehicle.available = false
            Logger.logEventTriggered(id)
            return true
        }
        postponed = true
        tick++
        return false
    }

    /**
     *stops the event
     * makes vehicle available again
     * logs
     */
    override fun stopEvent() {
        vehicle.affectedByEvent = false
        postponed = false
        vehicle.available = true
        Logger.logEventEnded(id)
    }
}
