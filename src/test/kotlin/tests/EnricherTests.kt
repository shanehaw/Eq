package tests

import Eq.Enricher
import Eq.Scanner
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
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

        result = enricher.enrich("1 + 1")
        assertThat(result, `is`("(1 + 1)"))
    }

    @Test
    fun `multi-clause-same-operation enrichment`() {
        val enricher = Enricher(Scanner())
        var result = ""

        result = enricher.enrich("1 + 1 / 1")
        assertThat(result, `is`("(1 + (1 / 1))"))

//        result = enricher.enrich("1 + 1 / 1 - 1 / 1 + 1")
//        assertThat(result, `is`("(1 + (1 / 1) - (1 / 1) + 1)"))
    }
}