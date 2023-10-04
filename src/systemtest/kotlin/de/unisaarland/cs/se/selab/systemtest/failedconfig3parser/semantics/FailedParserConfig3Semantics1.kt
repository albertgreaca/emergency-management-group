
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserConfig3Semantics1 : SystemTest() {
    override val name = "FailedParserConfig3Semantics1"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/Sematics/config3invalid1.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: config3invalid1.json invalid")
    }
}
