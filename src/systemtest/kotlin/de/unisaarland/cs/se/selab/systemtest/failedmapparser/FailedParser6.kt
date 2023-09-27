package de.unisaarland.cs.se.selab.systemtest.failedmapparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParser6 : SystemTest() {
    override val name = "FailedParser6"

    override val map = "invalidMaps/SemanticIssues/mapinvalid6.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid6.dot invalid")
    }
}
