package de.unisaarland.cs.se.selab

import java.io.File

/*
     val schem = getSchema(MapParser::class.java,"simulation")
     val json : JSONObject = JSONObject(file.readText())
     schem?.validate(json)
  */

/** Class for Parsing dot Files
 * digraph Id {
 * Id;
 * Id -> Id [attributes'];
 * }
 */
class MapParser(private val gm: GraphMap, file: File) {
    private var charcounter = 0
    private val chars = file.readText().toCharArray()
    private var graphName = ""
    private var vilRoadSet = mutableSetOf<Pair<String, String>>()
    private var verToVer = mutableSetOf<Pair<Int, Int>>()
    private var sideStreetCount = false
    private var mapVilMain = mutableMapOf<String, Boolean>()
    private var mapVilVer = mutableMapOf<Int, String>()

    /**
     * Starting function for parsing
     * @return Parsing Successful or not
     */
    fun parseMap(): Boolean {
        val next = getNextWord()
        if (next != "digraph") {
            return false
        }
        skipSpaces(true)
        graphName = getNextWord()
        if (!validateId(graphName)) {
            return false
        }
        skipSpaces(false)
        if (chars[charcounter] != '{') {
            return false
        }
        charcounter++
        return parseSgtStmtList() && validateGraphMap()
    }

    /**
     * Parsing everything in between { }
     * @return Parsing Successful or not
     */
    private fun parseSgtStmtList(): Boolean {
        skipSpaces(false)
        return parseVertices() && parseEdges()
    }

    /**
     * Parsing every Vertex of the Form
     * Id;
     * @return Parsing Successful or not
     */
    private fun parseVertices(): Boolean {
        var id = getNextInt() ?: return false
        skipSpaces(false)
        var sep = chars[charcounter]
        charcounter++
        while (sep == ';') {
            val vertex = Vertex(id, null)
            if (!validateVertex(vertex)) {
                return false
            }
            gm.addVertex(vertex)
            skipSpaces(false)
            id = getNextInt() ?: return false
            skipSpaces(false)
            sep = chars[charcounter]
            charcounter++
        }
        return sep == '-'
    }

    /**
     * Parsing every Edge of the Form
     * Id -> Id['attributes'];
     * @return Parsing Successful or not
     */
    private fun parseEdges(): Boolean {
        var start = getNextInt() ?: return false
        skipSpaces(false)
        var sep = chars[charcounter]
        while (sep == '-') {
            if (chars[charcounter] != '-' || chars[charcounter + 1] != '>') {
                return false
            }
            charcounter += 2
            val end = getNextInt()
            var ret = end == null || verToVer.contains(Pair(start, end)) || !parseAttributes(start, end)
            ret = ret || chars[charcounter] != ';'
            if (ret) {
                return false
            }
            charcounter++
            verToVer.add(Pair(start, end!!))
            start = getNextInt() ?: return false
            skipSpaces(false)
            sep = chars[charcounter]
        }
        return sep == '}' && sideStreetCount
    }

    /**
     * Parsing Attributes of the edge
     * ['village = Id; name = Id; heightlimit = Id; name = Id; primaryType = <PrimaryType>;
     * secondaryType = <SecondaryType>']
     * @return Parsing Successful or not
     */
    private fun parseAttributes(start: Int, end: Int): Boolean {
        var ret: Boolean = start == end
        skipSpaces(false)
        ret = ret && chars[charcounter] == '['
        charcounter++
        ret = ret && getNextWordEquals("village")
        skipSpaces(false)
        ret = ret && chars[charcounter] == '='
        charcounter++
        skipSpaces(false)
        val village = getNextWord()
        ret = ret && validateId(village) && village != graphName
        if (!mapVilMain.contains(village)) {
            mapVilMain[village] = false
        }
        skipSpaces(false)
        ret = ret && chars[charcounter] != ';'
        charcounter++
        ret = ret && getNextWordEquals("name")
        skipSpaces(false)
        ret = ret && chars[charcounter] == '='
        charcounter++
        skipSpaces(false)
        val name = getNextWord()
        ret = ret && validateId(name) && !vilRoadSet.contains(Pair(village, name))
        vilRoadSet.add(Pair(village, name))
        return ret && parseAttributes2(start, end, village, name)
    }

    private fun parseAttributes2(
        start: Int,
        end: Int,
        village: String,
        name: String,
    ): Boolean {
        var ret = true
        skipSpaces(false)
        ret = ret && chars[charcounter] == ';'
        charcounter++
        ret = ret && getNextWordEquals("heightLimit")
        skipSpaces(false)
        ret = ret && chars[charcounter] == '='
        charcounter++
        val heightLimit = getNextInt() ?: return false
        skipSpaces(false)
        ret = ret && chars[charcounter] == ';'
        charcounter++
        ret = ret && getNextWordEquals("weight")
        skipSpaces(false)
        ret = ret && chars[charcounter] == '='
        charcounter++
        val weight = getNextInt() ?: return false
        skipSpaces(false)
        ret = ret && chars[charcounter] == ';'
        charcounter++

        return ret && parseAttributes3(start, end, village, name, weight, heightLimit)
    }

