package EquationCore.Clauses

class DivisionClause(var lhs : Clause): Clause() {


    var rhs : Clause = EmptyClause()

    override fun hasRHS(): Boolean = true

    override fun setRHS(clause: Clause) {
        rhs = clause
    }

    override fun prepare(op: Operation) {
        lhs.prepare(op)
        rhs.prepare(op)
        if(op == Operation.Division) {
            lhs.prepareBODMAS()
            rhs.prepareBODMAS()
            val lhsValue = lhs.eval()
            val rhsValue = rhs.eval()

            if(lhsValue % rhsValue > 0) {
                throw IllegalArgumentException("Division would result in truncation.")
            }

            value = lhsValue / rhsValue
        }
    }

    override fun print(): String {
        return lhs.print() + " / " + rhs.print()
    }
}