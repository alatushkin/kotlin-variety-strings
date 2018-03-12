package name.alatushkin.utils.variety

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ParserKtTests {

    fun stringsToTestParsing(): Collection<String> {
        return listOf(
                "simple",
                "[s1]",
                "[s1|s2]",
                "s1[s2|s3]",
                "[s1|s2]s3"
        )
    }


    @TestFactory
    fun parseSmokeTests(): Collection<DynamicTest> {
        return stringsToTestParsing().map { str ->
            DynamicTest.dynamicTest(str,
                    { assertEquals(str, parse(str).toString()) })
        }.toList()

    }
}