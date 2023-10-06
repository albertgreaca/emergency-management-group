
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RoadClosureNoVertex : SystemTest() {
    override val name = "RoadClosureNoVertex"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/mutants/RoadClosureNoVertex.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: RoadClosureNoVertex.json invalid")
    }
}
