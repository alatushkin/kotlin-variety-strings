package name.alatushkin.utils.variety

import java.util.concurrent.ThreadLocalRandom


sealed class TreeNode {
    abstract val count: Int

    fun random() = variant(ThreadLocalRandom.current().nextInt(0, count))

    fun variant(variantIdx: Int): String {
        assert(variantIdx in 0 until count)
        return getVariant(variantIdx)
    }

    internal abstract fun getVariant(variantIdx: Int): String
}

internal class StringNode(private val str: String) : TreeNode() {
    override fun getVariant(variantIdx: Int) = str

    override val count: Int
        get() = 1

    override fun toString() = str
}

private fun multiply(arg1: Int, arg2: Int) = arg1 * arg2
private fun summ(arg1: Int, arg2: Int) = arg1 + arg2

internal class ListNode(private val items: Array<TreeNode>) : TreeNode() {
    override fun getVariant(variantIdx: Int): String {
        var reminder = variantIdx
        val result = StringBuilder()


        //количество комбинаций для каждого разряда
        val digitsSizes = items
            .map { it.count }
            .stream()
            .mapToInt({ it })
            .filter { count -> count > 1 }
            .skip(1).toArray()
            .reversedArray()

        //основания системы счисления с переменным основанием
        val bases = digitsSizes.mapIndexed { idx, count -> count * digitsSizes.getOrElse(idx - 1, { 1 }) }
            .reversed().toTypedArray() + 1

        var idx = 0
        for (element in items) {
            if (element.count == 1)
                result.append(element.getVariant(reminder))
            else {
                val elementSeed = (reminder / bases[idx])
                result.append(element.getVariant(elementSeed))
                reminder -= elementSeed * bases[idx]
                idx++
            }


        }

        return result.toString()
    }

    override val count: Int
        get() = items.map { it.count }.reduce(::multiply)

    override fun toString() = items.joinToString("")
}

internal class VaritetyNode(private val items: Array<TreeNode>) : TreeNode() {
    override fun getVariant(variantIdx: Int): String {
        var reminder = variantIdx
        for (item in items) {
            if (reminder - item.count < 0)
                return item.variant(reminder)
            reminder -= item.count
        }

        return items.last().variant(reminder)
    }

    override val count: Int
        get() = items.map { it.count }.reduce(::summ)

    override fun toString() = items.joinToString("|", "[", "]")
}