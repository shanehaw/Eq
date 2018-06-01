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
                    insertLogicalBracketsAroundOperatorAtIndex(i)
                    i++
                }
                i++
            }

            i = 0
            while(i < chs.size) {
                val curChar = chs[i]
                if(curChar == '+' || curChar == '-') {
                    insertLogicalBracketsAroundOperatorAtIndex(i)
                    i++
                }
                i++
            }

            return chs.joinToString("")
        }
        return text
    }

    private fun insertLogicalBracketsAroundOperatorAtIndex(i: Int) {
        //start parsing from the character left of the current operator
        index = i - 1
        //find correct index to place left bracket
        getNextToken(ParseDirection.Backwards)
        subClause(ParseDirection.Backwards)
        getNextToken(ParseDirection.Forwards)
        getNextToken(ParseDirection.Forwards)
        //consider boundaries of list
        chs.add(maxOf(index, 0), '(')
        //account for new left bracket and move one character to right of current operator
        index = i + 2
        //find correct index to place right bracket
        getNextToken(ParseDirection.Forwards)
        subClause(ParseDirection.Forwards)
        getNextToken(ParseDirection.Backwards)
        getNextToken(ParseDirection.Backwards)
        //consider boundaries of list
        chs.add(minOf(index + 1, chs.size), ')')
    }

    private fun subClause(direction: ParseDirection)  {
        if (curToken.type == TokenType.Integer) {
            getNextToken(direction)
        } else if ((direction == ParseDirection.Forwards && curToken.type == TokenType.LeftBracket)
                || (direction == ParseDirection.Backwards && curToken.type == TokenType.RightBracket)) {
            getNextToken(direction)
            parseClause(direction)
            if ((direction == ParseDirection.Forwards && curToken.type == TokenType.RightBracket)
                    || (direction == ParseDirection.Backwards && curToken.type == TokenType.LeftBracket)) {
                getNextToken(direction)
            } else {
                throw IllegalArgumentException()
            }
        }
        else {
            throw IllegalArgumentException()
        }
    }

    private fun getNextToken(direction: ParseDirection) {
        val text = chs.joinToString("")
        val nextContext : ScanContext =
            if(direction == ParseDirection.Forwards) {
                scanner.getNextToken(text, index)
            } else {
                scanner.getPrevToken(text, index)
            }
        curToken = nextContext.curToken
        index = nextContext.index

    }

    private fun parseClause(direction: ParseDirection) {
        subClause(direction)
        while (curToken.type == TokenType.Add
                || curToken.type == TokenType.Subtraction
                || curToken.type == TokenType.Multiply
                || curToken.type == TokenType.Division) {
            getNextToken(direction)
            parseClause(direction)
        }
    }
}
