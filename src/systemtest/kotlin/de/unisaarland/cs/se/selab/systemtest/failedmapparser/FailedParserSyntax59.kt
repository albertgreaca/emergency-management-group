package de.unisaarland.cs.se.selab.systemtest.failedmapparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserSyntax59 : SystemTest() {
    override val name = "FailedParserSyntax59"

    override val map = "invalidMaps/SyntaxIssues/mapinvalid59.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid59.dot invalid")
    }
}
