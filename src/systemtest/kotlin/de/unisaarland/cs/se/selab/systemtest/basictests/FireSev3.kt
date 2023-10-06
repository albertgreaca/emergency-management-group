package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FireSev3 : SystemTest() {
    override val name = "FireSev3"

    override val map = "mapFiles/FiveVertexMap.dot"
    override val assets = "assetsJsons/FireSev3Ass.json"
    override val scenario = "scenarioJsons/FireSev3Scen.json"
    override val maxTicks = 22

    override suspend fun run() {
        assertNextLine("Initialization Info: FiveVertexMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: FireSev3Ass.json successfully parsed and validated")
        assertNextLine("Initialization Info: FireSev3Scen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 4 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 10 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 11 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 12 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 13 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 14 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Request: 1 sent to 2 for 0.")
        assertNextLine("Asset Allocation: 0 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulate Tick: 2")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Asset Arrival: 2 arrived at 3.")
        assertNextLine("Simulate Tick: 3")
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        assertNextLine("Asset Arrival: 5 arrived at 3.")
        assertNextLine("Asset Arrival: 6 arrived at 3.")
        assertNextLine("Asset Arrival: 8 arrived at 3.")
        assertNextLine("Asset Arrival: 9 arrived at 3.")
        assertNextLine("Asset Arrival: 10 arrived at 3.")
        assertNextLine("Asset Arrival: 11 arrived at 3.")
        assertNextLine("Asset Arrival: 12 arrived at 3.")
        assertNextLine("Asset Arrival: 13 arrived at 3.")
        assertNextLine("Asset Arrival: 14 arrived at 3.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulate Tick: 4")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
