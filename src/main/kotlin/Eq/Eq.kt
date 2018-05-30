package Eq


import Eq.Clauses.*

class Eq(val parser: Parser, val enricher: Enricher) {
    fun eval(text: String?): Int {
        if (text == null || text.isEmpty()) {
            throw IllegalArgumentException()
        }

        val enrichedText = enricher.enrich(text)
        val statement = parser.parse(enrichedText)
        if (statement is EmptyClause)
            throw IllegalArgumentException()
        else {
            statement.prepareBODMAS()
            return statement.eval()
        }
    }

    fun evalAndPrintBack(text: String?): String {
        if (text == null || text.isEmpty()) {
            throw IllegalArgumentException()
        }

        val statement = parser.parse(text)
        if (statement is EmptyClause)
            throw IllegalArgumentException()
        else {
            return statement.print()
        }
    }
}