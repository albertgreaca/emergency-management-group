package de.unisaarland.cs.se.selab

import java.util.PriorityQueue

/**
 * Class representing Dijkstra
 */
object Dijkstra {
    var gm2: GraphMap? = null

    private fun determineBaseEmergency(et: EmergencyType, b: Base): Base? {
        if (et == EmergencyType.FIRE && b !is PoliceStation && b !is Hospital) {
            return b
        }
        if (et == EmergencyType.ACCIDENT && b !is PoliceStation && b !is Hospital) {
            return b
        }
        if (et == EmergencyType.CRIME && b is PoliceStation) {
            return b
        }
        if (et == EmergencyType.MEDICAL && b is Hospital) {
            return b
        }
        return null
    }

    private fun updateNeighborsEmergency(cur: Pair<Int, Int>, dist: IntArray, pq: PriorityQueue<Pair<Int, Int>>) {
        val nex: Map<Vertex, Road> = requireNotNull(gm2).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (dist[node.id] > dist[cur.first] + edge.weight) {
                dist[node.id] = dist[cur.first] + edge.weight
                pq.add(Pair(node.id, dist[node.id]))
            }
        }
    }

    /**
     * Doing dijkstra for emergencies
     */
    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        val gm: GraphMap = requireNotNull(gm2)
        val n: Int = gm.vertexList.size
        val dist = IntArray(n)
        for (i in 0..n - 1) {
            dist[i] = Int.MAX_VALUE
        }
        dist[startingNode] = 0
        dist[startingNode2] = 0
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i]))
        }
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first] != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(gm.getVertex(cur.first))
            if (v.base != null) {
                val b: Base = requireNotNull(v.base)
                if (determineBaseEmergency(et, b) != null) {
                    return determineBaseEmergency(et, b)
                }
            }
            updateNeighborsEmergency(cur, dist, pq)
        }
        return null
    }

    /**
     * Doing dijkstra for requesting
     */
    fun dijkstraRequest(startingNode: Int): MutableList<Base> {
        val gm: GraphMap = requireNotNull(gm2)
        val n: Int = gm.vertexList.size
        val dist = IntArray(n)
        for (i in 0..n - 1) {
            dist[i] = Int.MAX_VALUE
        }
        dist[startingNode] = 0
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i]))
        }
        val ans: MutableList<Base> = mutableListOf<Base>()
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first] != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(gm.getVertex(cur.first))
            if (v.base != null) {
                val b: Base = requireNotNull(v.base)
                ans.add(b)
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.id] > dist[cur.first] + edge.getActualWeight()) {
                    dist[node.id] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.id, dist[node.id]))
                }
            }
        }
        return ans
    }

    private fun determinePathHeight(cur: Pair<Int, Int>, dist: Array<Position>): Position {
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            dist[cur.first].roadList.add(
                requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1]))
            )
        }
        dist[cur.first].distance = cur.second
        dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
        dist[cur.first].distanceFromStart = 0
        dist[cur.first].distanceFromEnd = dist[cur.first].roadList[0].getActualWeight()
        return dist[cur.first]
    }

    private fun updateNeighborsHeight(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        pq: PriorityQueue<Pair<Int, Int>>,
        height: Int,
        v: Vertex
    ) {
        val nex: Map<Vertex, Road> = requireNotNull(gm2).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (edge.height <= height) {
                val newp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                newp.distance = dist[cur.first].distance + edge.getActualWeight()
                newp.vertexList = dist[cur.first].vertexList.toMutableList()
                newp.vertexList.add(v)
                if (dist[node.id].distance > newp.distance ||
                    (dist[node.id].distance == newp.distance && newp.smaller(dist[node.id]))
                ) {
                    dist[node.id] = newp
                    pq.add(Pair(node.id, newp.distance))
                }
            }
        }
    }

    /**
     * Doing Dijkstra respecting the height of vehicles and roads
     */
    fun dijkstraHeight(startingNode: Int, endRoad: Road, height: Int): Position? {
        val gm: GraphMap = requireNotNull(gm2)
        val n: Int = gm.vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
        }
        for (i in 0..n - 1) {
            dist[i].distance = Int.MAX_VALUE
        }
        dist[startingNode].distance = 0
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(gm.getVertex(cur.first))
            if (endRoad.start == v || endRoad.end == v) {
                return determinePathHeight(cur, dist)
            }
            updateNeighborsHeight(cur, dist, pq, height, v)
        }
        return null
    }

    private fun determinePathReroute(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        startRoad: Road,
        distStart: Int,
        distEnd: Int
    ): Position {
        if (dist[cur.first].vertexList[0] == startRoad.start) {
            if (distStart != 0) {
                dist[cur.first].roadList.add(startRoad)
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = startRoad.start
                dist[cur.first].distanceFromStart = distEnd
                dist[cur.first].distanceFromEnd = distStart
            } else {
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                dist[cur.first].distanceFromStart = 0
                dist[cur.first].distanceFromEnd =
                    requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])).getActualWeight()
            }
        } else {
            if (distEnd != 0) {
                dist[cur.first].roadList.add(startRoad)
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = startRoad.end
                dist[cur.first].distanceFromStart = distStart
                dist[cur.first].distanceFromEnd = distEnd
            } else {
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                dist[cur.first].distanceFromStart = 0
                dist[cur.first].distanceFromEnd =
                    requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])).getActualWeight()
            }
        }
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            dist[cur.first].roadList.add(
                requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1]))
            )
        }
        return dist[cur.first]
    }

    private fun updateNeighborsReroute(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        pq: PriorityQueue<Pair<Int, Int>>,
        height: Int,
        v: Vertex
    ) {
        val nex: Map<Vertex, Road> = requireNotNull(gm2).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (edge.height <= height) {
                val newp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                newp.distance = dist[cur.first].distance + edge.getActualWeight()
                newp.vertexList = dist[cur.first].vertexList.toMutableList()
                newp.vertexList.add(v)
                if (dist[node.id].distance > newp.distance ||
                    (dist[node.id].distance == newp.distance && newp.smaller(dist[node.id]))
                ) {
                    dist[node.id] = newp
                    pq.add(Pair(node.id, newp.distance))
                }
            }
        }
    }

    /**
     * Doing Dijkstra for Reallocation
     */
    fun dijkstraReroute(
        startRoad: Road,
        distStart: Int,
        distEnd: Int,
        dir: Vertex,
        endRoad: Road,
        height: Int
    ): Position? {
        val gm: GraphMap = requireNotNull(gm2)
        val n: Int = gm.vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(
                mutableListOf<Road>(),
                mutableListOf<Vertex>(),
                0,
                0,
                null,
                0,
                0
            )
        }
        for (i in 0..n - 1) {
            dist[i].distance = Int.MAX_VALUE
        }
        if (startRoad.start != dir) {
            dist[startRoad.start.id].distance = distStart
            dist[startRoad.end.id].distance = distEnd
        } else {
            dist[startRoad.start.id].distance = distEnd
            dist[startRoad.end.id].distance = distStart
        }
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(gm.getVertex(cur.first))
            if (endRoad.start == v || endRoad.end == v) {
                return determinePathReroute(cur, dist, startRoad, distStart, distEnd)
            }
            updateNeighborsReroute(cur, dist, pq, height, v)
        }
        return null
    }

    private fun determinePathBackToBase(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        startRoad: Road,
        distStart: Int,
        distEnd: Int
    ): Position {
        if (dist[cur.first].vertexList[0] == startRoad.start) {
            if (distStart != 0) {
                dist[cur.first].roadList.add(startRoad)
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = startRoad.start
                dist[cur.first].distanceFromStart = distEnd
                dist[cur.first].distanceFromEnd = distStart
            } else {
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                dist[cur.first].distanceFromStart = 0
                dist[cur.first].distanceFromEnd =
                    requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])).getActualWeight()
            }
        } else {
            if (distEnd != 0) {
                dist[cur.first].roadList.add(startRoad)
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = startRoad.end
                dist[cur.first].distanceFromStart = distStart
                dist[cur.first].distanceFromEnd = distEnd
            } else {
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                dist[cur.first].distanceFromStart = 0
                dist[cur.first].distanceFromEnd =
                    requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])).getActualWeight()
            }
        }
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            dist[cur.first].roadList.add(
                requireNotNull(requireNotNull(gm2).getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1]))
            )
        }
        return dist[cur.first]
    }

    private fun updateNeighborsBackToBase(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        pq: PriorityQueue<Pair<Int, Int>>,
        height: Int,
        v: Vertex
    ) {
        val nex: Map<Vertex, Road> = requireNotNull(gm2).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (edge.height <= height) {
                val newp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                newp.distance = dist[cur.first].distance + edge.getActualWeight()
                newp.vertexList = dist[cur.first].vertexList.toMutableList()
                newp.vertexList.add(v)
                if (dist[node.id].distance > newp.distance ||
                    (dist[node.id].distance == newp.distance && newp.smaller(dist[node.id]))
                ) {
                    dist[node.id] = newp
                    pq.add(Pair(node.id, newp.distance))
                }
            }
        }
    }

    /**
     * Doing dijkstra to the base
     */
    fun dijkstraBackToBase(
        startRoad: Road,
        distStart: Int,
        distEnd: Int,
        dir: Vertex,
        endNode: Int,
        height: Int
    ): Position? {
        val gm: GraphMap = requireNotNull(gm2)
        val n: Int = gm.vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(
                mutableListOf<Road>(),
                mutableListOf<Vertex>(),
                0,
                0,
                null,
                0,
                0
            )
        }
        for (i in 0..n - 1) {
            dist[i].distance = Int.MAX_VALUE
        }
        if (startRoad.start != dir) {
            dist[startRoad.start.id].distance = distStart
            dist[startRoad.end.id].distance = distEnd
        } else {
            dist[startRoad.start.id].distance = distEnd
            dist[startRoad.end.id].distance = distStart
        }
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(gm.getVertex(cur.first))
            if (endNode == v.id) {
                return determinePathBackToBase(cur, dist, startRoad, distStart, distEnd)
            }
            updateNeighborsBackToBase(cur, dist, pq, height, v)
        }
        return null
    }
}
