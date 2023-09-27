package de.unisaarland.cs.se.selab

/**
 * Representing all Hospital Bases
 */
class AmbulanceDepartment(val hospitals: MutableList<Base>) : EmergencyObserver {

    /**
     * function to add a base to the Department
     */
    override fun addBase(b: Base) {
        hospitals.add(b)
    }

    /**
     * function to find a Base via the ID
     */
    override fun findBase(id: Int): Base? {
        for (b in hospitals) {
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
