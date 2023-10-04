package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class LessDogsThanK9Cars : SystemTest() {
    override val name = "LessDogsThanK9Cars"

    override val map = "testcases/lessK9DogsThanK9Cars/map.dot"
    override val assets = "testcases/lessK9DogsThanK9Cars/config2.json"
    override val scenario = "testcases/lessK9DogsThanK9Cars/config3.json"
    override val maxTicks = 42

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: example_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // 4 police cars are allocated and arrive in 1 tick
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 5")
        // 4 police cars arrived in the next tick
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Asset Arrival: 2 arrived at 3.")
        assertNextLine("Asset Arrival: 3 arrived at 3.")
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Emergency Failed: 0 failed.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 0 resolved emergencies.")
    }
}
