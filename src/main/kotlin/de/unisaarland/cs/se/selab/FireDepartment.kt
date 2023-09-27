package de.unisaarland.cs.se.selab

/**
 * Class representing all Fire bases
 */
class FireDepartment : EmergencyObserver {
    override val bases: MutableList<Base> = mutableListOf()

    /**
     * add a Base to the List of Fire Stations
     */
    override fun addBase(b: Base) {
        bases.add(b)
    }

    /**
     * find Base via Index
     */
    override fun findBase(id: Int): Base? {
        for (b in bases) {
            if (b.id == id) return b
        }
        return null
    }

    /**
     * assigns closest base to the given emergency and logs the assignment
     */
    override fun update(em: Emergency) {
        em.base = Dijkstra.dijkstraEmergency(em.road.start.id, em.road.end.id, em.type)
        if (em.base != null) {
            Logger.logEmergencyAssignment(em.id, em.base!!.id)
        }
    }
}
