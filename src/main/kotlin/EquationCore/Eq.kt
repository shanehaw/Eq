package EquationCore


import EquationCore.Clauses.*

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
}