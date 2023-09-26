package de.unisaarland.cs.se.selab.systemtest.failedparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserSyntax2 : SystemTest() {
    override val name = "FailedParserSyntax2"

    override val map = "invalidMaps/SyntaxIssues/mapinvalid2.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid2.dot invalid")
    }
}
