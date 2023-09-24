package de.unisaarland.cs.se.selab

import java.io.File

class MapParser( private val gm:GraphMap, private val file:File) {
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
        validateId(name)
        skipSpaces(false)
        if(chars[charcounter] != '{') {
            return false
        }
        val res = parseSgtStmtList()
        validateGraphMap()
        return res
    }
    /**
     * Parsing everything in between { }
     * @return Parsing Successful or not
     */
    private fun parseSgtStmtList(): Boolean {
        skipSpaces(false);
        val res1 = parseVertices()
        val res2 = parseEdges()
        return res1 && res2
    }
    /**
     * Parsing every Vertex of the Form
     * Id;
     * @return Parsing Successful or not
     */
    private fun parseVertices(): Boolean {
        var sep = getNextSeperator()
        while(sep == ';') {
            val id = getNextWord(true).toIntOrNull() ?: return false
            val vertex = Vertex(id)
            gm.add(vertex)
            if(!validateVertex(vertex)) {
                return false
            }
            charcounter++
            skipSpaces(false)
            sep = getNextSeperator();
        }
        return sep == '-'
    }
    /**
     * Parsing every Edge of the Form
     * Id -> Id['attributes'];
     * @return Parsing Successful or not
     */
    private fun parseEdges(): Boolean {
        var sep = getNextSeperator()
        while(sep == '-') {
            val start = getNextWord(true)
            skipSpaces(false)
            if(chars[0] != '-' || chars[1] != '>') {
                return false
            }
            charcounter+= 2;
            val end = getNextWord(true)
            val road = Road()
            gm.addRoad(road, start, end)
            parseAttributes(road)
            if(chars[0] != ';') {
                return false;
            }
            sep = getNextSeperator()
        }
        return true
    }
    /**
     * Parsing Attributes of the edge
     * ['village = Id; name = Id; heightlimit = Id; name = Id; primaryType = <PrimaryType>;
     * secondaryType = <SecondaryType>']
     * @return Parsing Successful or not
     */
    private fun parseAttributes(road: Road): Boolean {
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
        skipSpaces(false)
        val village = getNextWord(true)
        validateId(village)
        skipSpaces(false)
        if(chars[0] != ';') {
            return false
        }
        if(getNextWord(true) != "name") {
            return false;
        }
        skipSpaces(false)

        return true
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


    }
    private fun validateRoad(): Boolean {

    }

    private fun validateGraphMap(): Boolean {

    }
    private fun getNum(): Int {

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

    private fun getNextSeperator(): Char {
        skipSpaces(false)
        val next = getNextWord(false)
        skipSpaces(false)
        return chars[0]
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

}