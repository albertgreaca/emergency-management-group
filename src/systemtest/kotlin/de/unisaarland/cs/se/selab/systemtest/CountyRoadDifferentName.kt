package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class CountyRoadDifferentName : SystemTest() {
    override val name = "County Road has a different name"

    override val map = "invalidMaps/countyroadvilnotgraphname.dot"
    override val assets = "config2Invalid/vehiclecapgrbasecap.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: countyroadvilnotgraphname.dot invalid")
    }
}
