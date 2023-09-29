
import de.unisaarland.cs.se.selab.*
import org.junit.jupiter.api.BeforeEach
import java.io.File


class BaseTest {
    @BeforeEach
    val graph = GraphMap()
    val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
    val jsonparse = JsonParser(graph, File("src/test/resources/UnitTestConfig2/firebasesimple.json"),File("src/test/resources/UnitTestConfig2/emergencysimple.json"))
    val vertex1 = requireNotNull(graph.getVertex(0))
    val vertex2 = requireNotNull(graph.getVertex(1))
    val road = requireNotNull(graph.getRoad(vertex1,vertex2)
    var vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_WATER, Vehicle)
    var res = Resource()
    val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20 )
}
