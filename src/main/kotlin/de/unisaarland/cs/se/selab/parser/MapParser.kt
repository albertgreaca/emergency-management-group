package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import io.github.oshai.kotlinlogging.KotlinLogging
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
    private val logger = KotlinLogging.logger {}

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
                logger.error { "validateNumber failed, line 55" }
                return false
            }
            val vertexID = vertexIDS.toIntOrNull() ?: return false
            val vert = Vertex(vertexID, null, curIndex)
            if (!validateVertex(vert)) {
                logger.error { "validateVertex failed, line 65" }
                return false
            }
            gm.addVertex(vert)
            mapVerCon[vertexID] = false
            curIndex++
            i++
        }
        if (!(i + 1 < tokenlist.size && tokenlist[i + 1].tokenkind == LexerToken.ARROW)) {
            logger.error { "end of parseVertices failed, line 74" }
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
                logger.error { "conditions failed on one of line 91, 92, 93, line 94" }
                return false
            }
            verToVer.add(Pair(start, end))
        }
        if (!(i < tokenlist.size && tokenlist[i].tokenkind == LexerToken.CRPARENTHESES)) {
            logger.error { "end of parseEdges failed, line 100" }
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
                logger.error { "parseAttribute fails, line 124" }
                return false
            }
            resultMap[attribute] = p.first
        }
        val res = parsePrimAndSecType()
        if (!res.third) {
            logger.error { "parsePrimAndSecType fails, line 131" }
            return false
        }
        val primtype = requireNotNull(res.first)
        val sectype = requireNotNull(res.second)
        val ans = ret && helper(resultMap, start, end, primtype, sectype)
        if (!ans) {
            logger.error { "end of parseAttributes fails, line 138" }
        }
        return ans
    }
    private fun helper(
        resultMap: Map<LexerToken, String>,
        start: Int,
        end: Int,
        primtype: PrimaryRoadType,
        sectype: SecondaryRoadType
    ): Boolean {
        var ret = validateAttributes(resultMap, start, end, primtype)
        if (ret == false) {
            logger.error { "validateAttributes failed, line 151" }
        }
        ret = ret && createObject(resultMap, primtype, sectype, start, end) && i < tokenlist.size
        if (ret == false) {
            logger.error { "createObject or i < failed, line 155" }
        }
        ret = ret && tokenlist[i++].tokenkind == LexerToken.RPARENTHESES
        if (ret == false) {
            logger.error { "next token isn't ], line 159" }
        }
        ret = ret && requireNotNull(resultMap[LexerToken.WEIGHT]).toInt() > 0
        if (ret == false) {
            logger.error { "weight isn't integer or > 0, line 163" }
        }
        ret = ret && requireNotNull(resultMap[LexerToken.HEIGHTLIMIT]).toInt() >= 1
        if (ret == false) {
            logger.error { "height isn't integer or >= 1, line 167" }
        }
        if (sectype == SecondaryRoadType.TUNNEL) {
            ret = ret && requireNotNull(resultMap[LexerToken.HEIGHTLIMIT]).toInt() <= 3
            if (ret == false) {
                logger.error { "height isn't integer or <= 3 for tunnel, line 172" }
            }
        }
        return ret
    }
    private fun parsePrimAndSecType(): Triple<PrimaryRoadType?, SecondaryRoadType?, Boolean> {
        var ret = i + SEVEN < tokenlist.size && tokenlist[i++].tokenkind == LexerToken.PRIMARYTYPE
        if (ret == false) {
            logger.error { "not enought tokens or kind isn't PRIMARYTYPE, line 180" }
        }
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        if (ret == false) {
            logger.error { "next token kind isn't EQUAL, line 184" }
        }
        val primtype = validatePrimaryType(tokenlist[i++].tokenkind) ?: return Triple(null, null, false)
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        if (ret == false) {
            logger.error { "next token kind isn't SEMICOLON, line 189" }
        }
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SECONDARYTYPE
        if (ret == false) {
            logger.error { "next token kind isn't SECONDARYTYPE, line 193" }
        }
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        val sectype = validateSecondaryType(tokenlist[i++].tokenkind) ?: return Triple(null, null, false)
        ret = ret && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        if (ret == false) {
            logger.error { "next token kind isn't EQUAL on line 195 or SEMICOLON on line 197, line 199" }
        }
        return Triple(primtype, sectype, ret)
    }

    private fun parseAttribute(token: LexerToken, isInt: Boolean): Pair<String, Boolean> {
        var ret = i + 3 < tokenlist.size && tokenlist[i++].tokenkind == token
        if (ret == false) {
            logger.error { "next token kind isn't the right one, line 207" }
        }
        ret = ret && tokenlist[i++].tokenkind == LexerToken.EQUAL
        if (ret == false) {
            logger.error { "next token kind isn't EQUAL, line 211" }
        }
        val ret2 = tokenlist[i++].text
        ret = ret && (validateId(ret2) || isInt) && tokenlist[i++].tokenkind == LexerToken.SEMICOLON
        if (ret == false) {
            logger.error { "validating id int: $isInt fails or next token kind isn't SEMICOLON, line 216" }
        }
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
        val ans = ret && validateNotCountyRoad(resultMap, start, end, primarytype)
        if (!ans) {
            logger.error { "fuckery in validateAttributes, line 298" }
        }
        return ans
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
        if (ret == false) {
            logger.error { "fuckery in validateNotCountyRoad, line 321" }
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
            if (res == false) {
                logger.error { "height isn't number or validation fails, line 339" }
            }
        }
        val heightlimit = resultMap[LexerToken.HEIGHTLIMIT]?.toIntOrNull()
        if (resultMap[LexerToken.WEIGHT] != null) {
            res = res && validateNumber(requireNotNull(resultMap[LexerToken.WEIGHT]))
            if (res == false) {
                logger.error { "weight isn't number or validation fails, line 346" }
            }
        }
        val weight = resultMap[LexerToken.WEIGHT]?.toIntOrNull() ?: return false
        val startv = gm.getVertexFromId(start) ?: return false
        val endv = gm.getVertexFromId(end) ?: return false
        if (name == null || village == null || heightlimit == null) {
            logger.error { "name, village or heightlimit is null, line 353" }
            return false
        }
        val road = Road(pty, sty, village, name, weight, heightlimit, startv, endv)
        gm.addRoad(road, start, end)
        if (res == false) {
            logger.error { "uhh idk what happens here, line 359" }
        }
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
