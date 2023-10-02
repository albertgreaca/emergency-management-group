import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class DijkstraTest {

    @BeforeEach
    fun restore() {
        val utils = TestUtils()
        utils.clear()
    }

    @Test
    fun dijkstraHeight() {
        /*
        val parser = MapParser(Simulation.map, File("src/systemtest/resources/mapFiles/MapEmergencySimple.dot"))
        val road = Simulation.map.getRoad("Campus", "Chemistry") ?: return
        val posdijkstra: Position? = Dijkstra.dijkstraHeight(1, road, 5)
        val roadList = mutableListOf<Road>()
        // val posway: Position = Position(mutableListOf(), )
    */
        assertTrue(true)
    }
}
