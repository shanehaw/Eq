package Eq

class Enricher {
    var index = 0


    fun enrich(text: String) : String {
        if(text.length > 0 && text.first().isDigit()) {
            var indexOfLHS = -1
            var indexOfRHS = -1
            var indexOfChs = -1
            var chCounter = -1
            val chs : MutableList<Char> = mutableListOf()

            val charCollection : MutableCollection<Char> = mutableListOf()
            text.toCollection(charCollection)
            chs.addAll(0, charCollection)


            for(i in 0 until text.length) {
                chCounter++

                if(i == indexOfRHS) {
                    chs.add(')')
                    chCounter++
                    indexOfRHS = -1
                }

                if(text[i] == '/') {
                    indexOfChs = chCounter - 1
                    indexOfLHS = i - 1
                    while(indexOfLHS > 0 && text[indexOfLHS].isWhitespace()) {
                        indexOfLHS--
                        indexOfChs--
                    }
                    while(indexOfLHS > 0 && text[indexOfLHS].isDigit()) {
                        indexOfLHS--
                        indexOfChs--
                    }

                    chs.add(indexOfChs + 1, '(')
                    chCounter++

                    indexOfRHS = i + 1
                    while(indexOfRHS < text.length && text[indexOfRHS].isWhitespace()) {
                        indexOfRHS++
                    }
                    while(indexOfRHS < text.length && text[indexOfRHS].isDigit()) {
                        indexOfRHS++
                    }
                }
            }

            if(indexOfRHS == text.length) {
                chs.add(')')
            }



            return "(${chs.joinToString("")})"
        }
        return text
    }
}