
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
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

    @Test
    fun testInitializeInvalidMapSemantically() {
        val res = Simulation.initialize(
            File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid9.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        assertFalse(res)
    }

    @Test
    fun testInitializeInvalidMapSyntactically() {
        val res = Simulation.initialize(
            File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid61.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        assertFalse(res)
    }

    @Test
    fun testInitializeInvalidConfig2Semantically() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/invalidConfig2/Semantics/config2invalid5.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        assertFalse(res)
    }

    @Test
    fun testInitializeInvalidConfig2Syntactically() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/invalidConfig2/Syntax/config2invalid21.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        assertFalse(res)
    }

    @Test
    fun testInitializeInvalidConfig3Semantically() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/invalidConfig3/Sematics/config3invalid7.json")
        )
        assertFalse(res)
    }

    @Test
    fun testInitializeInvalidConfig3Syntactically() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/invalidConfig3/Syntax/config3invalid10.json")
        )
        assertFalse(res)
    }

    @Test
    fun testsimulateSimulationMaxTicksNull() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )

        assertTrue(res)

        Simulation.simulateSimulation()

        val resolvedEms = Simulation.statistics.resolvedEmergencies
        val receivedEms = Simulation.statistics.receivedEmergencies
        val reroutedAssets = Simulation.statistics.reroutedAssets
        val failedEms = Simulation.statistics.failedEmergencies
        val ongoingEms = Simulation.statistics.ongoingEmergencies

        assertEquals(0, resolvedEms)
        assertEquals(1, receivedEms)
        assertEquals(0, reroutedAssets)
        assertEquals(1, failedEms)
        assertEquals(0, ongoingEms)
    }

    @Test
    fun testsimulateSimulationBasic() {
        val res = Simulation.initialize(
            File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"),
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )

        assertTrue(res)

        Simulation.maximumTicks = 10
        Simulation.simulateSimulation()

        val resolvedEms = Simulation.statistics.resolvedEmergencies
        val receivedEms = Simulation.statistics.receivedEmergencies
        val reroutedAssets = Simulation.statistics.reroutedAssets
        val failedEms = Simulation.statistics.failedEmergencies
        val ongoingEms = Simulation.statistics.ongoingEmergencies

        assertEquals(0, resolvedEms)
        assertEquals(1, receivedEms)
        assertEquals(0, reroutedAssets)
        assertEquals(1, failedEms)
        assertEquals(0, ongoingEms)
    }
}
