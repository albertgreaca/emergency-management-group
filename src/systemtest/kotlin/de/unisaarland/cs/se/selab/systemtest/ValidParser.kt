package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ValidParser() : SystemTest() {
    override val name = "ValidParser"

    override val map = "mapFiles/big.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/forbig.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: big.dot successfully parsed and validated")
        assertNextLine("Initialization Info: example_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: forbig.json successfully parsed and validated")
    }
}