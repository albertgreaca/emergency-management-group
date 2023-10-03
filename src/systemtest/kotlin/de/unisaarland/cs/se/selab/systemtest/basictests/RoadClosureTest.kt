package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RoadClosureTest : SystemTest() {
    override val name = "RoadClosureTest"

    override val map = "mapFiles/RoadClosureMap.dot"
    override val assets = "assetsJsons/AssetsRoadClosure.json"
    override val scenario = "scenarioJsons/ScenarioRoadClosure.json"
    override val maxTicks = 20

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: RoadClosureMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsRoadClosure.json successfully parsed and validated")
        assertNextLine("Initialization Info: ScenarioRoadClosure.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 0 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 18 arrived at 4.")
        assertNextLine("Asset Arrival: 22 arrived at 4.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
    }
}
