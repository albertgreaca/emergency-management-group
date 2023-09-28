package de.unisaarland.cs.se.selab

/**
 * the lexer
 */
class Lexer {

    /**
     * lexes
     */
    // function instream needed for character List?
    fun lex(s: String): MutableList<LexerToken> {
        var string = s.replace("\\s+}".toRegex(), Space).trim()
        // string = string.replace("\n", " ")
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
            whencyclo(element, tokenlist)
        }
        return tokenlist
    }

    private fun whencyclo(element: String, tokenList: MutableList<LexerToken>) {
        when (element) {
            "->" -> tokenList.add(LexerToken.ARROW)
            ";" -> tokenList.add(LexerToken.SEMICOLON)
            ")" -> tokenList.add(LexerToken.RPARENTHESES)
            "(" -> tokenList.add(LexerToken.LPARENTHESES)
            "{" -> tokenList.add(LexerToken.CLPARENTHESES)
            "}" -> tokenList.add(LexerToken.CRPARENTHESES)
            "=" -> tokenList.add(LexerToken.EQUAL)
            else -> {
                whencyclo2(element, tokenList)
            }
        }
    }

    private fun whencyclo2(element: String, tokenList: MutableList<LexerToken>) {
        when (element) {
            "village" -> tokenList.add(LexerToken.VILLAGE)
            "name" -> tokenList.add(LexerToken.NAME)
            "heightLimit" -> tokenList.add(LexerToken.HEIGHTLIMIT)
            "weight" -> tokenList.add(LexerToken.WEIGHT)
            "primaryType" -> tokenList.add(LexerToken.PRIMARYTYPE)
            "secondaryType" -> tokenList.add(LexerToken.SECONDARYTYPE)
            "mainStreet" -> tokenList.add(LexerToken.MAINSTREET)
            "sideStreet" -> tokenList.add(LexerToken.SIDESTREET)
            "countyRoad" -> tokenList.add(LexerToken.COUNTYROAD)
            "oneWayStreet" -> tokenList.add(LexerToken.ONEWAYSTREET)
            "tunnel" -> tokenList.add(LexerToken.TUNNEL)
            "none" -> tokenList.add(LexerToken.NONE)
        }
    }

    /**
     * lext string
     */
    fun lexString(s: String): String {
        if (s == Space) {
            return ""
        } else {
            return Space
        }
    }

    /**
     * lex Num
     */
    fun lexNum(xc: Char) {
        var acc = 0
        xc.minus(acc)
    }

    /**
     * checks if char
     */
    fun isChar(x: Char): Boolean {
        when (x) {
            in 'a'..'z' -> return true
            in 'A'..'Z' -> return true
            else -> return false
        }
    }

    /**
     * check if digit
     */
    fun isDigit(x: Char): Boolean {
        when (x) {
            in '0'..'9' -> return true
            else -> return false
        }
    }
    companion object {
        const val Space = " "
    }
}