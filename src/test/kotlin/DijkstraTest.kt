import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.utils.Position
import org.junit.jupiter.api.Test

class DijkstraTest {

    @Test
    fun dijkstraHeight() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        val road = Simulation.map.getRoad("Campus", "Chemistry") ?: return
        val posdijkstra: Position? = Dijkstra.dijkstraHeight(1, road, 5)
        val roadList = mutableListOf<Road>()
        // val posway: Position = Position(mutableListOf(), )


    }
}