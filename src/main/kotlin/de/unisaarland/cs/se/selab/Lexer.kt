package de.unisaarland.cs.se.selab

class Lexer {


    // function instream needed for character List?
    fun lex(s: String) {
        s.replace("\\s+".toRegex(), " ").trim()
        s.split("\\s+".toRegex()).toMutableList()
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