package de.unisaarland.cs.se.selab.systemtest.complicated

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class DoubleRealloc : SystemTest() {
    override val name = "DoubleRealloc"

    override val map = "mapFiles/doubleRealloc.dot"
    override val assets = "assetsJsons/AssetsDoubleRealloc.json"
    override val scenario = "scenarioJsons/EmergDoubleRealloc.json"
    override val maxTicks = 40

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: doubleRealloc.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsDoubleRealloc.json successfully parsed and validated")
        assertNextLine("Initialization Info: EmergDoubleRealloc.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Asset Allocation: 3 allocated to 0; 6 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 1 assigned to 2")
        assertNextLine("Asset Allocation: 4 allocated to 1; 6 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 6 ticks to arrive.")
        assertNextLine("Asset Reallocation: 3 reallocated to 1.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 2 assigned to 0")
        assertNextLine("Asset Allocation: 6 allocated to 2; 6 ticks to arrive.")
        assertNextLine("Asset Reallocation: 3 reallocated to 2.")
        assertNextLine("Asset Reallocation: 5 reallocated to 2.")
        assertNextLine("Asset Request: 1 sent to 1 for 2.")
        assertNextLine("Asset Allocation: 7 allocated to 2; 7 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 2; 7 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 2; 7 ticks to arrive.")
        assertNextLine("Asset Request: 2 sent to 3 for 2.")
        assertNextLine("Asset Allocation: 1 allocated to 2; 5 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 2; 5 ticks to arrive.")
        assertNextLine("Simulate Tick: 4")
        assertNextLine("Simulate Tick: 5")
    }
}
