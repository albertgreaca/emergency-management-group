package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class DijTieSimple : SystemTest() {
    override val name = "DijTieSimple"

    override val map = "mapFiles/dijkstraTie.dot"
    override val assets = "assetsJsons/dijTieAss.json"
    override val scenario = "scenarioJsons/dijTieScen.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: dijkstraTie.dot successfully parsed and validated")
        assertNextLine("Initialization Info: dijTieAss.json successfully parsed and validated")
        assertNextLine("Initialization Info: dijTieScen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 1 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Event Triggered: 1 triggered.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 1 arrived at 4.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 4")
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
