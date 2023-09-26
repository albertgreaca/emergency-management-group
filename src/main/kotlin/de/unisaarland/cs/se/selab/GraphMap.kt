package de.unisaarland.cs.se.selab

class GraphMap (var adjacencyList: MutableList<MutableMap<Vertex,Road>> = mutableListOf(mutableMapOf()), var vertexList: MutableList<Vertex> = mutableListOf(),
                var roadList: MutableList<Road> = mutableListOf(), var name: String? = null){
    fun addVertex(vertex: Vertex) {
        vertexList.add(vertex)
        adjacencyList.add(mutableMapOf())
    }
    fun addRoad(road: Road, start: Int, end: Int): Boolean {
        val vs: Vertex? = getVertex(start)
        val ve: Vertex? = getVertex(end)
        if (vs == null || ve == null)
            return false
        roadList.add(road)
        adjacencyList[vs.getId()][ve] = road
        adjacencyList[ve.getId()][vs] = road
        return true
    }
    fun getVertex(id: Int): Vertex? {
        return vertexList.firstOrNull {it.getId() == id}
    }
    fun getRoad(villageName: String, roadName: String): Road? {
        return roadList.firstOrNull {it.village == villageName && it.name == roadName}
    }
    fun getRoad(start: Vertex, end: Vertex): Road? {
        return adjacencyList[start.id].get(end)
    }
    fun getListRoad(t: PrimaryRoadType): MutableList<Road> {
        var ans: MutableList<Road> = mutableListOf<Road>()
        for(r in roadList)
            if (r.primType == t)
                ans.add(r)
        return ans
    }
}