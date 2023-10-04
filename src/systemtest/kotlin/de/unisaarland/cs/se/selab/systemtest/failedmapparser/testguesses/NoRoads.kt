package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class NoRoads : SystemTest() {
    override val name = "NoRoads"

    override val map = "invalidMaps/failedmapparser/testnameguesses/noroadsmap.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: noroadsmap.dot invalid")
    }
}
