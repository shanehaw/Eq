package Eq.Clauses

class SubtractionClause(var lhs: Clause) : Clause() {


    override fun print(): String {
        return lhs.print() + " - " + rhs.print()
    }

    var rhs: Clause = EmptyClause()

    override fun setRHS(clause: Clause) {
        rhs = clause
    }

    override fun hasRHS(): Boolean = true

    override fun prepare(op: Operation) {
        lhs.prepare(op)
        rhs.prepare(op)
        if(op == Operation.Sub) {
            lhs.prepareBODMAS()
            rhs.prepareBODMAS()
            value = lhs.eval() - rhs.eval()
        }
    }
}