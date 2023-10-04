package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import java.io.File

/** Class for Parsing dot Files
 * digraph Id {
 * Id;
 * Id -> Id [attributes'];
 * }
 */
class MapParser(private val gm: GraphMap, file: File) {
    private val tokenlist = Lexer().lex(file.readText())
    private var graphName = ""
    private val vilRoadSet = mutableSetOf<Pair<String, String>>()
    private val verToVer = mutableSetOf<Pair<Int, Int>>()
    private var sideStreetCount = false
    private val mapVilMain = mutableMapOf<String, Boolean>()
    private val mapVilVer = mutableMapOf<Int, String>()
    private val mapVerCon = mutableMapOf<Int, Boolean>()
    private val countyList = mutableListOf<String>()
    private val villist = mutableListOf<String>()
    private var curIndex = 0
    private var i = 0

    /**
     * Starting function for parsing
     * @return Parsing Successful or not
     */
    fun parseMap(): Boolean {
        var ret: Boolean
        if (i + 2 < tokenlist.size) {
            ret = tokenlist[i++].tokenkind == LexerToken.DIGRAPH
            graphName = tokenlist[i++].text
            ret = ret && tokenlist[i++].tokenkind == LexerToken.CLPARENTHESES
            ret = ret && validateId(graphName) && parseVertices() && parseEdges() && i == tokenlist.size
            ret = ret && sideStreetCount && !mapVilMain.containsValue(false) && !mapVerCon.containsValue(false)
        } else {
            ret = false
        }
        return ret
    }

    /**
     * Parsing every Vertex of the Form
     * Id;
     * @return Parsing Successful or not
     */
    private fun parseVertices(): Boolean {
        while (i + 1 < tokenlist.size && tokenlist[i + 1].tokenkind == LexerToken.SEMICOLON) {
            val vertexIDS = tokenlist[i++].text
            if (!validateNumber(vertexIDS)) {
                return false
            }
            val vertexID = vertexIDS.toIntOrNull() ?: return false
            val vert = Vertex(vertexID, null, curIndex)
            if (!validateVertex(vert)) {
                return false
            }
            gm.addVertex(vert)
            mapVerCon[vertexID] = false
            curIndex++
            i++
        }
        return i + 1 < tokenlist.size && tokenlist[i + 1].tokenkind == LexerToken.ARROW
    }

    /**
     * Parsing every Edge of the Form
     * Id -> Id['attributes'];
     * @return Parsing Successful or not
     */
    private fun parseEdges(): Boolean {
        while (i + 2 < tokenlist.size && tokenlist[i + 1].tokenkind == LexerToken.ARROW) {
            val start = tokenlist[i++].text.toIntOrNull() ?: return false
            i++
            val end = tokenlist[i++].text.toIntOrNull() ?: return false
            mapVerCon[start] = true
            mapVerCon[end] = true
            var ret = start != end && !verToVer.contains(Pair(start, end)) && !verToVer.contains(Pair(end, start))
            ret = ret && parseAttributes(start, end)
            if (!ret || i >= tokenlist.size || tokenlist[i++].tokenkind != LexerToken.SEMICOLON) {
                return false
            }
            verToVer.add(Pair(start, end))
        }
        return i < tokenlist.size && tokenlist[i++].tokenkind == LexerToken.CRPARENTHESES
    }

