package name.alatushkin.utils.variety

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

internal class LexerKtTest {
    @Test
    fun lexerTest1() {
        val lexemes = markLexems("[s1|s2]")
        assertArrayEquals(arrayOf(
                Lexem.OPEN to Location(0, 0),
                Lexem.STRING to Location(1, 2),
                Lexem.OR to Location(3, 3),
                Lexem.STRING to Location(4, 5),
                Lexem.CLOSE to Location(6, 6)
        )
                , lexemes)
    }
}