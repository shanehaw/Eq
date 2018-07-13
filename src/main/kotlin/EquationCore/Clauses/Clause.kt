package EquationCore.Clauses

abstract class Clause() {

    var value : Int? = null

    abstract fun prepare(op: Operation)

    open fun hasRHS() : Boolean = false

    open fun setRHS(clause: Clause) { }

    abstract fun print() : String

    fun eval() : Int {
        return value!!
    }

    fun prepareBODMAS() {
        prepare(Operation.Bracket)
        prepare(Operation.Division)
        prepare(Operation.Multiply)
        prepare(Operation.Add)
        prepare(Operation.Sub)
    }
}