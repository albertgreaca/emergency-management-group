package de.unisaarland.cs.se.selab

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

    var eventList: MutableList<Event> = mutableListOf()

    /**
     * @return the actual weight depending on the event
     */
    fun getActualWeight(): Int {
        // TODO
        return weight + height
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
}
