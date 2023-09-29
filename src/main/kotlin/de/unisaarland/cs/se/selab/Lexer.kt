package de.unisaarland.cs.se.selab

/**
 * the lexer
 */
class Lexer {

    /**
     * lexes
     */
    // function instream needed for character List?
    fun lex(s: String): MutableList<LexerIDToken> {
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
        val list = string.split("\\s+".toRegex()).toMutableList()
        val tokenlist = mutableListOf<LexerIDToken>()
        for (element in list) {
            whencyclo(element, tokenlist)
        }
        return tokenlist
    }

    private fun whencyclo(element: String, tokenList: MutableList<LexerIDToken>) {
        when (element) {
            "->" -> tokenList.add(LexerIDToken(LexerToken.ARROW, Space))
            ";" -> tokenList.add(LexerIDToken(LexerToken.SEMICOLON, Space))
            ")" -> tokenList.add(LexerIDToken(LexerToken.RPARENTHESES, Space))
            "(" -> tokenList.add(LexerIDToken(LexerToken.LPARENTHESES, Space))
            "{" -> tokenList.add(LexerIDToken(LexerToken.CLPARENTHESES, Space))
            "}" -> tokenList.add(LexerIDToken(LexerToken.CRPARENTHESES, Space))
            "=" -> tokenList.add(LexerIDToken(LexerToken.EQUAL, Space))
            else -> {
                whencyclo2(element, tokenList)
            }
        }
    }

    private fun whencyclo2(element: String, tokenList: MutableList<LexerIDToken>) {
        when (element) {
            "village" -> tokenList.add(LexerIDToken(LexerToken.VILLAGE, Space))
            "name" -> tokenList.add(LexerIDToken(LexerToken.NAME, Space))
            "heightLimit" -> tokenList.add(LexerIDToken(LexerToken.HEIGHTLIMIT, Space))
            "weight" -> tokenList.add(LexerIDToken(LexerToken.WEIGHT, Space))
            "primaryType" -> tokenList.add(LexerIDToken(LexerToken.PRIMARYTYPE, Space))
            "secondaryType" -> tokenList.add(LexerIDToken(LexerToken.SECONDARYTYPE, Space))
            "mainStreet" -> tokenList.add(LexerIDToken(LexerToken.MAINSTREET, Space))
            "sideStreet" -> tokenList.add(LexerIDToken(LexerToken.SIDESTREET, Space))
            "countyRoad" -> tokenList.add(LexerIDToken(LexerToken.COUNTYROAD, Space))
            "oneWayStreet" -> tokenList.add(LexerIDToken(LexerToken.ONEWAYSTREET, Space))
            "tunnel" -> tokenList.add(LexerIDToken(LexerToken.TUNNEL, Space))
            "none" -> tokenList.add(LexerIDToken(LexerToken.NONE, Space))
            else -> tokenList.add(this.lexString(element))
        }
    }

    /**
     * lext string
     */
    fun lexString(s: String): LexerIDToken {
        return LexerIDToken(LexerToken.ID, s)
    }

    /**
     *

     /**
     * lex Num
     */
     fun lexNum(xc: Char) {
     val acc = 0
     xc.minus(acc)
     }
     */

    /**
     * checks if char

     fun isChar(x: Char): Boolean {
     when (x) {
     in 'a'..'z' -> return true
     in 'A'.'Z' -> return true
     else -> return false
     }
     }
     */

    /**
     * check if digit

     fun isDigit(x: Char): Boolean {
     when (x) {
     in '0'..'9' -> return true
     else -> return false
     }
     }
     */
    companion object {
        const val Space = " "
    }
}
