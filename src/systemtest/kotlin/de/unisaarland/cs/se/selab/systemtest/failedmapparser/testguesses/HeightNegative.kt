package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class HeightNegative : SystemTest() {
    override val name = "HeightNegative"

    override val map = "invalidMaps/failedmapparser/testnameguesses/heightnegative.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: heightnegative.dot invalid")
    }
}
