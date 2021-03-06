package EquationCore

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EnricherTests {

    @Test
    fun `nonsense is ignored`() {
        val enricher = Enricher(Scanner())
        var result = ""

        result = enricher.enrich("")
        assertThat(result, `is`(""))

        result = enricher.enrich("abc")
        assertThat(result, `is`("abc"))

        result = enricher.enrich("...")
        assertThat(result, `is`("..."))

        result = enricher.enrich(" ")
        assertThat(result, `is`(" "))
    }

    @Test
    fun `simple enrichment`() {
        val enricher = Enricher(Scanner())
        var result = ""

        result = enricher.enrich("7 + 7 / 7 + 7 * 7 - 7")
        assertThat(result, `is`("(((7 + (7 / 7)) + (7 * 7)) - 7)"))

        result = enricher.enrich("7+7/7+7*7-7")
        assertThat(result, `is`("(((7+(7/7))+(7*7))-7)"))


        result = enricher.enrich("(1/1)/(1/1)")
        assertThat(result, `is`("(((1/1))/((1/1)))"))

        result = enricher.enrich("(1 / 1) / (1 / 1)")
        assertThat(result, `is`("(((1 / 1)) / ((1 / 1)))"))
    }

    @Test
    fun `multi-clause-same-operation enrichment`() {
        val enricher = Enricher(Scanner())
        var result = ""

        result = enricher.enrich("1 + 1 / 1")
        assertThat(result, `is`("(1 + (1 / 1))"))

        result = enricher.enrich("1 + 1 / 1 - 1 / 1 + 1")
        assertThat(result, `is`("(((1 + (1 / 1)) - (1 / 1)) + 1)"))


        result = enricher.enrich("10 - (1 + 1) / (1 + 1) + 7")
        assertThat(result, `is`("((10 - (((1 + 1)) / ((1 + 1)))) + 7)"))

        result = enricher.enrich("10 - (1 + 1 / 1 + 7) / 8 + 7")
        assertThat(result, `is`("((10 - ((((1 + (1 / 1)) + 7)) / 8)) + 7)"))
    }
}