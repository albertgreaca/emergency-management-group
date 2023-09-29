package de.unisaarland.cs.se.selab

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
    private var curIndex = 0
    private var i = 0

    /**
     * Starting function for parsing
     * @return Parsing Successful or not
     */
    fun parseMap(): Boolean {
        var ret = true
        if (i < tokenlist.size && tokenlist.size > minTokens) {
            ret = tokenlist[i++].tokenkind == LexerToken.DIGRAPH
            graphName = tokenlist[i++].text
            ret = tokenlist[i++].tokenkind == LexerToken.CLPARENTHESES
            ret = ret && validateId(graphName) && parseVertices() && parseEdges() && i == tokenlist.size
            ret = ret && sideStreetCount && !mapVilMain.containsValue(false) && !mapVerCon.containsValue(false) &&
                gm.roadList.isNotEmpty()
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
        while (tokenlist[i + 1].tokenkind == LexerToken.SEMICOLON) {
            val vertexID = tokenlist[i++].text.toIntOrNull() ?: return false
            val vert = Vertex(vertexID, null, curIndex)
            if (!validateVertex(vert)) {
                return false
            }
            gm.addVertex(vert)
            mapVerCon[vertexID] = false
            curIndex++
            i++
        }
        return tokenlist[i + 1].tokenkind == LexerToken.ARROW
    }

    /**
     * Parsing every Edge of the Form
     * Id -> Id['attributes'];
     * @return Parsing Successful or not
     */
    private fun parseEdges(): Boolean {
        while (i + 1 < tokenlist.size && tokenlist[i + 1].tokenkind == LexerToken.ARROW) {
            val start = tokenlist[i++].text.toIntOrNull() ?: return false
            i++
            val end = tokenlist[i++].text.toIntOrNull() ?: return false
            mapVerCon[start] = true
            mapVerCon[end] = true
            val ret = start != end && !verToVer.contains(Pair(start, end)) && !verToVer.contains(Pair(end, start)) &&
                parseAttributes(start, end)
            if (!ret || tokenlist[i++].tokenkind != LexerToken.SEMICOLON) {
                return false
            }
            verToVer.add(Pair(start, end))
        }
        return tokenlist[i++].tokenkind == LexerToken.CRPARENTHESES
    }

    /**
     * Parsing Attributes of the edge
     * ['village = Id; name = Id; heightlimit = Id; name = Id; primaryType = <PrimaryType>;
     * secondaryType = <SecondaryType>']
     * @return Parsing Successful or not
     */
    private fun parseAttributes(start: Int, end: Int): Boolean {
        var ret = tokenlist[i++].tokenkind == LexerToken.LPARENTHESES
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
        ret = tokenlist[i++].tokenkind == LexerToken.PRIMARYTYPE &&
            tokenlist[i++].tokenkind == LexerToken.EQUAL
        val primtype = validatePrimaryType(tokenlist[i++].tokenkind) ?: return false
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        ret = tokenlist[i++].tokenkind == LexerToken.SECONDARYTYPE &&
            tokenlist[i++].tokenkind == LexerToken.EQUAL
        val sectype = validateSecondaryType(tokenlist[i++].tokenkind) ?: return false
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        ret = ret && validateAttributes(resultMap, start, end, primtype)
        return ret && createObject(resultMap, primtype, sectype, start, end) &&
            tokenlist[i++].tokenkind == LexerToken.RPARENTHESES
    }

    private fun parseAttribute(token: LexerToken, isInt: Boolean): Pair<String, Boolean> {
        var ret = tokenlist[i++].tokenkind == token &&
            tokenlist[i++].tokenkind == LexerToken.EQUAL
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
            ret = ret && resultMap[LexerToken.VILLAGE] == graphName
        }
        if (primarytype == PrimaryRoadType.MAINSTREET && mapVilMain.contains(resultMap[LexerToken.VILLAGE])) {
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
            if (!mapVilMain.contains(requireNotNull(resultMap[LexerToken.VILLAGE]))) {
                mapVilMain[requireNotNull(resultMap[LexerToken.VILLAGE])] = primarytype == PrimaryRoadType.MAINSTREET
            }
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
        val heightlimit = resultMap[LexerToken.HEIGHTLIMIT]?.toIntOrNull()
        val weight = resultMap[LexerToken.WEIGHT]?.toIntOrNull() ?: return false
        val startv = gm.getVertexFromId(start) ?: return false
        val endv = gm.getVertexFromId(end) ?: return false
        if (name == null || village == null || heightlimit == null) {
            return false
        }
        val road = Road(pty, sty, village, name, weight, heightlimit, startv, endv)
        gm.addRoad(road, start, end)
        return true
    }
    companion object {
        const val minTokens = 10
    }
}
