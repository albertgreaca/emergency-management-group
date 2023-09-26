package de.unisaarland.cs.se.selab.systemtest.failedmapparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserSyntax30 : SystemTest() {
    override val name = "FailedParserSyntax30"

    override val map = "invalidMaps/SyntaxIssues/mapinvalid30.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid30.dot invalid")
    }
}
