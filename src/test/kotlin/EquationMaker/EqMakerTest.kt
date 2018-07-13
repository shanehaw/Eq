package EquationMaker

import EquationCore.Eq
import EquationCore.Scanner
import EquationCore.Parser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class EqMakerTest {

    @Test
    fun `can create basic equation`() {
        val creationString = "[0-9] + 1 = [1-10]"
        val eqMaker = EqMaker() //EqMaker(Eq(Parser(Scanner())))
        val possibilities: List<String> = eqMaker.parse(creationString)
        assertThat(possibilities.count(), `is`(9))
    }
}