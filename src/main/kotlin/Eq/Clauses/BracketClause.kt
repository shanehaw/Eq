package Eq.Clauses

open class BracketClause(open val inner : Clause) : Clause() {


    override fun print(): String {
        return "(" + inner.print() + ")"
    }

    override fun prepare(op: Operation) {
        if(op == Operation.Bracket) {
            inner.prepareBODMAS()
            value = inner.eval()
        }
    }
}