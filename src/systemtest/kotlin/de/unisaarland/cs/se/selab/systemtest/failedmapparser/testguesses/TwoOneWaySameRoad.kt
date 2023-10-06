package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class TwoOneWaySameRoad : SystemTest() {
    override val name = "TwoOneWayOneRoad"

    override val map = "invalidMaps/testnameguesses/2OneWaySameRoad.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: 2OneWaySameRoad.dot invalid")
    }
}
