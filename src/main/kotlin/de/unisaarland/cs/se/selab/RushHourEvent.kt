package de.unisaarland.cs.se.selab
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
    override fun executeStart(): Boolean {
        for (road in roads) {
            if (road.eventList.isEmpty()) {
                for (albert in roads) {
                    albert.addEvent(this)
                }
                EMCC.moveFromStartingToActive(this)
                return true
            }
        }
        tick++
        return false
    }

    override fun stopEvent() {
        for (road in roads) {
            road.eventList.remove(this)
        }
        Logger.logEventEnded(id)
    }
}

