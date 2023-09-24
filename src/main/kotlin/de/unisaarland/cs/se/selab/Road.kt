package de.unisaarland.cs.se.selab

import PrimaryRoadType
import SecondaryRoadType
import Event

class Road (private var primType: PrimaryRoadType, private var secType: SecondaryRoadType, private var village: String,
            private var name: String, private var weight: Int,
            private var height: Int, private var start: Vertex, private var end: Vertex) {

    private var eventList: MutableList<Event> = mutableListOf()


    fun getPrimType(): PrimaryRoadType {
        return primType
    }
    fun getSecType(): SecondaryRoadType {
        return secType
    }
    fun getVillage(): String {
        return village
    }
    fun getName(): String {
        return name
    }
    fun getStart(): Vertex {
        return start
    }
    fun setStart(v: Vertex) {
        this.start = v
    }
    fun getEnd(): Vertex {
        return end
    }
    fun setEnd(v: Vertex) {
        this.end = v
    }
    fun getWeight(): Int {
        return weight
    }
    fun getHeight(): Int {
        return height
    }
    fun getActualWeight(): Int {
        //TODO
        return 0
    }
    fun getCurrentEvent(): Event? {
        if (eventList.isEmpty())
            return null
        return eventList[0]
    }
    fun setAttributes(prim: PrimaryRoadType, sec: SecondaryRoadType, vil: String, name: String, weight: Int, height: Int) {
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