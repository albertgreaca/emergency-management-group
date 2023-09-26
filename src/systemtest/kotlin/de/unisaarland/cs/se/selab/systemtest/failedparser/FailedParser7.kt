package de.unisaarland.cs.se.selab.systemtest.failedparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParser7 : SystemTest() {
    override val name = "FailedParser7"

    override val map = "invalidMaps/SemanticIssues/mapinvalid7.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid7.dot invalid")
    }
}