    private fun parseAttributes3(
        start: Int,
        end: Int,
        village: String,
        name: String,
        weight: Int,
        heightLimit: Int,
    ): Boolean {
        var ret = true
        ret = ret && getNextWordEquals("primaryType")
        skipSpaces(false)
        ret = ret && chars[charcounter] == '='
        charcounter++
        val primary = getNextWord()
        val pty = validatePrimaryType(primary) ?: return false
        if (pty == PrimaryRoadType.SIDESTREET) {
            sideStreetCount = true
        } else if (mapVilVer.contains(start)) {
            ret = ret && mapVilVer[start] == village
        } else {
            mapVilVer[start] = village
        }
        if (pty == PrimaryRoadType.MAINSTREET) {
            if (mapVilMain.contains(village)) {
                mapVilMain.replace(village, false, true)
            }
        }
        if (mapVilVer.contains(end)) {
            ret = ret && mapVilVer[end] == village
        } else {
            mapVilVer[start] = village
        }

        skipSpaces(false)
        if (chars[charcounter] != ';') {
            ret = false
        }
        charcounter++
        return ret && parseAttributes4(pty, village, name, weight, heightLimit, start, end)
    }

    private fun parseAttributes4(
        pty: PrimaryRoadType,
        village: String,
        name: String,
        weight: Int,
        heightLimit: Int,
        start: Int,
        end: Int
    ): Boolean {
        var ret = true
        ret = ret && getNextWordEquals("secondaryType")

        skipSpaces(false)
        if (chars[charcounter] != '=') {
            ret = false
        }
        charcounter++
        val secondary = getNextWord()
        val sty = validateSecondaryType(secondary) ?: return false

        skipSpaces(false)
        if (chars[charcounter] != ';') {
            ret = false
        }
        charcounter++
        skipSpaces(false)
        val startv = gm.getVertex(start)
        val endv = gm.getVertex(end)
        if (startv == null || endv == null) {
            ret = false
        }
        val road = Road(pty, sty, village, name, weight, heightLimit, startv!!, endv!!)
        if (!validateRoad(road)) {
            ret = false
        }
        return ret && gm.addRoad(road, start, end) && chars[charcounter++] == ']'
    }

    private fun validateId(id: String): Boolean {
        val arr = id.toCharArray()
        if (arr[0] < 'A' || arr[0] > 'Z') {
            if (arr[0] < 'a' || arr[0] > 'z') {
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

    private fun validateVertex(v: Vertex): Boolean {
        return v.getId() >= 0 && gm.getVertex(v.getId()) == null
    }

    private fun validateRoad(r: Road): Boolean {
        return if (r.secType == SecondaryRoadType.TUNNEL) {
            r.weight > 0 && r.height <= 3 && r.height >= 1
        } else {
            r.weight > 0 && r.height >= 1
        }
    }

    private fun validateGraphMap(): Boolean {
        return !mapVilMain.containsValue(false)
    }

    private fun getNextWord(): String {
        skipSpaces(false)
        var res = ""
        while (!chars[charcounter].isWhitespace() && !isSeparator(chars[charcounter])
        ) {
            res += chars[charcounter]
            charcounter++
        }
        return res
    }

    private fun isSeparator(c: Char): Boolean {
        return when (c) {
            '{', '}', '[', ']', ';', '-', '>', '=' -> true
            else -> {
                false
            }
        }
    }

    private fun skipSpaces(spaceNec: Boolean): Boolean {
        if (spaceNec) {
            if (!chars[charcounter].isWhitespace()
            ) {
                charcounter++
                return false
            }
        }
        while (chars[charcounter].isWhitespace()
        ) {
            charcounter++
        }
        return true
    }

    private fun validatePrimaryType(ty: String): PrimaryRoadType? {
        return when (ty) {
            "mainStreet" -> PrimaryRoadType.MAINSTREET
            "sideStreet" -> PrimaryRoadType.SIDESTREET
            "countyRoad" -> PrimaryRoadType.COUNTYROAD
            else -> {
                null
            }
        }
    }

    private fun validateSecondaryType(ty: String): SecondaryRoadType? {
        return when (ty) {
            "oneWayStreet" -> SecondaryRoadType.ONEWAYSTREET
            "tunnel" -> SecondaryRoadType.TUNNEL
            "none" -> SecondaryRoadType.NONE
            else -> {
                null
            }
        }
    }

    private fun getNextWordEquals(w: String): Boolean {
        if (getNextWord() != w) {
            return false
        }
        skipSpaces(false)
        return true
    }

    private fun getNextInt(): Int? {
        skipSpaces(false)
        var res = ""
        while (!chars[charcounter].isWhitespace() && (!isSeparator(chars[charcounter]) || chars[charcounter] == '-')) {
            res += chars[charcounter]
            charcounter++
        }
        return res.toIntOrNull()
    }
}
