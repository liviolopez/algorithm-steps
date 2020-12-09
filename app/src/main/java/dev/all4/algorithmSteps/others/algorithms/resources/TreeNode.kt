package dev.all4.algorithmSteps.others.algorithms.resources

import dev.all4.algorithmSteps.utils.getFrom
import java.util.*
import java.util.function.Consumer


/**
 * Original code
 * - author afkbrb
 * - source https://github.com/afkbrb/binary-tree-printer
 *
 * Modified by Livio Lopez on 12/7/20.
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun TreeNode?._pretty(): String {
    if(this == null) return ""

    val treX = _TreeNode().getFrom(this)

    calcDisToParent(treX)
    val map = getMap(treX)
    return parseMap(map)
}

class _TreeNode {
    var `val`: Int = 0
    var left: _TreeNode? = null
    var right: _TreeNode? = null

    var disToParent: Int = 0
    var leftList: MutableList<Int> = ArrayList()
    var rightList: MutableList<Int> = ArrayList()

    init {
        // Calc joint, and add the corresponding border info into the list.
        val len = `val`.toString().length
        val joint = if (len % 2 == 1) (len + 1) / 2 else len / 2 + 1
        leftList.add(joint - 1)
        rightList.add(len - joint)
    }
}

fun parseTree(s: String?): TreeNode? {
    if (s == null || s.isEmpty()) return null

    val split = s.split(",".toRegex())
                 .map { it.toIntOrNull() }
                 .toTypedArray()

    return buildTree(*split)
}

private fun buildTree(vararg item: Any?): TreeNode? {
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

private fun calcDisToParent(node: _TreeNode?) {
    if (node == null) return
    calcDisToParent(node.left)
    calcDisToParent(node.right)

    // When calculating the child's distance to the current node,
    // we must make sure that the left tree won't collide with the right tree.
    var max = 0
    val min = Math.min(
            if (node.left == null) 0 else node.left!!.rightList.size,
            if (node.right == null) 0 else node.right!!.leftList.size
    )
    for (i in 0 until min) {
        max = Math.max(max, node.left!!.rightList[i] + node.right!!.leftList[i])
    }
    // 2 * dis + 1 > max
    // 2 * dis >= max
    // dis >= (max + 1) / 2
    val dis = Math.max((max + 1) / 2, 1)
    if (node.left != null) node.left!!.disToParent = dis
    if (node.right != null) node.right!!.disToParent = dis

    // Update the leftList and rightList of the current node.
    calcLeftList(node)
    calcRightList(node)
}

private fun calcRightList(node: _TreeNode?) {
    if (node == null) return
    if (node.left == null && node.right == null) return
    if (node.left != null && node.right == null) {
        val disToParent = node.left!!.disToParent
        for (i in 1..disToParent) {
            node.rightList.add(-i)
        }
        node.left!!.rightList.forEach(Consumer { d: Int -> node.rightList.add(d - disToParent - 1) })
    } else if (node.left == null) { // node.left == null && node.right != null.
        val disToParent = node.right!!.disToParent
        for (i in 1..disToParent) {
            node.rightList.add(i)
        }
        node.right!!.rightList.forEach(Consumer { d: Int -> node.rightList.add(d + disToParent + 1) })
    } else { // node.left != null && node.right != null.
        val disToParent = node.right!!.disToParent
        for (i in 1..disToParent) {
            node.rightList.add(i)
        }
        node.right!!.rightList.forEach(Consumer { d: Int -> node.rightList.add(d + disToParent + 1) })
        if (node.left!!.rightList.size > node.right!!.rightList.size) {
            for (i in node.right!!.rightList.size until node.left!!.rightList.size) {
                node.rightList.add(node.left!!.rightList[i] - disToParent - 1)
            }
        }
    }
}

private fun calcLeftList(node: _TreeNode?) {
    if (node == null) return
    if (node.left == null && node.right == null) return
    if (node.left != null && node.right == null) {
        val disToParent = node.left!!.disToParent
        for (i in 1..disToParent) {
            node.leftList.add(i)
        }
        node.left!!.leftList.forEach(Consumer { d: Int -> node.leftList.add(d + disToParent + 1) })
    } else if (node.left == null) { // node.left == null && node.right != null.
        val disToParent = node.right!!.disToParent
        for (i in 1..disToParent) {
            node.leftList.add(-i)
        }
        node.right!!.leftList.forEach(Consumer { d: Int -> node.leftList.add(d - disToParent - 1) })
    } else { // node.left != null && node.right != null.
        val disToParent = node.left!!.disToParent
        for (i in 1..disToParent) {
            node.leftList.add(i)
        }
        node.left!!.leftList.forEach(Consumer { d: Int -> node.leftList.add(d + disToParent + 1) })
        if (node.right!!.leftList.size > node.left!!.leftList.size) {
            for (i in node.left!!.leftList.size until node.right!!.leftList.size) {
                node.leftList.add(node.right!!.leftList[i] - disToParent - 1)
            }
        }
    }
}

private fun getMap(node: _TreeNode?): Array<CharArray?>? {
    if (node == null) return null
    var leftWidth = 0
    var rightWidth = 0
    for (w in node.leftList) {
        leftWidth = Math.max(leftWidth, w)
    }
    for (w in node.rightList) {
        rightWidth = Math.max(rightWidth, w)
    }
    val width = leftWidth + rightWidth + 1
    val height = node.leftList.size // rightList.size() is also the same.
    val map = Array<CharArray?>(height) { CharArray(width) }
    for (i in 0 until height) {
        for (j in 0 until width) {
            map[i]!![j] = ' ' // Fill the map with space first.
        }
    }
    fillMap(map, node, leftWidth, 0)
    return map
}

private fun fillMap(map: Array<CharArray?>, node: _TreeNode?, x: Int, y: Int) {
    if (node == null) return
    val s = node.`val`.toString()
    for (i in s.indices) {
        val z = x - node.leftList[0] + i
        if(y < map.size)
            if(z < map[y]!!.size)
                map[y]!![z] = s[i] // Fill the map with node's val.
    }
    if (node.left != null) {
        // '/'
        val disToParent = node.left!!.disToParent
        for (i in 1..disToParent) {
            map[y + i]!![x - i] = '/'
        }
        fillMap(map, node.left, x - disToParent - 1, y + disToParent + 1)
    }
    if (node.right != null) {
        // '\'
        val disToParent = node.right!!.disToParent
        for (i in 1..disToParent) {
            map[y + i]!![x + i] = '\\'
        }
        fillMap(map, node.right, x + disToParent + 1, y + disToParent + 1)
    }
}

private fun parseMap(map: Array<CharArray?>?) : String {
    var binaryTreePrintable = ""
    if (map.isNullOrEmpty() || map[0] == null || map[0]?.size == 0) return ""
    val h = map.size
    val w: Int = map[0]?.size ?: 0
    for (i in 0 until h) {
        for (j in 0 until w) {
            binaryTreePrintable += map[i]!![j]
        }
        binaryTreePrintable += "\n"
    }

    return binaryTreePrintable
}