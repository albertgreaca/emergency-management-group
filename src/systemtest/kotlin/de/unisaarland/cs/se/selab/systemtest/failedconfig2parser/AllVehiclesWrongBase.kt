package de.unisaarland.cs.se.selab.systemtest.failedconfig2parser

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class AllVehiclesWrongBase : SystemTest() {
    override val name = "AllVehiclesWrongBase"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/Semantics/allVehicleWrongBase.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: allVehicleWrongBase.json invalid")
    }
}
