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
            if (dist[node.realid].toLong() > dist[cur.first].toLong() + edge.weight.toLong()) {
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
                if (dist[node.realid].toLong() > dist[cur.first].toLong() + edge.getActualWeight().toLong()) {
                    dist[node.realid] = dist[cur.first] + edge.getActualWeight()
                    pq.add(Pair(node.realid, dist[node.realid]))
                }
            }
        }
        return ans
    }

    private fun determinePathHeight(cur: Pair<Int, Int>, dist: Array<Position>): Position {
        val ansp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
        ansp.vertexList = dist[cur.first].vertexList.toMutableList()
        for (i in 0..dist[cur.first].vertexList.size - 2) {
            ansp.roadList.add(
                requireNotNull(
                    requireNotNull(requireNotNull(Simulation.map)).getRoad(
                        dist[cur.first].vertexList[i],
                        dist[cur.first].vertexList[i + 1]
                    )
                )
            )
        }
        ansp.distance = cur.second
        if (dist[cur.first].vertexList.size >= 2) {
            ansp.destinationVertex = ansp.vertexList[1]
            ansp.distanceFromStart = 0
            ansp.distanceFromEnd = ansp.roadList[0].getActualWeight()
        }
        ansp.arrivalTicks = ansp.distance / divisor
        if (ansp.distance % divisor != 0) {
            ansp.arrivalTicks++
        }
        if (ansp.distance == 0) {
            ansp.startedThisTickZero = true
            ansp.arrivalTicks++
        }
        return ansp
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
                if (dist[cur.first].distance.toLong() + edge.getActualWeight().toLong() > Int.MAX_VALUE.toLong()) {
                    continue
                }
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
        var position1 = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)
        var position2 = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)

        val n: Int = requireNotNull(Simulation.map).vertexList.size
        val dist: Array<Position> = Array<Position>(n) { index ->
            Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)
        }
        dist[startingNode].distance = 0
        dist[startingNode].vertexList.add(
            requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(startingNode))
        )
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
            if (endRoad.start == v) {
                position1 = position1.determine(determinePathHeight(cur, dist))
            }
            if (endRoad.end == v) {
                position2 = position2.determine(determinePathHeight(cur, dist))
            }
            updateNeighborsHeight(cur, dist, pq, height)
        }
        return position1.determine(position2)
    }

    private fun determinePathRB(
        cur: Pair<Int, Int>,
        dist: Array<Position>,
        startRoad: Road,
        distStart: Int,
        distEnd: Int
    ): Position {
        val ansp = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, 0, 0)
        ansp.vertexList = dist[cur.first].vertexList.toMutableList()
        if (dist[cur.first].vertexList[0] == startRoad.start && distStart != 0) {
            ansp.roadList.add(startRoad)
            ansp.distance = cur.second
            ansp.destinationVertex = startRoad.start
            ansp.distanceFromStart = distEnd
            ansp.distanceFromEnd = distStart
        } else {
            if (dist[cur.first].vertexList[0] == startRoad.end && distEnd != 0) {
                ansp.roadList.add(startRoad)
                ansp.distance = cur.second
                ansp.destinationVertex = startRoad.end
                ansp.distanceFromStart = distStart
                ansp.distanceFromEnd = distEnd
            } else {
                ansp.distance = cur.second
                if (dist[cur.first].vertexList.size >= 2) {
                    ansp.destinationVertex = dist[cur.first].vertexList[1]
                    ansp.distanceFromStart = 0
                    ansp.distanceFromEnd =
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
            ansp.roadList.add(
                requireNotNull(
                    requireNotNull(
                        requireNotNull(Simulation.map)
                    ).getRoad(dist[cur.first].vertexList[i], dist[cur.first].vertexList[i + 1])
                )
            )
        }
        ansp.arrivalTicks = ansp.distance / divisor
        if (ansp.distance % divisor != 0) {
            ansp.arrivalTicks++
        }
        if (ansp.distance == 0) {
            ansp.startedThisTickZero = true
            ansp.arrivalTicks++
        }
        return ansp
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
                if (dist[cur.first].distance.toLong() + edge.getActualWeight().toLong() > Int.MAX_VALUE.toLong()) {
                    continue
                }
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
        var position1 = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)
        var position2 = Position(mutableListOf<Road>(), mutableListOf<Vertex>(), 0, 0, null, Int.MAX_VALUE, 0)

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
        while (!pq.isEmpty()) {
            val cur: Pair<Int, Int> = pq.remove()
            if (dist[cur.first].distance != cur.second) {
                continue
            }
            val v: Vertex = requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(cur.first))
            if (endRoad.start == v) {
                position1 = position1.determine(determinePathRB(cur, dist, startRoad, distStart, distEnd))
            }
            if (endRoad.end == v) {
                position2 = position2.determine(determinePathRB(cur, dist, startRoad, distStart, distEnd))
            }
            updateNeighborsRB(cur, dist, pq, height)
        }
        return position1.determine(position2)
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
        dist[startingNode].vertexList.add(
            requireNotNull(requireNotNull(Simulation.map).getVertexFromRealId(startingNode))
        )
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
