package name.alatushkin.utils.variety

import name.alatushkin.utils.variety.Lexem.*
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
    var expects = EnumSet.of(OPEN, STRING)

    while (idx < lexems.size) {
        val (currentLexeme, currentLexemeLocation) = lexems[idx]

        if (!expects.contains(currentLexeme))
            throw ParseException(str, currentLexemeLocation.begin, ParseErrorType.UNEXPECTED)

        if (currentLexeme == STRING) {
            nodes.add(StringNode(currentLexemeLocation.fromString(str)))
        } else if (currentLexeme == OPEN) {
            val (subNodes, lastIdx) = parseVariants(str, lexems, idx)
            nodes.add(subNodes)
            idx = lastIdx
        } else {
            TODO("Unknown lexeme $currentLexeme  at $idx")
        }
        idx++

    }
    return ListNode(nodes.toTypedArray()) to idx
}

@Throws(ParseException::class)
internal fun parseVariants(str: String, lexems: Array<LexemMark>, startIdx: Int = 0): Pair<TreeNode, Int> {


    if (lexems[startIdx].first != OPEN)
        throw ParseException(str, lexems[startIdx].second.end, ParseErrorType.EXPECT)

    var idx = startIdx + 1

    val nodes = ArrayList<TreeNode>()
    var expects = EnumSet.of(OPEN, STRING)

    while (idx < lexems.size) {
        val (currentLexeme, currentLexemeLocation) = lexems[idx]

        if (!expects.contains(currentLexeme))
            throw ParseException(str, currentLexemeLocation.begin, ParseErrorType.UNEXPECTED)

        if (currentLexeme == CLOSE) {
            break
        } else if (currentLexeme == OR) {
            expects = EnumSet.of(OPEN, STRING)
        } else {
            val (subNodes, lastIdx) = parseVariant(str, lexems, idx)
            nodes.add(subNodes)
            idx = lastIdx
            expects = EnumSet.of(CLOSE, OR)
        }
        idx++
    }

    if (idx >= lexems.size)
        throw ParseException(str, str.length, ParseErrorType.UNEXPECTED)
    if (lexems[idx].first != CLOSE)
        throw ParseException(str, lexems[idx].second.end, ParseErrorType.EXPECT)

    return VaritetyNode(nodes.toTypedArray()) to idx
}

@Throws(ParseException::class)
internal fun parseVariant(str: String, lexems: Array<LexemMark>, startIdx: Int = 0): Pair<TreeNode, Int> {
    var idx = startIdx

    val nodes = ArrayList<TreeNode>()
    var expects = EnumSet.of(OPEN, STRING)

    while (idx < lexems.size) {
        val (currentLexeme, currentLexemeLocation) = lexems[idx]

        if (!expects.contains(currentLexeme))
            throw ParseException(str, currentLexemeLocation.begin, ParseErrorType.UNEXPECTED)

        if (currentLexeme == STRING) {
            nodes.add(StringNode(currentLexemeLocation.fromString(str)))
            expects = EnumSet.allOf(Lexem::class.java)
        } else if (currentLexeme == OPEN) {
            val (subNodes, lastIdx) = parseVariants(str, lexems, idx)
            nodes.add(subNodes)
            idx = lastIdx
            expects = EnumSet.allOf(Lexem::class.java)
        } else if (currentLexeme == OR) {
            idx--
            break
        } else if (currentLexeme == CLOSE) {
            idx--
            break
        } else {
            TODO("Unknown lexeme $currentLexeme  at $idx")
        }
        idx++

    }
    return ListNode(nodes.toTypedArray()) to idx
}