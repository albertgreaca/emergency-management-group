package de.unisaarland.cs.se.selab

class Lexer {


    // function instream needed for character List?
    fun lex(s: String): MutableList<LexerToken> {

        var string = s.replace("\\s+}".toRegex(), " ").trim()
        string = string.replace("\n"," ")
        string = string.replace(";", " ; ")
        string = string.replace("[", " [ ")
        string = string.replace("]", " ] ")
        string = string.replace("{", " { ")
        string = string.replace("}", " } ")
        string = string.replace("=", " = ")
        string = string.replace("-", " -")
        string = string.replace(">", "> ")
        var list = string.split("\\s+".toRegex()).toMutableList()
        var tokenlist = mutableListOf<LexerToken>()
        for (element in list) {
            when(element) {
                "->" -> tokenlist.add(LexerToken.ARROW)
                ";" -> tokenlist.add(LexerToken.SEMICOLON)
                ")" -> tokenlist.add(LexerToken.RPARENTHESES)
                "(" -> tokenlist.add(LexerToken.LPARENTHESES)
                "{" -> tokenlist.add(LexerToken.CLPARENTHESES)
                "}" -> tokenlist.add(LexerToken.CRPARENTHESES)
                "=" -> tokenlist.add(LexerToken.EQUAL)
                "village" -> tokenlist.add(LexerToken.VILLAGE)
                "name" -> tokenlist.add(LexerToken.NAME)
                "heightLimit" -> tokenlist.add(LexerToken.HEIGHTLIMIT)
                "weight" -> tokenlist.add(LexerToken.WEIGHT)
                "primaryType" -> tokenlist.add(LexerToken.PRIMARYTYPE)
                "secondaryType" -> tokenlist.add(LexerToken.SECONDARYTYPE)
                "mainStreet" -> tokenlist.add(LexerToken.MAINSTREET)
                "sideStreet" -> tokenlist.add(LexerToken.SIDESTREET)
                "countyRoad" -> tokenlist.add(LexerToken.COUNTYROAD)
                "oneWayStreet" -> tokenlist.add(LexerToken.ONEWAYSTREET)
                "tunnel" -> tokenlist.add(LexerToken.TUNNEL)
                "none" -> tokenlist.add(LexerToken.NONE)
            }
        }

        return tokenlist
    }

    fun lexString() {

    }

    fun lexNum(xc: Char) {
        var acc = 0

    }

    fun isChar(x:Char): Boolean {
        when (x) {
            in 'a' .. 'z' -> return true
            in 'A' .. 'Z' -> return true
            else -> return false
        }
    }

    fun isDigit(x: Char): Boolean {
        when(x) {
            in '0' .. '9' -> return true
            else -> return false
        }
    }
}