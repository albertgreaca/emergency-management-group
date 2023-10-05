package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class CountyVillageName : SystemTest() {
    override val name = "CountyVillageName"

    override val map = "invalidMaps/testnameguesses/countyVillageName.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: countyVillageName.dot invalid")
    }
}
