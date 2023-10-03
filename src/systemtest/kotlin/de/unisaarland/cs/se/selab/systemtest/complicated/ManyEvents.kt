package de.unisaarland.cs.se.selab.systemtest.complicated

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ManyEvents : SystemTest() {
    override val name = "Many Events..."

    override val map = "mapFiles/manyevents_map.dot"
    override val assets = "assetsJsons/manyevents_assets.json"
    override val scenario = "scenarioJsons/manyevents_scenario.json"
    override val maxTicks = 40

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: manyevents_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: manyevents_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: manyevents_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 1 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 0 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 0 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Simulation Tick: 13")
        assertNextLine("Simulation Tick: 14")
        assertNextLine("Asset Allocation: 0 allocated to 1; 5 ticks to arrive.")
        assertNextLine("Simulation Tick: 15")
        assertNextLine("Simulation Tick: 16")
        assertNextLine("Simulation Tick: 17")
        assertNextLine("Simulation Tick: 18")
        assertNextLine("Simulation Tick: 19")
        assertNextLine("Asset Arrival: 0 arrived at 4.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 20")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation Tick: 21")
        assertNextLine("Simulation Tick: 22")
        assertNextLine("Simulation Tick: 23")
        assertNextLine("Event Ended: 1 ended.")
        assertNextLine("Simulation Tick: 24")
        assertNextLine("Emergency Assignment: 2 assigned to 1")
        assertNextLine("Simulation Tick: 25")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Simulation Tick: 26")
        assertNextLine("Simulation Tick: 27")
        assertNextLine("Simulation Tick: 28")
        assertNextLine("Asset Allocation: 0 allocated to 2; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 29")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Simulation Tick: 30")
        assertNextLine("Asset Arrival: 0 arrived at 2.")
        assertNextLine("Emergency Handling Start: 2 handling started.")
        assertNextLine("Simulation Tick: 31")
        assertNextLine("Emergency Resolved: 2 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 3 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 3 resolved emergencies.")
    }
}
