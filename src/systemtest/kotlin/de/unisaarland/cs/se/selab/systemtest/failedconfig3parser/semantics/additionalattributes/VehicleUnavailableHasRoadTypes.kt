package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.additionalattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class VehicleUnavailableHasRoadTypes : SystemTest() {
    override val name = "VehicleUnavailableHasRoadTypes"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/Sematics/AdditionalAttributes/VehicleUnavailableHasRoadTypes.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: VehicleUnavailableHasRoadTypes.json invalid")
    }
}
