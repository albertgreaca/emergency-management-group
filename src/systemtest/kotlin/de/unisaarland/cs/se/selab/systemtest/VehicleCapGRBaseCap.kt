package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class VehicleCapGRBaseCap : SystemTest() {
    override val name = "Vehicle Capacity > Base Staff"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/vehiclecapgrbasecap.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: vehiclecapgrbasecap.json invalid")
    }
}
