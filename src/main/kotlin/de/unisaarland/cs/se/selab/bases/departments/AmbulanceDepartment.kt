package de.unisaarland.cs.se.selab.bases.departments

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.utils.Logger

/**
 * Representing all Hospital Bases
 */
class AmbulanceDepartment : EmergencyObserver {
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
            Logger.logEmergencyAssignment(em.id, requireNotNull(em.base).id)
        }
    }
}
