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
        skipSpaces(false)
        if(chars[charcounter] != '{') {
            return false
        }
        val res = parseSgtStmtList()
        validateGraphMap()
        return res
    }
    private fun parseSgtStmtList(): Boolean {
        skipSpaces(false);
        val res1 = parseVertices()
        val res2 = parseEdges()
        return res1 && res2
    }
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
    private fun parseEdges(): Boolean {
        var sep = getNextSeperator()
        while(sep == '-') {

        }
    }
    private fun parseAttributes(): Boolean {

        return true
    }

    private fun validateVertex(Vertex v): Boolean {


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
            chars[charcounter] != '-' || chars[charcounter] != '>' ) {

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
            if(chars[charcounter] != ' ') {
                charcounter++;
                return false
            }
        }
        while(chars[charcounter] == ' ') {
            charcounter++
        }
        return true
    }

}