    /**
     * Parsing Attributes of the edge
     * ['village = Id; name = Id; heightlimit = Id; name = Id; primaryType = <PrimaryType>;
     * secondaryType = <SecondaryType>']
     * @return Parsing Successful or not
     */
    private fun parseAttributes(start: Int, end: Int): Boolean {
        val ret = i < tokenlist.size && tokenlist[i++].tokenkind == LexerToken.LPARENTHESES
        val attributeslist =
            mutableListOf(LexerToken.VILLAGE, LexerToken.NAME, LexerToken.HEIGHTLIMIT, LexerToken.WEIGHT)
        val resultMap = mutableMapOf<LexerToken, String>()
        for ((k, attribute) in attributeslist.withIndex()) {
            var p: Pair<String, Boolean>
            if (k >= 2) {
                p = parseAttribute(attribute, true)
            } else {
                p = parseAttribute(attribute, false)
            }
            if (!p.second) {
                return false
            }
            resultMap[attribute] = p.first
        }
        val res = parsePrimAndSecType()
        if (!res.third) {
            return false
        }
        val primtype = requireNotNull(res.first)
        val sectype = requireNotNull(res.second)

        return ret && helper(resultMap, start, end, primtype, sectype)
    }
    private fun helper(
        resultMap: Map<LexerToken, String>,
        start: Int,
        end: Int,
        primtype: PrimaryRoadType,
        sectype: SecondaryRoadType
    ): Boolean {
        var ret = validateAttributes(resultMap, start, end, primtype)
        ret = ret && createObject(resultMap, primtype, sectype, start, end) && i < tokenlist.size
        ret = ret && tokenlist[i++].tokenkind == LexerToken.RPARENTHESES
        ret = ret && requireNotNull(resultMap[LexerToken.WEIGHT]).toInt() > 0
        ret = ret && requireNotNull(resultMap[LexerToken.HEIGHTLIMIT]).toInt() >= 1
        if (sectype == SecondaryRoadType.TUNNEL) {
            ret = ret && requireNotNull(resultMap[LexerToken.HEIGHTLIMIT]).toInt() <= 3
        }
        return ret
    }
    private fun parsePrimAndSecType(): Triple<PrimaryRoadType?, SecondaryRoadType?, Boolean> {
        var ret = i + SEVEN < tokenlist.size && tokenlist[i++].tokenkind == LexerToken.PRIMARYTYPE
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        val primtype = validatePrimaryType(tokenlist[i++].tokenkind) ?: return Triple(null, null, false)
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SECONDARYTYPE
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        val sectype = validateSecondaryType(tokenlist[i++].tokenkind) ?: return Triple(null, null, false)
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        return Triple(primtype, sectype, ret)
    }

    private fun parseAttribute(token: LexerToken, isInt: Boolean): Pair<String, Boolean> {
        var ret = i + 3 < tokenlist.size && tokenlist[i++].tokenkind == token
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        val ret2 = tokenlist[i++].text
        ret = ret && (validateId(ret2) || isInt) && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        return Pair(ret2, ret)
    }

    private fun validateVertex(v: Vertex): Boolean {
        return v.id >= 0 && gm.getVertexFromId(v.id) == null
    }

    private fun validateId(id: String): Boolean {
        val arr = id.toCharArray()
        if (id == "" || arr[0] < 'A' || arr[0] > 'Z') {
            if (id == "" || arr[0] < 'a' || arr[0] > 'z') {
                return false
            }
        }

        for (ch in arr) {
            if (ch < 'A' || ch > 'Z') {
                if ((ch < 'a' || ch > 'z') && ch != '_') {
                    return false
                }
            }
        }
        return true
    }

    private fun validatePrimaryType(ty: LexerToken): PrimaryRoadType? {
        return when (ty) {
            LexerToken.MAINSTREET -> PrimaryRoadType.MAINSTREET
            LexerToken.SIDESTREET -> PrimaryRoadType.SIDESTREET
            LexerToken.COUNTYROAD -> PrimaryRoadType.COUNTYROAD
            else -> {
                null
            }
        }
    }

    private fun validateSecondaryType(ty: LexerToken): SecondaryRoadType? {
        return when (ty) {
            LexerToken.ONEWAYSTREET -> SecondaryRoadType.ONEWAYSTREET
            LexerToken.TUNNEL -> SecondaryRoadType.TUNNEL
            LexerToken.NONE -> SecondaryRoadType.NONE
            else -> {
                null
            }
        }
    }

