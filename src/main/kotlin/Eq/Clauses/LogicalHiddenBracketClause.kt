package Eq.Clauses

import Eq.Clauses.BracketClause

class LogicalHiddenBracketClause(override val inner : Clause) : BracketClause(inner) {

    override fun print(): String {
        return inner.print()
    }

}