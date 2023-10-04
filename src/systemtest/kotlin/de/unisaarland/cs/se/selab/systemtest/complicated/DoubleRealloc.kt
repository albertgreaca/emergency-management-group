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
        assertNextLine("Asset Reallocation: 4 reallocated to 2.")
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
        assertNextLine("Asset Arrival: 3 arrived at 7.")
        assertNextLine("Asset Arrival: 5 arrived at 7.")
        assertNextLine("Simulate Tick: 6")
        assertNextLine("Asset Arrival: 4 arrived at 7.")
        assertNextLine("Asset Arrival: 6 arrived at 7.")
        assertNextLine("Simulate Tick: 7")
        assertNextLine("Asset Arrival: 1 arrived at 7.")
        assertNextLine("Asset Arrival: 2 arrived at 7.")
        assertNextLine("Asset Arrival: 7 arrived at 7.")
        assertNextLine("Asset Arrival: 8 arrived at 7.")
        assertNextLine("Asset Arrival: 9 arrived at 7.")
        assertNextLine("Emergency Handling Start: 2 handling started.")
        assertNextLine("Simulate Tick: 8")
        assertNextLine("Simulate Tick: 9")
        assertNextLine("Emergency Resolved: 2 resolved.")
        assertNextLine("Simulate Tick: 10")
        assertNextLine("Simulate Tick: 11")
        assertNextLine("Simulate Tick: 12")
        assertNextLine("Simulate Tick: 13")
        assertNextLine("Asset Arrival: 5 arrived at 7.")
        assertNextLine("Simulate Tick: 14")
        assertNextLine("Simulate Tick: 15")
        assertNextLine("Simulate Tick: 16")
        assertNextLine("Simulate Tick: 17")
        assertNextLine("Simulate Tick: 18")
        assertNextLine("Asset Arrival: 3 arrived at 5.")
        assertNextLine("Asset Arrival: 4 arrived at 5.")
        assertNextLine("Emergency Handling Start: 1 handling started.") // to finish sev 1 em
    }
}
