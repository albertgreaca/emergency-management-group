package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.events.ConstructionSiteEvent
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.events.RoadClosureEvent
import de.unisaarland.cs.se.selab.events.RushHourEvent
import de.unisaarland.cs.se.selab.events.TrafficJamEvent

/**
 * Class representing Edges/Roads
 */
class Road(
    var primType: PrimaryRoadType,
    var secType: SecondaryRoadType,
    var village: String,
    var name: String,
    var weight: Int,
    var height: Int,
    var start: Vertex,
    var end: Vertex
) {

    val eventList: MutableList<Event> = mutableListOf()

    /**
     * @return the actual weight depending on the event
     */
    fun getActualWeight(): Int {
        if (eventList.isEmpty()) {
            return weight
        }
        val ev = eventList[0]
        var factor = 1
        if (ev is ConstructionSiteEvent) {
            factor = ev.factor
        }
        if (ev is RushHourEvent) {
            factor = ev.factor
        }
        if (ev is TrafficJamEvent) {
            factor = ev.factor
        }
        if (ev is RoadClosureEvent) {
            return Integer.MIN_VALUE
        }
        return factor * weight
    }

    /**
     * @return the current event (first element of list) null if no event is active
     */
    fun getCurrentEvent(): Event? {
        if (eventList.isEmpty()) {
            return null
        }
        return eventList[0]
    }

    /**
     * Setting all necessary attributes for a road
     */
    fun setAttributes(
        prim: PrimaryRoadType,
        sec: SecondaryRoadType,
        vil: String,
        name: String,
        weight: Int,
        height: Int
    ) {
        this.primType = prim
        this.secType = sec
        this.village = vil
        this.name = name
        this.weight = weight
        this.height = height
    }

    /**
     * Adding Event to list
     */
    fun addEvent(ev: Event) {
        eventList.add(ev)
    }

    /**
     * Removing event from list
     */
    fun removeEvent(ev: Event) {
        eventList.remove(ev)
    }

    /**
     * check equality
     */
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Road -> {
                other.primType == primType && other.secType == secType && other.village == village &&
                    other.name == name && other.weight == weight && other.height == height &&
                    other.start == start && other.end == end
            }

            else -> false
        }
    }

    /**
     * hashing
     */
    override fun hashCode(): Int {
        return 1
    }
}
