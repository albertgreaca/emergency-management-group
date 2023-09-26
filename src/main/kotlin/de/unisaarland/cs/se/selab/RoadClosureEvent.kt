package de.unisaarland.cs.se.selab

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
    override fun executeStart(): Boolean {
        if (road.eventList.isEmpty()) {
            road.addEvent(this)
            EMCC.moveFromStartingToActive(this)
            Logger.logEventTriggered(id)
            return true
        }
        tick++
        return false
    }

    override fun stopEvent() {
        road.eventList.remove(this)
        Logger.logEventEnded(id)
    }
}
