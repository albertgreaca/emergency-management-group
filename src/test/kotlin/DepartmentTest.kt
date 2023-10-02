
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.io.File

class DepartmentTest {
    // @BeforeEach
    // fun before() {

    // }

    @Test
    fun testFindBaseFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fireDep = EMCC.fireDepartment

        assertEquals(fireDep?.findBase(0)?.id, 0)
    }

    @Test
    fun testFindBaseNotFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fireDep = EMCC.fireDepartment

        assertNull(fireDep?.findBase(1)?.id)
    }
}
