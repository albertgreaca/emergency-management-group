package de.unisaarland.cs.se.selab.bases.departments

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.utils.Logger

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
        em.base = Dijkstra.dijkstraEmergency(em.road.start.realid, em.road.end.realid, em.type)
        if (em.base != null) {
            Logger.logEmergencyAssignment(em.id, requireNotNull(em.base).id)
        }
    }
    override fun updatenextBases() {
        for (base in bases) {
            base.calculateNextBases()
        }
    }
    override fun updateVehicles() {
        for (b in bases) {
            for (v in b.vehicles) {
                if (v.baseWaitingTicks > 1) {
                    v.baseWaitingTicks--
                } else if (v.baseWaitingTicks == 1) {
                    v.available = true
                    v.baseWaitingTicks--
                }
            }
        }
    }
}
