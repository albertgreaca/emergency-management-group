package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class Tunnel4 : SystemTest() {
    override val name = "FailedParser1"

    override val map = "invalidMaps/failedmapparser/testguesses/tunnel4.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: tunnel4.dot invalid")
    }
}
