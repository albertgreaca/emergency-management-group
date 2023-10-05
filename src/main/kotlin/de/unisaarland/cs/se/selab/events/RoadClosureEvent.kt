package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.utils.Logger

/** Class for the road closure event
 */
class RoadClosureEvent(
    override val id: Int,
    override var tick: Int,
    override var duration: Int,
    var road: Road
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
            Simulation.map.removeRoad(road.start.id, road.end.id, false)
            Logger.logEventTriggered(id)
            return true
        }
        postponed = true
        tick++
        return false
    }

    /**
     *stops the event
     * removes event from the list of events in the road
     * logs
     */
    override fun stopEvent() {
        postponed = false
        road.eventList.remove(this)
        Simulation.map.addRoad(road, road.start.id, road.end.id)
        Logger.logEventEnded(id)
    }
}
