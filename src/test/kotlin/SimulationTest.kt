
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class SimulationTest {

    val utils = TestUtils()

    @BeforeEach
    fun beforeEach() {
        utils.clear()
    }

    @Test
    fun testInitializeValid1() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )

        assertTrue(res)

        // check if map got parsed correctly
        assertEquals(4, Simulation.map.roadList.size)
        assertEquals(5, Simulation.map.vertexList.size)
        assertEquals(5, Simulation.map.adjacencyList.size)

        // check if bases and vehicles got parsed correctly
        assertEquals(3, EMCC.fireDepartment?.bases?.size)
        assertEquals(1, EMCC.fireDepartment?.bases!![0].vehicles.size)
        assertEquals(1, EMCC.fireDepartment?.bases!![1].vehicles.size)
        assertEquals(1, EMCC.fireDepartment?.bases!![2].vehicles.size)
        assertEquals(1, EMCC.policeDepartment?.bases?.size)
        assertEquals(1, EMCC.policeDepartment?.bases!![0].vehicles.size)
        assertEquals(1, EMCC.ambulanceDepartment?.bases?.size)
        assertEquals(1, EMCC.ambulanceDepartment?.bases!![0].vehicles.size)

        // check if emergencies and events got parsed correctly
        assertEquals(1, Simulation.emergencies.size)
        assertEquals(1, Simulation.events.size)
    }

    /*@Test
    fun testInitializeInvalidMap() {
        val res = Simulation.initialize(
            File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid9.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )

        assertFalse(res)
        assertTrue(false, Simulation.map.)
        assertTrue(Simulation.map.roadList.isEmpty())
        assertTrue(Simulation.map.vertexList.isEmpty())
    }*/
}
