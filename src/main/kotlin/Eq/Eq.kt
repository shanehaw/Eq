package Eq


import Eq.Clauses.*

class Eq(val parser: Parser) {
    fun eval(text: String?): Int {
        val statement = parser.parse(text)
        if (statement is EmptyClause)
            throw IllegalArgumentException()
        else {
            prepareBODMAS(statement)
            return statement.eval()
        }
    }

    private fun prepareBODMAS(statement: Clause) {
        statement.prepare(Operation.Bracket)
        statement.prepare(Operation.Division)
        statement.prepare(Operation.Multiply)
        statement.prepare(Operation.Add)
        statement.prepare(Operation.Sub)
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