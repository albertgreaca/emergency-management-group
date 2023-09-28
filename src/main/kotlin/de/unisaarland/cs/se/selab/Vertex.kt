package de.unisaarland.cs.se.selab

/**
 * Class for representing Vertices of Graph
 */
class Vertex(val id: Int, var base: Base?, val realid: Int) {
    /**
     * check equality
     */
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Vertex -> id == other.id
            else -> false
        }
    }

    /**
     * hashing
     */
    override fun hashCode(): Int {
        return id
    }
}
