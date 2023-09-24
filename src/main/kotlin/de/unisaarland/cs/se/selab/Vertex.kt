package de.unisaarland.cs.se.selab

class Vertex (private val id: Int, private var base: Base?){
    fun getId(): Int {
        return id
    }
    fun getBase(): Base? {
        return base
    }
    fun setBase(base: Base){
        this.base = base
    }
}