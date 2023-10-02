package de.unisaarland.cs.se.selab.systemtest.failedconfig2parser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FailedParser23 : SystemTest() {
    override val name = "FailedParser23"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/Semantics/config2invalid23.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info:config2invalid1.json invalid")

    }
}
