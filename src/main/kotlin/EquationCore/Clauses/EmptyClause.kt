package EquationCore.Clauses

class EmptyClause() : Clause() {
    override fun print(): String {
        return "!empty!"
    }

    override fun prepare(op: Operation) {
    }

}