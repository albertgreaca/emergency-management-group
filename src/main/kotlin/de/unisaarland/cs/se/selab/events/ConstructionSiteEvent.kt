package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.utils.Logger

/** Class for the construction site event
 */
class ConstructionSiteEvent(
    override val id: Int,
    override var tick: Int,
    override var duration: Int,
    val oneWayStreet: Boolean,
    var road: Road,
    val factor: Int
) : Event(
    id,
    tick,
    duration
) {
    /**
     * starts event for the first time
     * return true if event could be started
     * check if event can be applied on the road
     */
    override fun executeStart(): Boolean {
        if (road.eventList.isEmpty()) {
            road.addEvent(this)
            Logger.logEventTriggered(id)
            return true
        }
        tick++
        return false
    }

    /**
     *stops the event
     * removes event from the list of events in the road
     * logs
     */
    override fun stopEvent() {
        road.eventList.remove(this)
        Logger.logEventEnded(id)
    }
}
