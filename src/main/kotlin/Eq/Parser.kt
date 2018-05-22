package Eq

import Eq.Clauses.Clause
import Eq.Clauses.EmptyClause
import Eq.Clauses.IntLiteralClause
import Eq.Clauses.LogicalHiddenBracketClause
import Eq.Clauses.BracketClause
import java.lang.Character.isDigit

class Parser() {

    private var textChars : CharArray = charArrayOf()
    private var index: Int = 0
    private var curToken: Token = Token("", TokenType.Empty)
    private var finishedParsing : Boolean = false
    private var statement : Clause = EmptyClause()

    fun parse(text: String?): Clause {
        return if(text.isNullOrEmpty()) {
            EmptyClause()
        }
        else {
            textChars = text!!.toCharArray()
            index = 0
            finishedParsing = false
            statement = parseStatement()
            curToken = Token("", TokenType.Empty)
            return statement
        }
    }

    private fun parseStatement() : Clause {
        var statement : Clause = EmptyClause()
        getNextToken()
        do {
            statement = parseClause(statement)
        } while (curToken.type != TokenType.Empty)
        return statement
    }

    private fun parseClause(curClause : Clause) : Clause {
        var lhs = subClause(curClause)
        while(curToken.type == TokenType.Add || curToken.type == TokenType.Subtraction || curToken.type == TokenType.Multiply || curToken.type == TokenType.Division) {
            val newClause = curToken.createCorrespondingClause(lhs)
            getNextToken()
            lhs = parseClause(newClause)
        }
        return lhs
    }

    private fun subClause(curClause : Clause) : Clause {
        if(curToken.type == TokenType.Integer) {
            var clause : Clause = IntLiteralClause(curToken.token.toInt())

            if(curClause.hasRHS()) {
                curClause.setRHS(clause)
                clause = LogicalHiddenBracketClause(curClause)
            }

            getNextToken()
            return clause
        } else if(curToken.type == TokenType.LeftBracket){
            var clause : Clause

            getNextToken()
            val middleClause = parseClause(EmptyClause())
            if(curToken.type == TokenType.RightBracket) {
                getNextToken()
            }
            clause = BracketClause(middleClause)

            if(curClause.hasRHS()) {
                curClause.setRHS(clause)
                clause = curClause
            }
            return clause
        }
        throw IllegalArgumentException()
    }

    private fun getNextToken() {
        curToken = Token("", TokenType.Empty)

        if(index < textChars.size) {

            var tokenChars: MutableList<Char> = mutableListOf()
            var curChar = textChars[index]
            while (curChar == ' ') {
                index++;
                curChar = textChars.get(index)
            }

            if (curChar == '(') {
                curToken = Token("(", TokenType.LeftBracket)
                index++
            } else if (curChar == ')') {
                curToken = Token(")", TokenType.RightBracket)
                index++
            } else if (curChar == '+') {
                curToken = Token("+", TokenType.Add)
                index++
            } else if (curChar == '-') {
                curToken = Token("-", TokenType.Subtraction)
                index++
            } else if (curChar == '*') {
                curToken = Token("*", TokenType.Multiply)
                index++
            } else if (curChar == '/') {
                curToken = Token("/", TokenType.Division)
                index++
            } else {

                if (isDigit(curChar)) {
                    do {
                        tokenChars.add(tokenChars.size, curChar)
                        index++
                        curChar = if (index < textChars.size) textChars.get(index) else 0.toChar()

                    } while (isDigit(curChar))

                    curToken = Token(tokenChars.joinToString(""), TokenType.Integer)
                }
            }
        }
    }
}