import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.utils.Position
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class DijkstraTest {

    @BeforeEach
    fun restore() {
        val utils = TestUtils()
        utils.clear()
    }

    @Test
    fun dijkstraHeight() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        parser.parseMap()
        val road = requireNotNull(Simulation.map.getRoad("Campus", "Chemistry"))
        val posdijkstra: Position = requireNotNull(Dijkstra.dijkstraHeight(0, road, 5))
        val road1 = requireNotNull(Simulation.map.getRoad("Campus", "Medieninformatik"))
        val road2 = requireNotNull(Simulation.map.getRoad("UdS", "Naturkunst"))
        val road3 = requireNotNull(Simulation.map.getRoad("UdS", "Biochemie"))
        val vertex0 = Simulation.map.getVertexFromId(1) ?: return
        val vertex1 = Simulation.map.getVertexFromId(3) ?: return
        val vertex2 = Simulation.map.getVertexFromId(2) ?: return
        val vertex3 = Simulation.map.getVertexFromId(4) ?: return
        val vertexList = mutableListOf(vertex0, vertex1, vertex2, vertex3)
        val roadList = mutableListOf(road1, road2, road3)
        val posway: Position = Position(roadList, vertexList, 0, 7, vertex1, 25, 3, false, false)

        assertTrue(posdijkstra.isEqual(posway))
    }
}
