package de.unisaarland.cs.se.selab
class RushHourEvent(
     val id: Int,
     var tick: Int,
     var duration: Int,
     var roads: MutableList<Road>,
     val factor: Int
) : Event(
    id,
    tick,
    duration
) {
    fun executeStart(): Boolean{
        for(road in roads){
            if()
        }

    }

    override fun updateEvent(): Boolean{
        if(tick+duration == Simulation.getCurrentTick()) {
            EMCC.getActiveEvents().remove(this)
            for (road in roads) {
                road.getev
            }
            Logger.logEventEnded(id)
            return true
        }
        return false
    }
}
