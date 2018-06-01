package Eq

class Scanner {

    private var index: Int = 0
    private var textChars: CharArray = charArrayOf()

    fun getNextToken(text: String, index: Int) : ScanContext {
        this.index = index
        this.textChars = text.toCharArray()
        val curToken = getNextToken()
        return ScanContext(this.index, curToken)
    }

    fun getPrevToken(text: String, index: Int) : ScanContext {
        this.index = index
        this.textChars = text.toCharArray()
        val curToken = getNextToken(false)
        return ScanContext(this.index, curToken)
    }

    private fun getNextToken(forwards: Boolean = true) : Token {
        var curToken = Token("", TokenType.Empty)
        if (index < textChars.size && index >= 0) {
            val curChar = if(forwards) getFirstCharAfterWhitespace() else getFirstCharBeforeWhitespace()
            when {
                curChar == '(' -> {
                    curToken = Token("(", TokenType.LeftBracket)
                    index = if(forwards) index + 1 else index -1
                }
                curChar == ')' -> {
                    curToken = Token(")", TokenType.RightBracket)
                    index = if(forwards) index + 1 else index -1
                }
                curChar == '+' -> {
                    curToken = Token("+", TokenType.Add)
                    index = if(forwards) index + 1 else index -1
                }
                curChar == '-' -> {
                    curToken = Token("-", TokenType.Subtraction)
                    index = if(forwards) index + 1 else index -1
                }
                curChar == '*' -> {
                    curToken = Token("*", TokenType.Multiply)
                    index = if(forwards) index + 1 else index -1
                }
                curChar == '/' -> {
                    curToken = Token("/", TokenType.Division)
                    index = if(forwards) index + 1 else index -1
                }
                Character.isDigit(curChar) -> curToken = if(forwards) getNextIntegerToken() else getPrevIntegerToken()
            }

            if(forwards) getFirstCharAfterWhitespace() else getFirstCharBeforeWhitespace()
        }
        return curToken
    }

    private fun getFirstCharAfterWhitespace(): Char {
        if(index < textChars.size) {
            var curChar = textChars[index]
            while (curChar == ' ') {
                index++
                curChar = textChars[index]
            }
            return curChar
        }
        return 0.toChar()
    }

    private fun getFirstCharBeforeWhitespace(): Char {
        if(index >= 0) {
            var curChar = textChars[index]
            while (curChar == ' ') {
                index--
                curChar = textChars[index]
            }
            return curChar
        }
        return 0.toChar()
    }

    private fun getNextIntegerToken(): Token {
        var curChar = textChars.get(index)
        val tokenChars: MutableList<Char> = mutableListOf()
        while (Character.isDigit(curChar)) {
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

    private fun getPrevIntegerToken(): Token {
        var curChar = textChars.get(index)
        val tokenChars: MutableList<Char> = mutableListOf()
        while (Character.isDigit(curChar)) {
            tokenChars.add(tokenChars.size, curChar)
            index--
            curChar =
                    if (index >= 0)
                        textChars[index]
                    else
                        0.toChar()
        }
        tokenChars.reverse()
        return Token(tokenChars.joinToString(""), TokenType.Integer)
    }


}