package de.unisaarland.cs.se.selab

class Emergency {

    fun getResources(): Resource {
        return Resource(mutableListOf(),0,0,0)
    }
    fun getId(): Int {
        return -1;
    }
    fun getSeverity(): Int {
        return -1
    }

    fun getBase(): Base? {
        return null
    }
}