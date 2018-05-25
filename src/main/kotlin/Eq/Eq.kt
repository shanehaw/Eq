package Eq


import Eq.Clauses.*

class Eq(val parser: Parser) {
    fun eval(text: String?): Int {
        val statement = parser.parse(text)
        if (statement is EmptyClause)
            throw IllegalArgumentException()
        else {
            statement.prepareBODMAS()
            return statement.eval()
        }
    }

    fun evalAndPrintBack(text: String?): String {
        val statement = parser.parse(text)
        if (statement is EmptyClause)
            throw IllegalArgumentException()
        else {
            return statement.print()
        }
    }
}