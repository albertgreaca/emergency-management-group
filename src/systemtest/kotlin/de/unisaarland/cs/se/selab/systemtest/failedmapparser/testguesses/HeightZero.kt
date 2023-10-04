package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class HeightZero : SystemTest() {
    override val name = "HeightZero"

    override val map = "invalidMaps/failedmapparser/testnameguesses/heightzero.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: heightzero.dot invalid")
    }
}
