package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class OneWay : SystemTest() {
    override val name = "OneWay"

    override val map = "mapFiles/OneWayStreetMap.dot"
    override val assets = "assetsJsons/oneWayAssets.json"
    override val scenario = "scenarioJsons/oneWayScen.json"
    override val maxTicks = 22

    override suspend fun run() {
        assertNextLine("Initialization Info: OneWayStreetMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: oneWayAssets.json successfully parsed and validated")
        assertNextLine("Initialization Info: oneWayScen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 1 allocated to 0; 3 ticks to arrive")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Emergency Resolved: 0 resolved")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
