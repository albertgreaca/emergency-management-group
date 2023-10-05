package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class HeightChangeTest : SystemTest() {
    override val name = "HeightChange"

    override val map = "HeightChange/map.dot"
    override val assets = "HeightChange/assets.json"
    override val scenario = "HeightChange/scenario.json"
    override val maxTicks = 100

    override suspend fun run() {
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 1 allocated to 0; 10 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 10 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Assets Rerouted: 1")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 2 arrived at 2.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Asset Arrival: 1 arrived at 4.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 1 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
    }
}
