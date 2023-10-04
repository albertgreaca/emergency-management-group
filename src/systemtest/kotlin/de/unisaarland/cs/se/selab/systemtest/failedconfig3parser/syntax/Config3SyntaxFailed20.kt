package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.syntax

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class Config3SyntaxFailed20 : SystemTest() {
    override val name = "Config3SyntaxFailed20"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/Syntax/config3invalid20.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: config3invalid1.json invalid")
    }
}
