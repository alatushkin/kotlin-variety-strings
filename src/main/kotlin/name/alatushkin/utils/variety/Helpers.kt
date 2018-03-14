package name.alatushkin.utils.variety

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom

private val NAIVE_MEMOIZATION = ConcurrentHashMap<String, TreeNode>()

fun String.randomVariant(): String {
    val nextIdx = ThreadLocalRandom.current().nextInt(variants())
    return variant(nextIdx)
}

fun String.variants(): Int {
    val rootNode = NAIVE_MEMOIZATION.getOrPut(this, { parse(this) })
    return rootNode.count
}

fun String.variant(idx: Int): String {
    val rootNode = NAIVE_MEMOIZATION.getOrPut(this, { parse(this) })
    assert(idx in 0 until rootNode.count)
    return rootNode.variant(idx)
}