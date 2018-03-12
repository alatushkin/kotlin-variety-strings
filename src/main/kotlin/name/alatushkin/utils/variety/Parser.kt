package name.alatushkin.utils.variety

class ParseException(val str: String, val position: Int) : RuntimeException()

@Throws(ParseException::class)
fun parse(str: String, openChar: Char = '[', closeChar: Char = ']', orChar: Char = '|'): TreeNode {
    return StringNode("")
}
