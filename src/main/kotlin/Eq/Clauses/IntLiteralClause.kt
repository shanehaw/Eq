package Eq.Clauses

class IntLiteralClause(val num: Int) : Clause() {
    override fun print(): String {
        return num.toString()
    }

    override fun setRHS(clause: Clause) {
    }

    override fun hasRHS(): Boolean = false

    override fun prepare(op: Operation) {
        value = num
    }
}