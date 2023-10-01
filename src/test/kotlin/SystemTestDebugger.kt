import de.unisaarland.cs.se.selab.mainlogic.Simulation
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class SystemTestDebugger {

    @Test
    fun reallocation() {
        Simulation.maximumTicks = 50
        var res = Simulation.initialize(
            File("src/test/resources/testReallocation/map_reallocate.dot"),
            File("src/test/resources/testReallocation/assets_reallocate.json"),
            File("src/test/resources/testReallocation/scenario_reallocate.json")
        )
        assertTrue(res)
        Simulation.simulateSimulation()
    }
}
