package name.alatushkin.utils.variety

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TreeNodeTest {

    @Test
    fun getCountTest0() {
        autotest("s0[[s1|s2]as|s3]s[s4|s5]s6")
    }

    @Test
    fun variantTest0() {
        autotest("s0[[s1|s2]|s3]s[s4|s5]s6")
    }

    @Test
    fun variantTest1() {
        autotest("1[2|3|[4|5|[6|7]8[9|10|[11|12|13]]]]")
    }

    @Test
    fun variantTest2() {
        val str = "0[1|2]3[4|[5|6]]"
        println("Variants for")
        println(str)
        for (idx in 0 until str.variants()) {
            println(str.variant(idx))
        }
        autotest(str)
    }

    internal fun autotest(str: String) {
        val resultSet = IntRange(0, str.variants() - 1)
                .map { str.variant(it) }
                .toSet()
        assertEquals(str.variants(), resultSet.size, "Variants counts must be equal to estimate for string $str")
    }
}