package EquationMaker

import EquationCore.Eq
import EquationCore.Scanner
import EquationCore.Parser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.test.assertTrue


class EqMakerTest {

    fun createEqMaker() : EqMaker {
        return EqMaker() //EqMaker(Eq(Parser(Scanner())))
    }

    @Test
    fun `can create basic equation`() {
        val creationString = "[0-9] + 1 = {1-10}"
        val eqMaker = createEqMaker()
        val possibilities: List<String> = eqMaker.parse(creationString)
        assertTrue { possibilities.count() == 9}
        assertTrue { possibilities.contains("0 + 1 = ") }
        assertTrue { possibilities.contains("1 + 1 = ") }
        assertTrue { possibilities.contains("2 + 1 = ") }
        assertTrue { possibilities.contains("3 + 1 = ") }
        assertTrue { possibilities.contains("4 + 1 = ") }
        assertTrue { possibilities.contains("5 + 1 = ") }
        assertTrue { possibilities.contains("6 + 1 = ") }
        assertTrue { possibilities.contains("7 + 1 = ") }
        assertTrue { possibilities.contains("8 + 1 = ") }
        assertTrue { possibilities.contains("9 + 1 = ") }
    }
}