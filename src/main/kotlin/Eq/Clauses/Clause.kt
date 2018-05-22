package Eq.Clauses

abstract class Clause() {

    var value : Int? = null

    abstract fun prepare(op: Operation)

    abstract fun hasRHS() : Boolean

    abstract fun setRHS(clause: Clause);

    abstract fun print() : String;

    fun eval() : Int {
        return value!!
    }
}