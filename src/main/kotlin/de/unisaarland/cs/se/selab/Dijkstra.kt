package de.unisaarland.cs.se.selab

import java.util.PriorityQueue

/**
 * Class representing Dijkstra
 */
object Dijkstra {
    var gm2: GraphMap? = null

    /**
     * Doing dijkstra for emergencies
     */
    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist = IntArray(n)
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

    /**
     * Doing dijkstra for requesting
     */
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

    /**
     * Doing Dijkstra respecting the height of vehicles and roads
     */
    fun dijkstraHeight(startingNode: Int, endRoad: Road, height: Int): Position? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: Array<Position> = Array<Position>(n) { index ->
            Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
        }
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
                for (i in 0..dist[cur.first].vertexList.size - 2) {
                    dist[cur.first].roadList.add(
                        gm.getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1])!!
                    )
                }
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                dist[cur.first].distanceFromStart = 0
                dist[cur.first].distanceFromEnd = dist[cur.first].roadList[0].getActualWeight()
                return dist[cur.first]
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (edge.height <= height) {
                    var newp: Position = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                    newp.distance = dist[cur.first].distance + edge.getActualWeight()
                    newp.vertexList = dist[cur.first].vertexList.toMutableList()
                    newp.vertexList.add(v)
                    if (dist[node.id].distance > newp.distance || (
                            dist[node.id].distance == newp.distance && newp.smaller(
                                dist[node.id]
                            )
                            )
                    ) {
                        dist[node.id] = newp
                        pq.add(Pair(node.id, newp.distance))
                    }
                }
            }
        }
        return null
    }

    /**
     * Doing Dijkstra for Reallocation
     */
    fun dijkstraReallocate(
        startRoad: Road,
        distStart: Int,
        distEnd: Int,
        dir: Vertex,
        endRoad: Road,
        height: Int
    ): Position? {
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: Array<Position> = Array<Position>(n) { index ->
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
        var i: Int
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
                            gm.getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])!!.getActualWeight()
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
                            gm.getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])!!.getActualWeight()
                    }
                }
                for (i in 0..dist[cur.first].vertexList.size - 2) {
                    dist[cur.first].roadList.add(
                        gm.getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1])!!
                    )
                }
                return dist[cur.first]
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (edge.height <= height) {
                    var newp: Position = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                    newp.distance = dist[cur.first].distance + edge.getActualWeight()
                    newp.vertexList = dist[cur.first].vertexList.toMutableList()
                    newp.vertexList.add(v)
                    if (dist[node.id].distance > newp.distance || (
                            dist[node.id].distance == newp.distance && newp.smaller(
                                dist[node.id]
                            )
                            )
                    ) {
                        dist[node.id] = newp
                        pq.add(Pair(node.id, newp.distance))
                    }
                }
            }
        }
        return null
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
        var gm: GraphMap = gm2!!
        var n: Int = gm.vertexList.size
        var dist: Array<Position> = Array<Position>(n) { index ->
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
        var i: Int
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
            if (endNode == v.id) {
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
                            gm.getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])!!.getActualWeight()
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
                            gm.getRoad(dist[cur.first].vertexList[0], dist[cur.first].vertexList[1])!!.getActualWeight()
                    }
                }
                for (i in 0..dist[cur.first].vertexList.size - 2) {
                    dist[cur.first].roadList.add(
                        gm.getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1])!!
                    )
                }
                return dist[cur.first]
            }
            val nex: Map<Vertex, Road> = gm.adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (edge.height <= height) {
                    var newp: Position = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                    newp.distance = dist[cur.first].distance + edge.getActualWeight()
                    newp.vertexList = dist[cur.first].vertexList.toMutableList()
                    newp.vertexList.add(v)
                    if (dist[node.id].distance > newp.distance || (
                            dist[node.id].distance == newp.distance &&
                                newp.smaller(dist[node.id])
                            )
                    ) {
                        dist[node.id] = newp
                        pq.add(Pair(node.id, newp.distance))
                    }
                }
            }
        }
        return null
    }
}
