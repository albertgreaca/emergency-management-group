package de.unisaarland.cs.se.selab

/**
 * Class representing all Fire bases
 */
class FireDepartment(val fireStations: MutableList<Base>) : EmergencyObserver {
    /**
     * add a Base to the List of Fire Stations
     */
    override fun addBase(b: Base) {
        fireStations.add(b)
    }

    /**
     * find Base via Index
     */
    override fun findBase(id: Int): Base? {
        for (b in fireStations) {
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
