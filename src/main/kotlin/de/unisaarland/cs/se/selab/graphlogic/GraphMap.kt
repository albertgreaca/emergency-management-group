package de.unisaarland.cs.se.selab.graphlogic

/**
 * Class representing that graph of map
 */
class GraphMap(
    val adjacencyList: MutableList<MutableMap<Vertex, Road>> = mutableListOf(
        mutableMapOf()
    ),
    val vertexList: MutableList<Vertex> = mutableListOf(),
    val roadList: MutableList<Road> = mutableListOf(),
    var name: String? = null
) {
    /**
     * Adding Vertex to lists
     */
    fun addVertex(vertex: Vertex) {
        vertexList.add(vertex)
        adjacencyList.add(mutableMapOf())
    }

    /**
     * Adding Vertex to lists
     */
    fun addRoad(road: Road, start: Int, end: Int): Boolean {
        val vs: Vertex? = getVertexFromId(start)
        val ve: Vertex? = getVertexFromId(end)
        if (vs == null || ve == null) {
            return false
        }
        roadList.add(road)
        if (road.secType == SecondaryRoadType.ONEWAYSTREET) {
            adjacencyList[vs.realid][ve] = road
        } else {
            adjacencyList[vs.realid][ve] = road
            adjacencyList[ve.realid][vs] = road
        }
        return true
    }

    /**
     * Restore OneWay to Normal
     */
    fun toNormal(road: Road, start: Int, end: Int) {
        adjacencyList[requireNotNull(getVertexFromId(end)?.realid)][requireNotNull(getVertexFromId(start))] = road
    }

    /**
     * Getting Vertex by Id
     */
    fun getVertexFromId(id: Int): Vertex? {
        return vertexList.firstOrNull { it.id == id }
    }

    /**
     * Getting Vertex by Id
     */
    fun getVertexFromRealId(realid: Int): Vertex? {
        return vertexList.firstOrNull { it.realid == realid }
    }

    /**
     * Getting Road of village and with roadname
     */
    fun getRoad(villageName: String, roadName: String): Road? {
        return roadList.firstOrNull { it.village == villageName && it.name == roadName }
    }

    /**
     * @return road by start and end vertex
     */
    fun getRoad(start: Vertex, end: Vertex): Road? {
        return adjacencyList[start.realid][end]
    }

    /**
     @return List of Roads that are of certain type
     */
    fun getListRoad(t: PrimaryRoadType): MutableList<Road> {
        val ans: MutableList<Road> = mutableListOf<Road>()
        for (r in roadList) {
            if (r.primType == t) {
                ans.add(r)
            }
        }
        return ans
    }

    /**
     * Removing Road
     */
    fun removeRoad(start: Int, end: Int, oneWay: Boolean) {
        if (oneWay) {
            adjacencyList[requireNotNull(getVertexFromId(end)?.realid)].remove(requireNotNull(getVertexFromId(start)))
        } else {
            roadList.remove(adjacencyList[requireNotNull(getVertexFromId(start)?.realid)][getVertexFromId(end)])
            adjacencyList[requireNotNull(getVertexFromId(start)?.realid)]?.remove(requireNotNull(getVertexFromId(end)))
            adjacencyList[requireNotNull(getVertexFromId(end)?.realid)]?.remove(requireNotNull(getVertexFromId(start)))
        }
    }
}
