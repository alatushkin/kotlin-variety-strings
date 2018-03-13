package name.alatushkin.utils.variety

@Throws(ParseException::class)
fun parse(str: String, openChar: Char = '[', closeChar: Char = ']', orChar: Char = '|'): TreeNode {
    val lexemes = markLexems(str, openChar, closeChar, orChar)
    val (result, lastIdx) = parse(str, lexemes, 0)
    if (lastIdx < lexemes.size)
        throw ParseException(str, lexemes[lastIdx].second.begin, ParseErrorType.UNEXPECTED)
    return result
}


