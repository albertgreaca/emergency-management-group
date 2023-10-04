package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EventIdTest : SystemTest() {
    override val name = "EventIdTest"

    override val map = "mapFiles/ThreeVertexMap.dot"
    override val assets = "assetsJsons/event_id_bases.json"
    override val scenario = "scenarioJsons/event_id_scenario.json"
    override val maxTicks = 20

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: ThreeVertexMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: event_id_bases.json successfully parsed and validated")
        assertNextLine("Initialization Info: event_id_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 0 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Event Triggered: 1 triggered.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
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
