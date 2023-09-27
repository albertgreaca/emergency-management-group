package de.unisaarland.cs.se.selab

/**
 * Class representing all Police bases
 */
class PoliceDepartment : EmergencyObserver {
    override val bases: MutableList<Base> = mutableListOf()

    /**
     * function to add a base to the Department
     */
    override fun addBase(b: Base) {
        bases.add(b)
    }

    /**
     * function to find a Base via the ID
     */
    override fun findBase(id: Int): Base? {
        for (b in bases) {
            if (b.getId() == id) return b
        }
        return null
    }

    /**
     * gets the Emergencies and give it to the responsible Bases
     */
    override fun update(list: MutableList<Emergency>) {
        for (e in list) {
            e.base = Dijkstra.dijkstraEmergency(e.road.start.id, e.road.end.id, e.type)
        }
    }
}
