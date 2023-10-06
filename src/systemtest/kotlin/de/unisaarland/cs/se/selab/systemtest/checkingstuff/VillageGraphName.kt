package de.unisaarland.cs.se.selab.systemtest.checkingstuff

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class VillageGraphName : SystemTest() {
    override val name = "Village = Graphname"
    override val map = "map_invalid.dot"
    override val assets = "assetsJsons/dijTieAss.json"
    override val scenario = "scenarioJsons/dijTieNodesScen.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: map_invalid.dot invalid")
        assertEnd()
    }
}
