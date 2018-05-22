package Eq

import Eq.Clauses.Clause
import Eq.Clauses.AdditionClause
import Eq.Clauses.SubtractionClause
import Eq.Clauses.EmptyClause
import Eq.Clauses.MultiplyClause
import Eq.Clauses.DivisionClause

data class Token(val token : String, val type: TokenType) {

    fun createCorrespondingClause(lhs: Clause) : Clause {
        return when (type) {
            TokenType.Add -> AdditionClause(lhs)
            TokenType.Subtraction -> SubtractionClause(lhs)
            TokenType.Multiply -> MultiplyClause(lhs)
            TokenType.Division -> DivisionClause(lhs)
            else -> EmptyClause()
        }
    }

}