package name.alatushkin.utils.variety

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

class ParserKtTests {

    fun stringsToTestParsing(): Collection<String> {
        return listOf(
                "simple",
                "[s1]",
                "[s1|s2]",
                "s1[s2|s3]",
                "[s1|s2]s3",
                "s0[s1|s2]s3",
                "s0[[s1|s2]|s3]s[s4|s5]s6",
                "a[b|c|[d|e]f[g|h]]i"
        )
    }

    fun errorParsingStrings(): Collection<String> {
        return listOf(
                "[s1",
                "[|s2]",
                "s1][s2|s3]",
                "[s1|s2s3"
        )
    }


    @TestFactory
    fun parseSmokeTests(): Collection<DynamicTest> {
        return stringsToTestParsing().map { str ->
            DynamicTest.dynamicTest(str,
                    { assertEquals(str, parse(str).toString()) })
        }.toList()

    }

    @TestFactory
    fun parseSmokeTestsParseErrors(): Collection<DynamicTest> {
        return errorParsingStrings().map { str ->
            DynamicTest.dynamicTest(str,
                    {
                        assertThrows<ParseException> {
                            parse(str)
                        }
                    })
        }.toList()

    }

    @Test
    fun doDebug() {
        assertEquals("[b|[g|h]]", parse("[b|[g|h]]").toString())
    }
}