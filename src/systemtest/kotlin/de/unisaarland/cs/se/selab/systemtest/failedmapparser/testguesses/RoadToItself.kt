package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RoadToItself : SystemTest() {
    override val name = "RoadToItself"

    override val map = "invalidMaps/testnameguesses/roadToItself.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: roadToItself.dot invalid")
    }
}
