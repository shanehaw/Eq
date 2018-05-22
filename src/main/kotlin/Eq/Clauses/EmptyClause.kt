package Eq.Clauses

class EmptyClause() : Clause() {
    override fun print(): String {
        return "!empty!"
    }

    override fun setRHS(clause: Clause) {
    }

    override fun hasRHS(): Boolean = false

    override fun prepare(op: Operation) {
    }

}