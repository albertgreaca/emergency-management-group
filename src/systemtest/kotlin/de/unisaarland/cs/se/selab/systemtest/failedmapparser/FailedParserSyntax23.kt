package de.unisaarland.cs.se.selab.systemtest.failedmapparser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParserSyntax23 : SystemTest() {
    override val name = "FailedParserSyntax23"

    override val map = "invalidMaps/SyntaxIssues/mapinvalid23.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: mapinvalid23.dot invalid")
    }
}
