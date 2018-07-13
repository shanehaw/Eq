package EquationCore.Clauses

class MultiplyClause(var lhs: Clause): Clause() {


    var rhs: Clause = EmptyClause()

    override fun hasRHS(): Boolean  = true

    override fun setRHS(clause: Clause) {
        rhs = clause
    }

    override fun prepare(op: Operation) {
        lhs.prepare(op)
        rhs.prepare(op)
        if(op == Operation.Multiply) {
            lhs.prepareBODMAS()
            rhs.prepareBODMAS()
            value = lhs.eval() * rhs.eval()
        }
    }

    override fun print(): String {
        return lhs.print() + " * " + rhs.print()
    }

}