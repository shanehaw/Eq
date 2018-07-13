package EquationCore.Clauses

class IntLiteralClause(val num: Int) : Clause() {
    override fun print(): String {
        return num.toString()
    }

    override fun prepare(op: Operation) {
        value = num
    }
}