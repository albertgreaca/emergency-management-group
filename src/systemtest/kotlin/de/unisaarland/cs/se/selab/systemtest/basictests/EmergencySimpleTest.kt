package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EmergencySimpleTest : SystemTest() {
    override val name = "EmergencySimpleTest"

    override val map = "mapFiles/MapEmergencySimple.dot"
    override val assets = "assetsJsons/AssetsEmergencySimple.json"
    override val scenario = "scenarioJsons/ScenarioEmergencySimple.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: MapEmergencySimple.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsEmergencySimple.json successfully parsed and validated")
        assertNextLine("Initialization Info: ScenarioEmergencySimple.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 18 allocated to 0; 0 ticks to arrive.")
        assertNextLine("Asset Allocation: 22 allocated to 0; 0 ticks to arrive.")
        assertNextLine("Asset Arrival: 18 arrived at 1.")
        assertNextLine("Asset Arrival: 22 arrived at 1.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        assertNextLine("Asset Allocation: 19 allocated to 1; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Emergency Assignment: 2 assigned to 1")
        assertNextLine("Asset Allocation: 0 allocated to 2; 0 ticks to arrive.")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Emergency Handling Start: 2 handling started.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Emergency Assignment: 3 assigned to 2")
        assertNextLine("Asset Allocation: 42 allocated to 3; 2 ticks to arrive.")
        assertNextLine("Asset Arrival: 19 arrived at 4.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 2 resolved.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Asset Arrival: 42 arrived at 4.")
        assertNextLine("Emergency Handling Start: 3 handling started.")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Emergency Resolved: 3 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 4 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 4 resolved emergencies.")
    }
}
