package Eq.Clauses

class AdditionClause(val lhs : Clause) : Clause() {
    override fun print(): String {
        return lhs.print() + " + " + rhs.print()
    }

    var rhs : Clause = EmptyClause()

    override fun hasRHS(): Boolean = true

    override fun setRHS(clause: Clause) {
        rhs = clause
    }

    override fun prepare(op: Operation) {
        lhs.prepare(op)
        rhs.prepare(op)
        if(op == Operation.Add) {
            value = lhs.eval() + rhs.eval()
        }
    }
}