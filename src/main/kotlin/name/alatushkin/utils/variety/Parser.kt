package name.alatushkin.utils.variety

import java.util.*

enum class ParseErrorType {
    EXPECT, UNEXPECTED
}

class ParseException(val str: String, val position: Int, val errorType: ParseErrorType) : RuntimeException() {
    override fun toString(): String {
        return "ParseException(str='$str', position=$position, errorType=$errorType)"
    }
}

@Throws(ParseException::class)
internal fun parse(str: String, lexems: Array<LexemMark>, startIdx: Int = 0): Pair<TreeNode, Int> {
    var idx = startIdx

    val nodes = ArrayList<TreeNode>()
    var expects = EnumSet.of(Lexem.OPEN, Lexem.STRING)

    while (idx < lexems.size) {
        val (currentLexeme, currentLexemeLocation) = lexems[idx]

        if (!expects.contains(currentLexeme))
            throw ParseException(str, currentLexemeLocation.begin, ParseErrorType.UNEXPECTED)

        if (currentLexeme == Lexem.STRING) {
            nodes.add(StringNode(currentLexemeLocation.fromString(str)))
            idx++
            expects = EnumSet.allOf(Lexem::class.java)
        } else if (currentLexeme == Lexem.OPEN) {
            val (subNodes, lastIdx) = parse(str, lexems, idx + 1)
            nodes.add(subNodes)
            idx = lastIdx + 1
            if (lastIdx == lexems.size || lexems[lastIdx].first != Lexem.CLOSE)
                throw ParseException(str, currentLexemeLocation.begin, ParseErrorType.UNEXPECTED)

            expects = EnumSet.allOf(Lexem::class.java)
        } else if (currentLexeme == Lexem.CLOSE) {
            return VaritetyNode(nodes.toTypedArray()) to idx
        } else if (currentLexeme == Lexem.OR) {
            idx++
            expects = EnumSet.of(Lexem.STRING, Lexem.OPEN, Lexem.CLOSE)
        } else {
            TODO("Unknown lexeme $currentLexeme  at $idx")
        }

    }
    return ListNode(nodes.toTypedArray()) to idx
}


