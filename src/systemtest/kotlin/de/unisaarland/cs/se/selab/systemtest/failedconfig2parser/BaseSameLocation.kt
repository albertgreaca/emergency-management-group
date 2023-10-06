package de.unisaarland.cs.se.selab.systemtest.failedconfig2parser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class BaseSameLocation : SystemTest() {
    override val name = "BaseSameLocation"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/BaseSameLocation.json"
    override val scenario = "invalidConfig3/BaseSameLocation.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: BaseSameLocation.json invalid")
    }
}
