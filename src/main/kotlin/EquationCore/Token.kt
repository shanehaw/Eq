package EquationCore

import EquationCore.Clauses.Clause
import EquationCore.Clauses.AdditionClause
import EquationCore.Clauses.SubtractionClause
import EquationCore.Clauses.EmptyClause
import EquationCore.Clauses.MultiplyClause
import EquationCore.Clauses.DivisionClause

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