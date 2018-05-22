package Eq.Clauses

class DivisionClause(val lhs : Clause): Clause() {
    var rhs : Clause = EmptyClause()

    override fun hasRHS(): Boolean = true

    override fun setRHS(clause: Clause) {
        rhs = clause
    }

    override fun prepare(op: Operation) {
        lhs.prepare(op)
        rhs.prepare(op)
        if(op == Operation.Division) {
            value = lhs.eval() / rhs.eval()
        }
    }

    override fun print(): String {
        return lhs.print() + " / " + rhs.print()
    }
}