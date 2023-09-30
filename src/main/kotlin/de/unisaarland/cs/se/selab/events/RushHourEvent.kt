package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.utils.Logger

/** Class for the Rush hour event
 */
class RushHourEvent(
    override val id: Int,
    override var tick: Int,
    override var duration: Int,
    var roads: MutableList<Road>,
    val factor: Int
) : Event(
    id,
    tick,
    duration
) {

    /**
     * starts event for the first time
     * return true if event could be started
     * check if event can be applied on at least one road
     * if yes adds to other ones
     */
    override fun executeStart(): Boolean {
        for (road in roads) {
            if (road.eventList.isEmpty()) {
                for (albert in roads) {
                    albert.addEvent(this)
                }
                return true
            }
        }
        tick++
        return false
    }

    /**
     *stops the event
     * removes event from the list of events in the roads
     * logs
     */
    override fun stopEvent() {
        for (road in roads) {
            road.eventList.remove(this)
        }
        Logger.logEventEnded(id)
    }
}
