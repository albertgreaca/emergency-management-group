package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ArrivalTickFailWithRequest : SystemTest() {
    override val name = "ArrivalTickFailWithRequest"

    override val map = "ArrivalTickFailWithRequest/Map.dot"
    override val assets = "ArrivalTickFailWithRequest/assets.json"
    override val scenario = "ArrivalTickFailWithRequest/scenerio.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: Map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenerio.json successfully parsed and validated")

        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 3")
        assertNextLine("Asset Request: 1 sent to 0 for 0.")
        assertNextLine("Request Failed: 0 failed.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Request: 2 sent to 0 for 0.")
        assertNextLine("Request Failed: 0 failed.")
        assertNextLine("Emergency Failed: 0 failed.")

        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 0 resolved emergencies.")
    }
}
