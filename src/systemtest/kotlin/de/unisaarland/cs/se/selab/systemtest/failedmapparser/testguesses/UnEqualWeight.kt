package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class UnEqualWeight : SystemTest() {
    override val name = "UnEqualWeight"

    override val map = "invalidMaps/testnameguesses/unequalweight.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: unequalweight.dot invalid")
    }
}
