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

            var i = 0
            while(i < chs.size) {
                val curChar = chs[i]
                if(curChar == '/') {
                    index = i - 1
                    getNextToken(false)
                    subClause(false)
                    chs.add(index + 1, '(')
                    index = i + 2
                    getNextToken(true)
                    subClause(true)
                    chs.add(index, ')')
                    index = 0
                    i++
                }
                i++
            }

            return "(${chs.joinToString("")})"
        }
        return text
    }

    private fun subClause(forwards: Boolean)  {
        if (curToken.type == TokenType.Integer) {
            getNextToken(forwards)
        } else if ((forwards && curToken.type == TokenType.LeftBracket) || (!forwards && curToken.type == TokenType.RightBracket)) {

            getNextToken(forwards)
            parseClause(forwards)
            if ((forwards && curToken.type == TokenType.RightBracket) || (!forwards && curToken.type == TokenType.LeftBracket)) {
                getNextToken(forwards)
            } else {
                throw IllegalArgumentException()
            }
        } else {        
            throw IllegalArgumentException()
        }
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
