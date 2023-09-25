package de.unisaarland.cs.se.selab

import java.util.PriorityQueue

object Dijkstra {
    private var gm2: GraphMap? = null

    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.getVertexList().size
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
            if (v.getBase() != null) {
                var b: Base = v.getBase()!!
                if (et == EmergencyType.FIRE && b !is PoliceStation && b !is Hospital)
                    return b
                if (et == EmergencyType.ACCIDENT && b !is PoliceStation && b !is Hospital)
                    return b
                if (et == EmergencyType.CRIME && b is PoliceStation)
                    return b
                if (et == EmergencyType.MEDICAL && b is Hospital)
                    return b
            }
            val nex: Map<Vertex, Road> = gm.getAdjacencyList()[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.getId()] > dist[cur.first] + edge.getActualWeight()) {
                    dist[node.getId()] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.getId(), dist[node.getId()]))
                }
            }
        }
        return null
    }

    fun dijkstraRequest(startingNode: Int): List<Base> {
        var gm: GraphMap = gm2!!
        var n: Int = gm.getVertexList().size
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
            if (v.getBase() != null) {
                var b: Base = v.getBase()!!
                ans.add(b)
            }
            val nex: Map<Vertex, Road> = gm.getAdjacencyList()[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.getId()] > dist[cur.first] + edge.getActualWeight()) {
                    dist[node.getId()] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.getId(), dist[node.getId()]))
                }
            }
        }
        return ans
    }
}
