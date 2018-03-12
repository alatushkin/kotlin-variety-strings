package name.alatushkin.utils.variety

interface TreeNode {
    val count: Int
}

internal data class StringNode(val str: String) : TreeNode {
    override val count: Int
        get() = 1

    override fun toString() = str
}

private fun multiply(arg1: Int, arg2: Int) = arg1 * arg2
private fun summ(arg1: Int, arg2: Int) = arg1 + arg2

internal data class ListNode(val items: Array<TreeNode>) : TreeNode {
    override val count: Int
        get() = items.map { it.count }.reduce(::multiply)

    override fun toString() = items.joinToString("")
}

internal data class VaritetyNode(val items: Array<TreeNode>) : TreeNode {
    override val count: Int
        get() = items.map { it.count }.reduce(::summ)

    override fun toString() = items.joinToString("|", "[", "]")
}