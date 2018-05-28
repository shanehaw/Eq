package Eq

class Enricher(val scanner: Scanner) {
    private var index = 0
    private var curToken = Token("", TokenType.Empty)
    private val chs : MutableList<Char> = mutableListOf()

    fun enrich(text: String) : String {
        if(text.isNotEmpty() && text.first().isDigit()) {
            val charCollection : MutableCollection<Char> = mutableListOf()
            text.toCollection(charCollection)
            chs.addAll(0, charCollection)

            for(i in 0 until chs.size) {
                val curChar = chs[i]
                if(curChar == '/') {
                    index = i - 1
                    getNextToken(false)
                    subClause(false)
                    chs.add(index, '(')
                    index = i
                    getNextToken(true)
                    subClause(true)
                    chs.add(index, ')')
                    index = 0
                }
            }

            return "(${chs.joinToString("")})"
        }
        return text
    }

    private fun subClause(forwards: Boolean)  {
        if (curToken.type == TokenType.Integer) {
            getNextToken(forwards)
        } else if (curToken.type == TokenType.LeftBracket) {

            getNextToken(forwards)
            parseClause(forwards)
            if (curToken.type == TokenType.RightBracket) {
                getNextToken(forwards)
            } else {
                throw IllegalArgumentException()
            }
        }
        throw IllegalArgumentException()
    }

    private fun getNextToken(forwards: Boolean) {
        val text = chs.joinToString("")
        val nextContext : ScanContext =
            if(forwards) {
                scanner.getNextToken(text, index)
            } else {
                scanner.getPrevToken(text, index)
            }
        curToken = nextContext.curToken
        index = nextContext.index

    }

    private fun parseClause(forwards: Boolean) {
        subClause(forwards)
        while (curToken.type == TokenType.Add
                || curToken.type == TokenType.Subtraction
                || curToken.type == TokenType.Multiply
                || curToken.type == TokenType.Division) {
            getNextToken(forwards)
            parseClause(forwards)
        }
    }
}