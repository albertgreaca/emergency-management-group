package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class OrderedSeverity : SystemTest() {
    override val name = "OrderedSeverity"

    override val map = "EmergenciesBySeverity/ebsmap.dot"
    override val assets = "EmergenciesBySeverity/ebsassets.json"
    override val scenario = "EmergenciesBySeverity/ebsscenario.json"
    override val maxTicks = 21

    override suspend fun run() {
        // everything is successfully parsed and validated
        assertNextLine("Initialization Info: ebsmap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: ebsassets.json successfully parsed and validated")
        assertNextLine("Initialization Info: ebsscenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Asset Allocation: 0 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Request: 1 sent to 2 for 1.")
        assertNextLine("Asset Allocation: 7 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 0 arrived at 5.")
        assertNextLine("Asset Arrival: 1 arrived at 5.")
        assertNextLine("Asset Arrival: 2 arrived at 5.")
        assertNextLine("Asset Arrival: 3 arrived at 5.")
        assertNextLine("Asset Arrival: 4 arrived at 5.")
        assertNextLine("Asset Arrival: 6 arrived at 5.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 7 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Asset Arrival: 0 arrived at 0.")
        assertNextLine("Asset Arrival: 1 arrived at 0.")
        assertNextLine("Asset Arrival: 2 arrived at 0.")
        assertNextLine("Asset Arrival: 3 arrived at 0.")
        assertNextLine("Asset Arrival: 4 arrived at 5.")
        assertNextLine("Asset Arrival: 6 arrived at 0.")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        // end of file
        assertEnd()
    }
}
