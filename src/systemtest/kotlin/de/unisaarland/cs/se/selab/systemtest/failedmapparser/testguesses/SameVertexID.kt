package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class SameVertexID : SystemTest() {
    override val name = "FailedParser1"

    override val map = "invalidMaps/failedmapparser/testguesses/samevertexid.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: samevertexid.dot invalid")
    }
}
