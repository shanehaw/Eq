package Eq

class Enricher(private val scanner: Scanner) {
    private var index = 0
    private var curToken = Token("", TokenType.Empty)
    private val chs : MutableList<Char> = mutableListOf()

    fun enrich(text: String) : String {
        if(text.isNotEmpty() && (text.first().isDigit() || '(' == text.first())) {
            chs.clear()
            val charCollection : MutableCollection<Char> = mutableListOf()
            text.toCollection(charCollection)
            chs.addAll(0, charCollection)

            var i = 0
            while(i < chs.size) {
                val curChar = chs[i]
                if(curChar == '/' || curChar == '*') {
                    index = i - 1
                    getNextToken(false)
                    subClause(false)
                    getNextToken(true)
                    getNextToken(true)
                    chs.add(maxOf(index, 0), '(')
                    index = i + 2
                    getNextToken(true)
                    subClause(true)
                    getNextToken(false)
                    getNextToken(false)
                    chs.add(minOf(index+1, chs.size), ')')
                    index = 0
                    i++
                }
                i++
            }

            i = 0
            while(i < chs.size) {
                val curChar = chs[i]
                if(curChar == '+' || curChar == '-') {
                    index = i - 1
                    getNextToken(false)
                    subClause(false)
                    getNextToken(true)
                    getNextToken(true)
                    chs.add(maxOf(index, 0), '(')
                    index = i + 2
                    getNextToken(true)
                    subClause(true)
                    getNextToken(false)
                    getNextToken(false)
                    chs.add(minOf(index+1, chs.size), ')')
                    index = 0
                    i++
                }
                i++
            }

            return chs.joinToString("")
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
        } else if ((forwards && curToken.type == TokenType.LeftSquareBracket) || (!forwards && curToken.type == TokenType.RightSquareBracket)) {
            getNextToken(forwards)
            parseClause(forwards)
            if ((forwards && curToken.type == TokenType.RightSquareBracket) || (!forwards && curToken.type == TokenType.LeftSquareBracket)) {
                getNextToken(forwards)
            } else {
                throw IllegalArgumentException()
            }
        }
        else {
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
