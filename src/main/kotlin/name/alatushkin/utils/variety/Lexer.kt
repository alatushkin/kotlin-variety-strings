package name.alatushkin.utils.variety

enum class Lexem {
    STRING, OPEN, OR, CLOSE
}

data class Location(val begin: Int, val end: Int) {
    fun fromString(str: String) = str.substring(begin, end + 1)
}

typealias LexemMark = Pair<Lexem, Location>

internal fun markLexems(str: String, openChar: Char = '[', closeChar: Char = ']', orChar: Char = '|'): Array<LexemMark> {
    val delimeters = charArrayOf(openChar, closeChar, orChar)
    val marks = ArrayList<LexemMark>()
    var idx = 0

    while (idx < str.length) {
        val nextMark = if (str[idx] == openChar)
            Lexem.OPEN to Location(idx, idx)
        else if (str[idx] == closeChar)
            Lexem.CLOSE to Location(idx, idx)
        else if (str[idx] == orChar)
            Lexem.OR to Location(idx, idx)
        else {
            var endIdx = str.indexOfAny(delimeters, idx)
            if (endIdx == -1)
                endIdx = str.length
            Lexem.STRING to Location(idx, endIdx - 1)
        }

        marks.add(nextMark)
        idx = nextMark.second.end + 1
    }
    return marks.toTypedArray()
}