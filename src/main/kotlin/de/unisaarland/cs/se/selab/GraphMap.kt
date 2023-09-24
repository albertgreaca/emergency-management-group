package de.unisaarland.cs.se.selab

class GraphMap (private var adjacencyList: MutableList<MutableMap<Vertex,Road>>, private var vertexList: MutableList<Vertex>,
                private var roadList: MutableList<Road>, private var name: String){
    fun getAdjacencyList(): MutableList<MutableMap<Vertex,Road>> {
        return adjacencyList
    }
    fun getVertexList(): MutableList<Vertex> {
        return vertexList
    }
    fun getRoadList(): MutableList<Road> {
        return roadList
    }
    fun getName(): String {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }
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
        return roadList.firstOrNull {it.getVillage() == villageName && it.getName() == roadName}
    }
}