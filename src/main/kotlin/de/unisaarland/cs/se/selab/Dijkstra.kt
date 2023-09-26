package de.unisaarland.cs.se.selab

import java.util.PriorityQueue

object Dijkstra {
    public var gm2: GraphMap? = null

    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: IntArray = IntArray(n)
        var i: Int
        for (i in 0..n - 1) {
            dist[i] = Int.MAX_VALUE
        }
        dist[startingNode] = 0
        dist[startingNode2] = 0
        var compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        var pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i]))
        }
        while (!pq.isEmpty()) {
            var cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first] != cur.second) {
                continue
            }
            var v: Vertex = gm.getVertex(cur.first) ?: continue
            if (v.base != null) {
                var b: Base = v.base!!
                if (et == EmergencyType.FIRE && b !is PoliceStation && b !is Hospital)
                    return b
                if (et == EmergencyType.ACCIDENT && b !is PoliceStation && b !is Hospital)
                    return b
                if (et == EmergencyType.CRIME && b is PoliceStation)
                    return b
                if (et == EmergencyType.MEDICAL && b is Hospital)
                    return b
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.getId()] > dist[cur.first] + edge.weight) {
                    dist[node.getId()] = dist[cur.first] + edge.weight
                    pq.add(Pair(node.getId(), dist[node.getId()]))
                }
            }
        }
        return null
    }

    fun dijkstraRequest(startingNode: Int): MutableList<Base> {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: IntArray = IntArray(n)
        var i: Int
        for (i in 0..n - 1) {
            dist[i] = Int.MAX_VALUE
        }
        dist[startingNode] = 0
        var compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        var pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i]))
        }
        var ans: MutableList<Base> = mutableListOf()
        while (!pq.isEmpty()) {
            var cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first] != cur.second) {
                continue
            }
            var v: Vertex = gm.getVertex(cur.first) ?: continue
            if (v.base != null) {
                var b: Base = v.base!!
                ans.add(b)
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.getId()] > dist[cur.first] + edge.getActualWeight()) {
                    dist[node.getId()] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.getId(), dist[node.getId()]))
                }
            }
        }
        return ans
    }

    fun dijkstraHeight(startingNode: Int, endRoad: Road, height: Int): Position? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: Array<Position> = Array<Position>(n) {index -> Position(mutableListOf<Road>(),mutableListOf<Vertex>(),0,null, 0,0, false)}
        var i: Int
        for (i in 0..n - 1) {
            dist[i].distance = Int.MAX_VALUE
        }
        dist[startingNode].distance = 0
        var compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        var pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        var ans: MutableList<Base> = mutableListOf()
        while (!pq.isEmpty()) {
            var cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            var v: Vertex = gm.getVertex(cur.first) ?: continue
            if (endRoad.start == v || endRoad.end == v) {
                return dist[cur.first]
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex)
                if (edge.height <= height) {
                    var newp: Position
                    if (dist[node.id].distance > newp.distance || (dist[node.id].distance == newp.distance && newp.smaller(dist[node.id]))) {
                        dist[node.id] = newp
                        pq.add(Pair(node.id, newp.distance))
                    }
                }
        }
        return null
    }
}
