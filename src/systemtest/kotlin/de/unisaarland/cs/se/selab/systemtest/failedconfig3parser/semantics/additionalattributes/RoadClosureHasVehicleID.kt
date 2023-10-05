package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.additionalattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RoadClosureHasVehicleID : SystemTest() {
    override val name = "RoadClosureHasVehicleID"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/Sematics/AdditionalAttributes/RoadClosureHasVehicleID.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: RoadClosureHasVehicleID.json invalid")
    }
}
