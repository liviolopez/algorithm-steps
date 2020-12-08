package dev.all4.algorithmSteps.others

import java.util.*

/**
 * Created by Livio Lopez on 12/7/20.
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun parseTree(s: String?): TreeNode? {
    if (s == null || s.isEmpty()) return null

    val split = s.replace("#","")
                 .replace("null","")
                 .split(",".toRegex())
                 .map { if(it == "") null else it.toInt() }.toTypedArray()

    return buildTree(*split)
}

fun buildTree(vararg item: Any?): TreeNode? {
    if (item.isEmpty() || item[0] == null) return null
    val q: Queue<TreeNode?> = LinkedList()
    var i = 0
    val root = TreeNode(item[i++]!! as Int)
    q.offer(root)
    while (!q.isEmpty()) {
        val node = q.poll()
        if (i >= item.size) break
        if (item[i++] != null) {
            node!!.left = TreeNode(item[i - 1]!! as Int)
            q.offer(node.left)
        }
        if (i >= item.size) break
        if (item[i++] != null) {
            node!!.right = TreeNode(item[i - 1]!! as Int)
            q.offer(node.right)
        }
    }
    return root
}