    private fun validateAttributes(
        resultMap: Map<LexerToken, String>,
        start: Int,
        end: Int,
        primarytype: PrimaryRoadType
    ): Boolean {
        var ret = !vilRoadSet.contains(Pair(resultMap[LexerToken.VILLAGE], resultMap[LexerToken.NAME]))
        vilRoadSet.add(Pair(requireNotNull(resultMap[LexerToken.VILLAGE]), requireNotNull(resultMap[LexerToken.NAME])))
        if (primarytype == PrimaryRoadType.SIDESTREET) {
            sideStreetCount = true
        }
        if (primarytype == PrimaryRoadType.COUNTYROAD) {
            ret = ret && !villist.contains(resultMap[LexerToken.VILLAGE])
            if (!countyList.contains(resultMap[LexerToken.VILLAGE])) {
                countyList.add(requireNotNull(resultMap[LexerToken.VILLAGE]))
            }
        } else {
            ret = ret && !countyList.contains(resultMap[LexerToken.VILLAGE])
            if (!villist.contains(resultMap[LexerToken.VILLAGE])) {
                villist.add(requireNotNull(resultMap[LexerToken.VILLAGE]))
            }
        }
        if (primarytype != PrimaryRoadType.COUNTYROAD && !mapVilMain.contains(resultMap[LexerToken.VILLAGE])) {
            mapVilMain[requireNotNull(resultMap[LexerToken.VILLAGE])] = false
        }
        if (primarytype == PrimaryRoadType.MAINSTREET) {
            mapVilMain.replace(requireNotNull(resultMap[LexerToken.VILLAGE]), false, true)
        }
        return ret && validateNotCountyRoad(resultMap, start, end, primarytype)
    }

    private fun validateNotCountyRoad(
        resultMap: Map<LexerToken, String>,
        start: Int,
        end: Int,
        primarytype: PrimaryRoadType
    ): Boolean {
        var ret = true
        if (primarytype != PrimaryRoadType.COUNTYROAD) {
            ret = ret && resultMap[LexerToken.VILLAGE] != graphName
            if (mapVilVer.contains(start)) {
                ret = ret && mapVilVer[start] == resultMap[LexerToken.VILLAGE]
            } else {
                mapVilVer[start] = requireNotNull(resultMap[LexerToken.VILLAGE])
            }
            if (mapVilVer.contains(end)) {
                ret = ret && mapVilVer[end] == resultMap[LexerToken.VILLAGE]
            } else {
                mapVilVer[end] = requireNotNull(resultMap[LexerToken.VILLAGE])
            }
        }
        return ret
    }

    private fun createObject(
        resultMap: Map<LexerToken, String>,
        pty: PrimaryRoadType,
        sty: SecondaryRoadType,
        start: Int,
        end: Int
    ): Boolean {
        val village = resultMap[LexerToken.VILLAGE]
        val name = resultMap[LexerToken.NAME]
        var res = true
        if (resultMap[LexerToken.HEIGHTLIMIT] != null) {
            res = res && validateNumber(requireNotNull(resultMap[LexerToken.HEIGHTLIMIT]))
        }
        val heightlimit = resultMap[LexerToken.HEIGHTLIMIT]?.toIntOrNull()
        if (resultMap[LexerToken.WEIGHT] != null) {
            res = res && validateNumber(requireNotNull(resultMap[LexerToken.WEIGHT]))
        }
        val weight = resultMap[LexerToken.WEIGHT]?.toIntOrNull() ?: return false
        val startv = gm.getVertexFromId(start) ?: return false
        val endv = gm.getVertexFromId(end) ?: return false
        if (name == null || village == null || heightlimit == null) {
            return false
        }
        val road = Road(pty, sty, village, name, weight, heightlimit, startv, endv)
        gm.addRoad(road, start, end)
        return res
    }

    private fun validateNumber(id: String): Boolean {
        if (id == "0") {
            return true
        } else if (id[0] == '0') {
            return false
        } else {
            return true
        }
    }

    companion object {
        const val SEVEN = 7
    }
}
