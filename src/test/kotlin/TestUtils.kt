
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation

class TestUtils {
    fun clearEMCC() {
        EMCC.ambulanceDepartment = null
        EMCC.fireDepartment = null
        EMCC.policeDepartment = null
        EMCC.handledEmergencies.clear()
        EMCC.startingEmergencies.clear()
        EMCC.startingEvents.clear()
        EMCC.activeEvents.clear()
    }

    fun clearSimulation() {
        Simulation.emergencies.clear()
        Simulation.events.clear()
        Simulation.map = GraphMap()
    }

    fun clear() {
        clearEMCC()
        clearSimulation()
    }
}