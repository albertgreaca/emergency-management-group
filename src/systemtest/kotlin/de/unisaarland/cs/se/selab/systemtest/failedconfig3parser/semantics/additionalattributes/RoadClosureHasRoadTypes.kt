package de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.additionalattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RoadClosureHasRoadTypes : SystemTest() {
    override val name = "RoadClosureHasRoadTypes"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "invalidConfig3/Sematics/AdditionalAttributes/RoadClosureHasRoadTypes.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: RoadClosureHasRoadTypes.json invalid")
    }
}
