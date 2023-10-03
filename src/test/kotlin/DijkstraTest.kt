import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.events.TrafficJamEvent
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.utils.Position
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
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

    @Test
    fun dijkstraEmergencyTest() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        parser.parseMap()
        val jsonParser = JsonParser(
            Simulation.map,
            File("src/systemtest/resources/assetsJsons/AssetsEmergencySimple.json"),
            File("src/systemtest/resources/scenarioJsons/ScenarioEmergencySimple.json")
        )
        jsonParser.parseBases()
        val baseDijkstra = Dijkstra.dijkstraEmergency(3, 4, EmergencyType.FIRE) ?: return
        val base = EMCC.fireDepartment?.bases?.get(0) ?: return
        assertEquals(base.id, baseDijkstra.id)
    }

    @Test
    fun dijkstraRequestTest() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        parser.parseMap()
        val jsonParser = JsonParser(
            Simulation.map,
            File("src/systemtest/resources/assetsJsons/AssetsEmergencySimple.json"),
            File("src/systemtest/resources/scenarioJsons/ScenarioEmergencySimple.json")
        )
        jsonParser.parseBases()
        val baseListComparable = mutableListOf(
            requireNotNull(EMCC.fireDepartment?.bases?.get(0)),
            requireNotNull(EMCC.policeDepartment?.bases?.get(0)),
            requireNotNull(EMCC.ambulanceDepartment?.bases?.get(0))
        )
        val baseListDijkstra = Dijkstra.dijkstraRequest(0)
        assertEquals(baseListDijkstra, baseListComparable)
    }

    @Test
    fun dijkstraRerouteTest() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        parser.parseMap()
        val jsonParser = JsonParser(
            Simulation.map,
            File("src/systemtest/resources/assetsJsons/AssetsEmergencySimple.json"),
            File("src/systemtest/resources/scenarioJsons/ScenarioEmergencySimple.json")
        )
        jsonParser.parseBases()
        val startroad = Simulation.map.getRoad("Campus", "Chemistry") ?: return
        val endRoad = Simulation.map.getRoad("Campus", "Bioinfostr") ?: return
        val vertex = Simulation.map.getVertexFromId(4) ?: return

        val road2 = requireNotNull(Simulation.map.getRoad("UdS", "Biochemie"))
        val roadList = mutableListOf(startroad, road2)

        val vertex1 = requireNotNull(Simulation.map.getVertexFromId(4))
        val vertex2 = requireNotNull(Simulation.map.getVertexFromId(2))
        val vertexList = mutableListOf(vertex1, vertex2)

        val roadEvent = requireNotNull(Simulation.map.getRoad("UdS", "Art"))
        val event = TrafficJamEvent(50, 1, 50, roadEvent, 60)
        roadEvent.eventList.add(event)

        val posDijkstra = Dijkstra.dijkstraReroute(startroad, 8, 10, vertex, endRoad, 2)
        val posComparable = Position(roadList, vertexList, 10, 8, vertex, 22, 3, false, false)

        assertTrue(posDijkstra.isEqual(posComparable))
    }

    @Test
    fun dijkstraRerouteBackToBaseTest() {
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        parser.parseMap()
        val jsonParser = JsonParser(
            Simulation.map,
            File("src/systemtest/resources/assetsJsons/AssetsEmergencySimple.json"),
            File("src/systemtest/resources/scenarioJsons/ScenarioEmergencySimple.json")
        )
        jsonParser.parseBases()
        val startroad = Simulation.map.getRoad("Campus", "Chemistry") ?: return
        val endRoad = Simulation.map.getRoad("Campus", "Bioinfostr") ?: return
        val vertex = Simulation.map.getVertexFromId(4) ?: return

        val road2 = requireNotNull(Simulation.map.getRoad("UdS", "Biochemie"))
        val roadList = mutableListOf(startroad, road2)

        val vertex1 = requireNotNull(Simulation.map.getVertexFromId(4))
        val vertex2 = requireNotNull(Simulation.map.getVertexFromId(2))
        val vertexList = mutableListOf(vertex1, vertex2)

        val roadEvent = requireNotNull(Simulation.map.getRoad("UdS", "Art"))
        val event = TrafficJamEvent(50, 1, 50, roadEvent, 60)
        roadEvent.eventList.add(event)

        val posDijkstra = requireNotNull(Dijkstra.dijkstraRerouteBackToBase(startroad, 8, 10, vertex, 1, 2))
        val posComparable = Position(roadList, vertexList, 10, 8, vertex, 22, 3, false, false)

        assertTrue(posDijkstra.isEqual(posComparable))
    }
}
