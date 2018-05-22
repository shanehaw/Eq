package Eq.Clauses

open class BracketClause(open val inner : Clause) : Clause() {
    override fun print(): String {
        return "(" + inner.print() + ")"
    }

    override fun setRHS(clause: Clause) { }

    override fun hasRHS(): Boolean = false

    override fun prepare(op: Operation) {
        if(op == Operation.Bracket) {
            inner.prepare(Operation.Bracket)
            inner.prepare(Operation.Division)
            inner.prepare(Operation.Multiply)
            inner.prepare(Operation.Add)
            inner.prepare(Operation.Sub)
            value = inner.eval()
        }
    }
}