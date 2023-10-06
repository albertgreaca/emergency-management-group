package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class MultipleReroutings : SystemTest() {
    override val name = "MultipleReroutings"

    override val map = "testcases/multipleReroutings/map.dot"
    override val assets = "testcases/multipleReroutings/config2.json"
    override val scenario = "testcases/multipleReroutings/config2.json"
    override val maxTicks = 50
    val assetsRerouted3 = "Assets Rerouted: 3"

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: config2.json successfully parsed and validated")
        assertNextLine("Initialization Info: config3.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        // em0 is assigned to hospital (id 0)
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // amb0, amb1, and docCar2 are allocated to em0
        assertNextLine("Asset Allocation: 0 allocated to 0; 5 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 5 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 5 ticks to arrive.")
        // first traffic jam event is triggered
        assertNextLine("Event Triggered: 0 triggered.")
        // amb0, amb1, and docCar are rerouted, need 6 ticks now
        assertNextLine(assetsRerouted3)

        // assets move and need 5 ticks to arrive
        assertNextLine("Simulation Tick: 2")

        // assets move and need 4 ticks to arrive
        assertNextLine("Simulation Tick: 3")

        // assets move and need 3 ticks to arrive
        assertNextLine("Simulation Tick: 4")
        // first and second construction site event are triggered
        assertNextLine("Event Triggered: 1 triggered.")
        assertNextLine("Event Triggered: 2 triggered.")
        // amb0, amb1, and docCar are rerouted, need 6 ticks now
        assertNextLine(assetsRerouted3)

        // assets move and need 5 ticks to arrive
        assertNextLine("Simulation Tick: 5")
        // first traffic jam event ends
        assertNextLine("Event Ended: 0 ended.")

        // assets move and need 4 ticks ot arrive
        assertNextLine("Simulation Tick: 6")

        // assets move and need 3 ticks to arrive
        assertNextLine("Simulation Tick: 7")
        // first and second construction site event end
        assertNextLine("Event Ended: 1 ended.")
        assertNextLine("Event Ended: 2 ended.")

        // assets move and need 2 ticks to arrive
        assertNextLine("Simulation Tick: 8")
        // first rush hour event is triggered, affects main streets
        assertNextLine("Event Triggered: 3 triggered.")
        // amb0, amb1, and docCar are rerouted, need 3 ticks now
        assertNextLine(assetsRerouted3)

        // assets move and need 2 ticks to arrive
        assertNextLine("Simulation Tick: 9")

        // assets move and need 1 ticks to arrive
        assertNextLine("Simulation Tick: 10")

        // assets move and need 0 ticks to arrive
        assertNextLine("Simulation Tick: 11")
        // amb0, amb1, and docCar2 arrive at em0
        assertNextLine("Asset Arrival: 0 arrived at 6.")
        assertNextLine("Asset Arrival: 1 arrived at 6.")
        assertNextLine("Asset Arrival: 2 arrived at 6.")

        assertNextLine("Simulation Tick: 12")
        // rush hour event ends
        assertNextLine("Event Ended: 3 ended.")

        assertNextLine("Simulation Tick: 13")
        // after one tick, em0 is resolved
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 9 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
