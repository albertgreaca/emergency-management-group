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
     * assigns closest base to the given emergency and logs the assignment
     */
    override fun update(em: Emergency) {
        em.base = Dijkstra.dijkstraEmergency(em.road.start.id, em.road.end.id, em.type)
        if (em.base != null) {
            Logger.logEmergencyAssignment(em.id, em.base!!.getId())
        }
    }
}
