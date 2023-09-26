package de.unisaarland.cs.se.selab.systemtest.failedparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParser17 : SystemTest() {
    override val name = "FailedParser9"

    override val map = "invalidMaps/SemanticIssues/mapinvalid17.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid17.dot invalid")
    }
}