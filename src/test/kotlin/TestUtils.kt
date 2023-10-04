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
        EMCC.requests.clear()
        EMCC.resolvedOrFailedEmergencies.clear()
    }

    fun clearSimulation() {
        Simulation.emergencies.clear()
        Simulation.events.clear()
        Simulation.map = GraphMap()
        Simulation.maximumTicks = null
        Simulation.statistics.resolvedEmergencies = 0
        Simulation.statistics.receivedEmergencies = 0
        Simulation.statistics.ongoingEmergencies = 0
        Simulation.statistics.reroutedAssets = 0
        Simulation.statistics.failedEmergencies = 0
    }

    fun clear() {
        clearEMCC()
        clearSimulation()
    }
}
