package Eq

import Eq.Clauses.*

class Parser(val scanner: Scanner) {

    private var curToken: Token = Token("", TokenType.Empty)
    private var text: String = ""
    private var index: Int = 0

    fun parse(text: String): Clause {
        return parseStatement(text)
    }

    private fun parseStatement(text: String): Clause {
        this.text = text
        this.index = 0

        getNextToken()

        var parsedClause = parseClause(EmptyClause())
        if (index < text.length - 1) {
            throw IllegalArgumentException()
        }

        return parsedClause
    }

    private fun parseClause(curClause: Clause): Clause {
        var lhs = subClause(curClause)

        while (curToken.type == TokenType.Add
                || curToken.type == TokenType.Subtraction
                || curToken.type == TokenType.Multiply
                || curToken.type == TokenType.Division) {
            val newClause = curToken.createCorrespondingClause(lhs)
            getNextToken()
            lhs = parseClause(newClause)
        }
        return lhs
    }

    private fun subClause(curClause: Clause): Clause {
        if (curToken.type == TokenType.Integer) {
            var clause: Clause = IntLiteralClause(curToken.token.toInt())

            if (curClause.hasRHS()) {
                curClause.setRHS(clause)
                clause = LogicalHiddenBracketClause(curClause)
            }

            getNextToken()
            return clause
        } else if (curToken.type == TokenType.LeftBracket) {
            var clause: Clause

            getNextToken()
            val middleClause = parseClause(EmptyClause())
            if (curToken.type == TokenType.RightBracket) {
                getNextToken()
            } else {
                throw IllegalArgumentException()
            }
            clause = BracketClause(middleClause)

            if (curClause.hasRHS()) {
                curClause.setRHS(clause)
                clause = curClause
            }
            return clause
        }
        throw IllegalArgumentException()
    }

    private fun getNextToken() {
        val nextContext = scanner.getNextToken(text, index)
        curToken = nextContext.curToken
        index = nextContext.index

    }
}