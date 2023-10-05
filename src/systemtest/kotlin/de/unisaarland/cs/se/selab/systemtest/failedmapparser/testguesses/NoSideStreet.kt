package de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class NoSideStreet : SystemTest() {
    override val name = "NoSideStreet"

    override val map = "invalidMaps/testnameguesses/noSideStreet.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: noSideStreet.dot invalid")
    }
}
