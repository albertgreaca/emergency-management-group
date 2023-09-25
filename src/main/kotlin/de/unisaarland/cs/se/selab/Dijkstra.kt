package de.unisaarland.cs.se.selab

import Base
import EmergencyType
import Hospital
import java.util.PriorityQueue
import PoliceStation

object Dijkstra {
    private var gm2: GraphMap? = null

    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.getVertexList().size
        var ans: IntArray = IntArray(n)
        var i: Int
        for (i in 0..n - 1) {
            ans[i] = Int.MAX_VALUE
        }
        ans[startingNode] = 0
        ans[startingNode2] = 0
        var compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        var pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, ans[i]))
        }
        while (!pq.isEmpty()) {
            var cur: Pair<Int, Int> = pq.remove()
            if (ans[cur.first] != cur.second) {
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
                if (et == EmergencyType.Medical && b is Hospital)
                    return b
            }
            val nex: Map<Vertex, Road> = gm.getAdjacencyList()[cur.first]
            for ((node, edge) in nex) {
                if (ans[node.getId()] > ans[cur.first] + edge.getActualWeight()) {
                    ans[node.getId()] = ans[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.getId(), ans[node.getId()]))
                }
            }
        }
        return null
    }
}
