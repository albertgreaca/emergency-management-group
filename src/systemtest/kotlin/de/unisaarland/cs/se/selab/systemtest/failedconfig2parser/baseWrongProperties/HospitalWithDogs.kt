package de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.baseWrongProperties

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class HospitalWithDogs : SystemTest() {
    override val name = "HospitalWithDogs"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/BaseWrongProperties/HospitalWithDogs.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: HospitalWithDogs.json invalid")
    }
}
