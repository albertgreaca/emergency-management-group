package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class OneWayConstructionSiteTest : SystemTest() {
    override val name = "OneWayConstructionSiteTest"

    override val map = "mapFiles/MapOneWayConstructionSite.dot"
    override val assets = "assetsJsons/AssetsOneWayConstructionSite.json"
    override val scenario = "scenarioJsons/ScenarioOneWayConstructionSite.json"
    override val maxTicks = 20
    override suspend fun run() {
        assertNextLine("Initialization Info: MapOneWayConstructionSite.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsOneWayConstructionSite.json successfully parsed and validated")
        assertNextLine("Initialization Info: ScenarioOneWayConstructionSite.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 2 allocated to 0; 8 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Asset Arrival: 2 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Simulation Tick: 13")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
    }
}
