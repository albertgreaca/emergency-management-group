package de.unisaarland.cs.se.selab

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
