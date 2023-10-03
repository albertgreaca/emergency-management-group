package de.unisaarland.cs.se.selab.graphlogic

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.bases.Hospital
import de.unisaarland.cs.se.selab.bases.PoliceStation
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.utils.Position
import java.util.PriorityQueue

/**
 * Class representing Dijkstra
 */
object Dijkstra {
    const val divisor = 10

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
        val nex: Map<Vertex, Road> = requireNotNull(requireNotNull(Simulation.map)).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (dist[node.realid] > dist[cur.first] + edge.weight) {
                dist[node.realid] = dist[cur.first] + edge.weight
                pq.add(Pair(node.realid, dist[node.realid]))
            }
        }
    }

    /**
     * Doing dijkstra for emergencies
     */
    fun dijkstraEmergency(startingNode: Int, startingNode2: Int, et: EmergencyType): Base? {
        val n: Int = requireNotNull(Simulation.map).vertexList.size
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
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
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
        val n: Int = requireNotNull(Simulation.map).vertexList.size
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
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (v.base != null) {
                val b: Base = requireNotNull(v.base)
                ans.add(b)
            }
            val nex: Map<Vertex, Road> = requireNotNull(Simulation.map).adjacencyList[cur.first]
            for ((node, edge) in nex) {
                if (dist[node.realid] > dist[cur.first] + edge.getActualWeight()) {
                    dist[node.realid] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.realid, dist[node.realid]))
                }
            }
        }
        return ans
    }

    private fun determinePathHeight(cur: Pair<Int, Int>, dist: Array<Position>): Position {
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            dist[cur.first].roadList.add(
                requireNotNull(
                    requireNotNull(requireNotNull(Simulation.map)).getRoad(
                        dist[cur.first].vertexList[i],
                        dist[cur.first].vertexList[i + 1]
                    )
                )
            )
        }
        dist[cur.first].distance = cur.second
        if (dist[cur.first].vertexList.size >= 2) {
            dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
            dist[cur.first].distanceFromStart = 0
            dist[cur.first].distanceFromEnd = dist[cur.first].roadList[0].getActualWeight()
        }
        dist[cur.first].arrivalTicks = dist[cur.first].distance / divisor
        if (dist[cur.first].distance % divisor != 0 || dist[cur.first].distance == 0) {
            dist[cur.first].arrivalTicks++
        }
        return dist[cur.first]
    }

    private fun updateNeighborsHeight(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        pq: PriorityQueue<Pair<Int, Int>>,
        height: Int,
    ) {
        val nex: Map<Vertex, Road> = requireNotNull(requireNotNull(Simulation.map)).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (edge.height >= height) {
                val newp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                newp.distance = dist[cur.first].distance + edge.getActualWeight()
                newp.vertexList = dist[cur.first].vertexList.toMutableList()
                newp.vertexList.add(node)
                if (dist[node.realid].distance > newp.distance ||
                    (dist[node.realid].distance == newp.distance && newp.smaller(dist[node.realid]))
                ) {
                    dist[node.realid] = newp
                    pq.add(Pair(node.realid, newp.distance))
                }
            }
        }
    }

    /**
     * Doing Dijkstra respecting the height of vehicles and roads
     */
    fun dijkstraHeight(startingNode: Int, endRoad: Road, height: Int): Position {
        var position1: Position? = null
        var position2: Position? = null

        val n: Int = requireNotNull(Simulation.map).vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)
        }
        dist[startingNode].distance = 0
        dist[startingNode].vertexList.add(requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(startingNode)))
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        while (!pq.isEmpty() && (position1 == null || position2 == null)) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (endRoad.start == v) {
                position1 = determinePathHeight(cur, dist)
            }
            if (endRoad.end == v) {
                position2 = determinePathHeight(cur, dist)
            }
            updateNeighborsHeight(cur, dist, pq, height)
        }
        if (requireNotNull(position1).distance < requireNotNull(position2).distance || (requireNotNull(position1).distance == requireNotNull(position2).distance && position1.smaller(position2)))
            return position1
        else
            return position2
    }

    private fun determinePathRB(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        startRoad: Road,
        distStart: Int,
        distEnd: Int
    ): Position {
        if (dist[cur.first].vertexList[0] == startRoad.start && distStart != 0) {
            dist[cur.first].roadList.add(startRoad)
            dist[cur.first].distance = cur.second
            dist[cur.first].destinationVertex = startRoad.start
            dist[cur.first].distanceFromStart = distEnd
            dist[cur.first].distanceFromEnd = distStart
        } else {
            if (dist[cur.first].vertexList[0] == startRoad.end && distEnd != 0) {
                dist[cur.first].roadList.add(startRoad)
                dist[cur.first].distance = cur.second
                dist[cur.first].destinationVertex = startRoad.end
                dist[cur.first].distanceFromStart = distStart
                dist[cur.first].distanceFromEnd = distEnd
            } else {
                dist[cur.first].distance = cur.second
                if (dist[cur.first].vertexList.size >= 2) {
                    dist[cur.first].destinationVertex = dist[cur.first].vertexList[1]
                    dist[cur.first].distanceFromStart = 0
                    dist[cur.first].distanceFromEnd =
                        requireNotNull(
                            requireNotNull(requireNotNull(Simulation.map)).getRoad(
                                dist[cur.first].vertexList[0],
                                dist[cur.first].vertexList[1]
                            )
                        ).getActualWeight()
                }
            }
        }
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            dist[cur.first].roadList.add(
                requireNotNull(
                    requireNotNull(
                        requireNotNull(Simulation.map)
                    ).getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1])
                )
            )
        }
        dist[cur.first].arrivalTicks = dist[cur.first].distance / divisor
        if (dist[cur.first].distance % divisor != 0 || dist[cur.first].distance == 0) {
            dist[cur.first].arrivalTicks++
        }
        return dist[cur.first]
    }

    private fun updateNeighborsRB(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        pq: PriorityQueue<Pair<Int, Int>>,
        height: Int,
    ) {
        val nex: Map<Vertex, Road> = requireNotNull(requireNotNull(Simulation.map)).adjacencyList[cur.first]
        for ((node, edge) in nex) {
            if (edge.height >= height) {
                val newp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
                newp.distance = dist[cur.first].distance + edge.getActualWeight()
                newp.vertexList = dist[cur.first].vertexList.toMutableList()
                newp.vertexList.add(node)
                if (dist[node.realid].distance > newp.distance ||
                    (dist[node.realid].distance == newp.distance && newp.smaller(dist[node.realid]))
                ) {
                    dist[node.realid] = newp
                    pq.add(Pair(node.realid, newp.distance))
                }
            }
        }
    }

    /**
     * Doing Dijkstra for Rerouting
     */
    fun dijkstraReroute(
        startRoad: Road,
        distStart: Int,
        distEnd: Int,
        dir: Vertex,
        endRoad: Road,
        height: Int
    ): Position {
        var position1: Position? = null
        var position2: Position? = null

        val n: Int = requireNotNull(Simulation.map).vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(
                mutableListOf<Road>(),
                mutableListOf<Vertex>(),
                0,
                0,
                null,
                Int.MAX_VALUE,
                0
            )
        }
        if (startRoad.start != dir) {
            dist[startRoad.start.realid].distance = distStart
            dist[startRoad.end.realid].distance = distEnd
        } else {
            dist[startRoad.start.realid].distance = distEnd
            dist[startRoad.end.realid].distance = distStart
        }
        dist[startRoad.start.realid].vertexList.add(startRoad.start)
        dist[startRoad.end.realid].vertexList.add(startRoad.end)
        val compare: Comparator<Pair<Int, Int>> = compareBy { it.second }
        val pq: PriorityQueue<Pair<Int, Int>> = PriorityQueue<Pair<Int, Int>>(compare)
        for (i in 0..n - 1) {
            pq.add(Pair(i, dist[i].distance))
        }
        while (!pq.isEmpty() && (position1 == null || position2 == null)) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (endRoad.start == v) {
                position1 = determinePathRB(cur, dist, startRoad, distStart, distEnd)
            }
            if (endRoad.end == v) {
                position2 = determinePathRB(cur, dist, startRoad, distStart, distEnd)
            }
            updateNeighborsRB(cur, dist, pq, height)
        }
        if (requireNotNull(position1).distance < requireNotNull(position2).distance || (requireNotNull(position1).distance == requireNotNull(position2).distance && position1.smaller(position2)))
            return position1
        else
            return position2
    }

    /**
     * determining first path to the base
     */
    fun dijkstraBackToBase(
        startingNode: Int,
        endNode: Int,
        height: Int
    ): Position? {
        val n: Int = requireNotNull(Simulation.map).vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(
                mutableListOf<Road>(),
                mutableListOf<Vertex>(),
                0,
                0,
                null,
                Int.MAX_VALUE,
                0
            )
        }
        dist[startingNode].distance = 0
        dist[startingNode].vertexList.add(requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(startingNode)))
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
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (endNode == v.realid) {
                return determinePathHeight(cur, dist)
            }
            updateNeighborsHeight(cur, dist, pq, height)
        }
        return null
    }

    /**
     * Doing dijkstra to reroute to the base
     */
    fun dijkstraRerouteBackToBase(
        startRoad: Road,
        distStart: Int,
        distEnd: Int,
        dir: Vertex,
        endNode: Int,
        height: Int
    ): Position? {
        val n: Int = requireNotNull(Simulation.map).vertexList.size
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
            dist[startRoad.start.realid].distance = distStart
            dist[startRoad.end.realid].distance = distEnd
        } else {
            dist[startRoad.start.realid].distance = distEnd
            dist[startRoad.end.realid].distance = distStart
        }
        dist[startRoad.start.realid].vertexList.add(startRoad.start)
        dist[startRoad.end.realid].vertexList.add(startRoad.end)
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
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (endNode == v.realid) {
                return determinePathRB(cur, dist, startRoad, distStart, distEnd)
            }
            updateNeighborsRB(cur, dist, pq, height)
        }
        return null
    }
}
