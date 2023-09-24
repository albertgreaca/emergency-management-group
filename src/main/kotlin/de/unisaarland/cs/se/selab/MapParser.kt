package de.unisaarland.cs.se.selab

import PrimaryRoadType
import SecondaryRoadType
import java.io.File

/** Class for Parsing dot Files
 * digraph Id {
 * Id;
 * Id -> Id [attributes'];
 * }
 */
class MapParser(private val gm:GraphMap, private val file:File) {
    private var charcounter = 0
    private val chars = file.readText().toCharArray()

    /**
     * Starting function for parsing
     * @return Parsing Successful or not
     */
    public fun parseMap(): Boolean {
        if(getNextWord(true) != "digraph")  {
            return false
        }
        skipSpaces(true)
        val name = getNextWord(true)
        if(!validateId(name)) {
            return false
        }
        skipSpaces(false)
        if(chars[charcounter] != '{') {
            return false
        }
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
        var split = getNextSeparator()
        var sep = split.second
        while(sep == ';') {
            val id = split.first.toIntOrNull() ?: return false
            val vertex = Vertex(id,null)
            if(!validateVertex(vertex)) {
                return false
            }
            gm.addVertex(vertex)
            charcounter++
            skipSpaces(false)
            split = getNextSeparator()
            sep = split.second
        }
        return sep == '-'
    }
    /**
     * Parsing every Edge of the Form
     * Id -> Id['attributes'];
     * @return Parsing Successful or not
     */
    private fun parseEdges(): Boolean {
        var split = getNextSeparator()
        var sep = split.second
        while(sep == '-') {
            val start = split.first.toIntOrNull() ?: return false
            skipSpaces(false)
            if(chars[0] != '-' || chars[1] != '>') {
                return false
            }
            charcounter+= 2;
            val end = getNextWord(true).toIntOrNull() ?: return false
            if(!parseAttributes(start,end) || chars[0] != ';') {
                return false;
            }
            split = getNextSeparator()
            sep = split.second
        }
        return sep == '}'
    }
    /**
     * Parsing Attributes of the edge
     * ['village = Id; name = Id; heightlimit = Id; name = Id; primaryType = <PrimaryType>;
     * secondaryType = <SecondaryType>']
     * @return Parsing Successful or not
     */
    private fun parseAttributes(start: Int, end: Int): Boolean {
        if(start==end) {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '[') {
            return false
        }
        charcounter++
        if(getNextWord(true) != "village") {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false
        }
        charcounter++
        skipSpaces(false)
        val village = getNextWord(true)
        if(!validateId(village)) {
            return false
        }
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        charcounter++
        if(getNextWord(true) != "name") {
            return false;
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false
        }
        charcounter++
        skipSpaces(false)
        val name = getNextWord(true)
        if(!validateId(name)) {
            return false
        }
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        charcounter++
        if(getNextWord(true) != "heightLimit") {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false;
        }
        val heightLimit = getNextWord(true).toIntOrNull() ?: return false
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        charcounter++;
        if(getNextWord(true) != "weight") {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false;
        }
        val weight = getNextWord(true).toIntOrNull() ?: return false
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        charcounter++;
        if(getNextWord(true) != "primaryType") {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false
        }
        charcounter++
        val primary = getNextWord(true)
        val pty = validatePrimaryType(primary) ?: return false
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        charcounter++
        if(getNextWord(true) != "secondaryType") {
            return false
        }
        skipSpaces(false)
        if(chars[0] != '=') {
            return false
        }
        charcounter++
        val secondary = getNextWord(true)
        val sty = validateSecondaryType(secondary) ?: return false

        skipSpaces(false)
        if(chars[0] != ';')  {
            return false
        }
        charcounter++
        skipSpaces(false)
        val startv = gm.getVertex(start) ?: return false
        val endv = gm.getVertex(end) ?: return false
        val road = Road(pty,sty,village,name, weight, heightLimit,startv, endv)
        if(!validateRoad(road)) {
            return false
        }
        return gm.addRoad(road,start,end) && chars[0] == ']'
    }

    private fun validateId(id: String): Boolean {
        val arr = id.toCharArray()
        if((arr[0] < 'A' || arr[0] > 'Z') && (arr[0] < 'a' || arr[0] > 'z')) {
            return false
        }

        for(ch in arr) {
            if((ch < 'A' || ch > 'Z') && (ch < 'a' || ch > 'z') && ch != '_') {
                return false;
            }
        }
        return true;
    }
    private fun validateVertex(v: Vertex): Boolean {
        return v.getId() >= 0 && gm.getVertex(v.getId()) == null
    }
    private fun validateRoad(r: Road): Boolean {
        return if(r.getSecType() == SecondaryRoadType.TUNNEL) {
            r.getWeight() > 0 && r.getHeight() <= 3 && r.getHeight() >= 1
        } else {
            r.getWeight() > 0 && r.getHeight() >= 1
        }
    }

    private fun validateGraphMap(): Boolean {
        return true
    }

    private fun getNextWord(count: Boolean): String {
        skipSpaces(false)
        var res = ""
        while(chars[charcounter] != ' ' || chars[charcounter] != '{'  || chars[charcounter] != '}'
            || chars[charcounter] != '[' || chars[charcounter] != ']' || chars[charcounter] != ',' ||
            chars[charcounter] != '-' || chars[charcounter] != '>' || chars[charcounter] != '='
            || chars[charcounter] != '\n' || chars[charcounter] != '\t' || chars[charcounter] != '\u000c'
            || chars[charcounter] != '\r' || chars[charcounter] != '\u00A0' || chars[charcounter] != '\u240b') {

            res += chars[charcounter]
            if(count) {
                charcounter++;
            }
        }
        return res
    }

    private fun getNextSeparator(): Pair<String,Char> {
        skipSpaces(false)
        val next = getNextWord(true)
        skipSpaces(false)
        return Pair(next,chars[0])
    }

    private fun skipSpaces(spaceNec: Boolean): Boolean {
        if(spaceNec) {
            if(chars[charcounter] != ' ' ||chars[charcounter] != '\n' || chars[charcounter] != '\t' || chars[charcounter] != '\u000c'
                || chars[charcounter] != '\r' || chars[charcounter] != '\u00A0' || chars[charcounter] != '\u240b') {
                charcounter++;
                return false
            }
        }
        while(chars[charcounter] != ' ' ||chars[charcounter] != '\n' || chars[charcounter] != '\t' || chars[charcounter] != '\u000c'
            || chars[charcounter] != '\r' || chars[charcounter] != '\u00A0' || chars[charcounter] != '\u240b') {
            charcounter++
        }
        return true
    }
    private fun validatePrimaryType(ty: String): PrimaryRoadType? {
        return when(ty) {
            "mainStreet" -> PrimaryRoadType.MAINSTREET
            "sideStreet" -> PrimaryRoadType.SIDESTREET
            "countyRoad" -> PrimaryRoadType.COUNTYROAD
            else -> {
                null
            }
        }
    }
    private fun validateSecondaryType(ty: String): SecondaryRoadType? {
        return when(ty) {
            "oneWayStreet" -> SecondaryRoadType.ONEWAYSTREET
            "tunnel" -> SecondaryRoadType.TUNNEL
            "none" -> SecondaryRoadType.NONE
            else -> {
                null
            }
        }
    }

}