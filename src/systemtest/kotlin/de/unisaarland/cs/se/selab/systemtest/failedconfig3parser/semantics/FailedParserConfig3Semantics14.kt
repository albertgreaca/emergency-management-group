package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserConfig3Semantics14 : SystemTest() {
    override val name = "FailedParserConfig3Semantics14"

    override val map = "mapFiles/example_map.dot"
    override val assets = "src/systemtest/resources/assetsJsons/example_assets.json"
    override val scenario = "src/systemtest/resources/invalidConfig3/Sematics/config3invalid14.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: config3invalid14.json invalid")
    }
}
