package de.unisaarland.cs.se.selab
class RushHourEvent(
    private val id: Int,
    private var tick: Int,
    private var duration: Int,
    private var roads: MutableList<Road>,
    private val factor: Int
) : Event(
    id,
    tick,
    duration
) {
    fun executeStart(): Boolean{

    }

    fun updateEvent(): Boolean{
        if(tick+duration == )
    }
}
