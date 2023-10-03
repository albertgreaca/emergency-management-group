package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RushHour1Test : SystemTest() {
    override val name = "RushHour1Test"

    override val map = "mapFiles/RushHourMap.dot"
    override val assets = "assetsJsons/AssetsRushHour1.json"
    override val scenario = "scenarioJsons/ScenarioRushHour1.json"
    override val maxTicks = 20

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: RushHourMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsRushHour1.json successfully parsed and validated")
        assertNextLine("Initialization Info: ScenarioRushHour1.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 2 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 2 arrived at 1.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
    }
}
