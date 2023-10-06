package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class NothingArrivesNoRequest : SystemTest() {
    override val name = "NothingArrivesNoRequest"

    override val map = "testcases/nothingArrivesNoRequest/config2.json"
    override val assets = "EventIDConflict/assets.json"
    override val scenario = "EventIDConflict/scenario.json"
    override val maxTicks = 20

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: example_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")

        assertNextLine("Simulation Tick: 1")
        // traffic jam with factor 5 triggered, duration of 1
        assertNextLine("Event Triggered: 0 triggered.")

        assertNextLine("Simulation Tick: 2")
        // em0 assigned to hospital0
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // nothing allocated since nothing can arrive in time
        // no requests are sent since
        // traffic jam ended
        assertNextLine("Event Ended: 0 ended.")

        assertNextLine("Simulation Tick: 3")
        // amb0, amb1, and docCar2 allocated since they can now arrive in time
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 2 ticks to arrive.")

        // assets move and arrive in 1 tick
        assertNextLine("Simulation Tick: 4")

        // assets move and arrive
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 0 arrived at 1.")
        assertNextLine("Asset Arrival: 1 arrived at 1.")
        assertNextLine("Asset Arrival: 2 arrived at 1.")
        assertNextLine("Emergency Handling Start: 0 handling started.")

        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
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
