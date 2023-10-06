package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.sthmissing

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EmNoRoadsTest : SystemTest() {
    override val name = "EmNoRoads"

    override val map = "invalidConfig3/mutants/EmNoRoad.dot"
    override val assets = "invalidConfig3/mutants/EmNoRoadAsset.json"
    override val scenario = "invalidConfig3/mutants/EmNoRoadScenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: EmNoRoad.dot successfully parsed and validated")
        assertNextLine("Initialization Info: EmNoRoadAsset.json successfully parsed and validated")
        assertNextLine("Initialization Info: EmNoRoadScenario.json invalid")
    }
}
