package Eq

import Eq.Clauses.Clause
import Eq.Clauses.EmptyClause
import Eq.Clauses.IntLiteralClause
import Eq.Clauses.SubtractionClause
import Eq.Clauses.LogicalHiddenBracketClause
import Eq.Clauses.BracketClause
import java.lang.Character.isDigit

class Parser() {

    private var textChars : CharArray = charArrayOf()
    private var index: Int = 0
    private var curToken: Token = Token("", TokenType.Empty)

    fun parse(text: String?): Clause {
        if(text == null || text.isEmpty()) {
            throw IllegalArgumentException()
        }

        return parseStatement(text)
    }

    private fun parseStatement(text: String) : Clause {
        textChars = text.toCharArray()
        index = 0
        getNextToken()
        val parsedClause = parseClause(EmptyClause())

        if(index < textChars.size - 1) {
            throw IllegalArgumentException()
        }

        return parsedClause
    }

    private fun parseClause(curClause : Clause) : Clause {
        var lhs = subClause(curClause)

        while(curToken.type == TokenType.Add
                || curToken.type == TokenType.Subtraction
                || curToken.type == TokenType.Multiply
                || curToken.type == TokenType.Division) {
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
            } else {
                throw IllegalArgumentException()
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
            val curChar = getFirstCharAfterWhitespace()
            when {
                curChar == '(' -> {
                    curToken = Token("(", TokenType.LeftBracket)
                    index++
                }
                curChar == ')' -> {
                    curToken = Token(")", TokenType.RightBracket)
                    index++
                }
                curChar == '+' -> {
                    curToken = Token("+", TokenType.Add)
                    index++
                }
                curChar == '-' -> {
                    curToken = Token("-", TokenType.Subtraction)
                    index++
                }
                curChar == '*' -> {
                    curToken = Token("*", TokenType.Multiply)
                    index++
                }
                curChar == '/' -> {
                    curToken = Token("/", TokenType.Division)
                    index++
                }
                isDigit(curChar) -> curToken = getNextIntegerToken()
            }
        }
    }

    private fun getFirstCharAfterWhitespace() : Char {
        var curChar = textChars[index]
        while (curChar == ' ') {
            index++
            curChar = textChars[index]
        }
        return curChar
    }

    private fun getNextIntegerToken() : Token {
        var curChar = textChars.get(index)
        val tokenChars: MutableList<Char> = mutableListOf()
        while(isDigit(curChar)) {
            tokenChars.add(tokenChars.size, curChar)
            index++
            curChar =
                if (index < textChars.size)
                    textChars[index]
                else
                    0.toChar()
        }
        return Token(tokenChars.joinToString(""), TokenType.Integer)
    }
}