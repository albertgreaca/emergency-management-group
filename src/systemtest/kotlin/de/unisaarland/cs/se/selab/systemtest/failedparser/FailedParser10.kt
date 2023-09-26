package de.unisaarland.cs.se.selab.systemtest.failedparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParser10 : SystemTest() {
    override val name = "FailedParser10"

    override val map = "invalidMaps/SemanticIssues/mapinvalid10.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid10.dot invalid")
    }
}
