package EquationCore.Clauses

class AdditionClause(var lhs : Clause) : Clause() {

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
            lhs.prepareBODMAS()
            rhs.prepareBODMAS()
            value = lhs.eval() + rhs.eval()
        }
    }
}