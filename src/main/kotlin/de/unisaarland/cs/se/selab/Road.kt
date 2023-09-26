package de.unisaarland.cs.se.selab

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

    fun getActualWeight(): Int {
        // TODO
        return 0
    }
    fun getCurrentEvent(): Event? {
        if (eventList.isEmpty()) {
            return null
        }
        return eventList[0]
    }
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
    fun addEvent(ev: Event) {
        eventList.add(ev)
    }
    fun removeEvent(ev: Event) {
        eventList.remove(ev)
    }
}
