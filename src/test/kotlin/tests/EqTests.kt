package tests

import Eq.Eq
import Eq.Parser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertFailsWith

class EqTests {

    fun createEq(): Eq = Eq(Parser())

    @Test
    fun `nonsense in nonsense out`() {
        val eq = createEq()

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("nonsense")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval(null)
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("*")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("+")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("-")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("/")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("^")
        })

        assertFailsWith(IllegalArgumentException::class, {
            eq.eval("1 +")
        })
    }

    fun testEq(text : String, expected : Int) {
        val eq = createEq()

        val result = eq.eval(text)
        assertThat(result, `is`(expected))

        val resultText = eq.evalAndPrintBack(text)
        assertThat(resultText, `is`(text))
    }

    @Test
    fun `simple number`() {
        testEq("1", 1)
        testEq("2", 2)
        testEq("299987", 299987)
    }

    @Test
    fun `addition`() {
        testEq("1 + 1", 2)
        testEq("1 + 2", 3)
        testEq("2 + 2", 4)
        testEq("1 + 2 + 3", 6)
        testEq("1 + 2 + 3 + 9999 + 321", 10326)
    }

    @Test
    fun `subtraction`() {
        testEq("1 - 1", 0)
        testEq("10 - 5", 5)
        testEq("100 - 10 - 10 - 10 - 10 - 10", 50)
    }

    @Test
    fun `add and sub`() {
        testEq("1 + 10 - 5 + 3 - 1 + 5 + 10", 23)
    }

    @Test
    fun`brackets`() {
        testEq("(1 + 10) - 10", 1)
        testEq("100 + (1 + 10) - ((13 - 3) + 2)", 99)
        testEq("((100 - 50) - (40 + 25 - 30)) - (1 + 10) - ((13 - 3) + 2)", -8)
    }

    @Test
    fun `multiply`() {
        testEq("1 * 1", 1)
        testEq("1 * 2", 2)
        testEq("5 * 5", 25)
        testEq("5 * 5 * 5", 125)
    }

    @Test
    fun `brackets and multiply`() {
        testEq("((10 - 2 - 3) + 5) * (2 + 3)", 50)
    }

    @Test
    fun `division`() {
        testEq("1 / 1", 1)
        testEq("8 / 2 / 2", 2)

    }

    @Test
    fun `combination`() {
        testEq("4 * 5 / 2 + 7", 17)
        testEq("((25 / 5 * 2) / 2) * 30 - 5 + 5", 150)

    }
}