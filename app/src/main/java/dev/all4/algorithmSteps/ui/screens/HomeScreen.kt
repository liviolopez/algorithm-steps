package dev.all4.algorithmSteps.ui.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.all4.algorithmSteps.others.TreeNode
import dev.all4.algorithmSteps.others.parseTree
import dev.all4.algorithmSteps.utils._log
import dev.all4.algorithmSteps.utils._logline
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Livio Lopez on 12/6/20.
 */
@Composable
fun HomeScreen() {
    Surface(color = MaterialTheme.colors.background) {
        ScrollableColumn(
                modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
        ) {
            Text(text = "Hello I am in Home")

            ScrollableRow(
                    modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
            ) {
//                val treeNodeInt = TreeNode(1)
//                        treeNodeInt.left = TreeNode(5)
//                                .apply { left = TreeNode(2); right = TreeNode(6);  }
//                        treeNodeInt.right = TreeNode(4)
//                treeNodeInt._log()

                val treeNodeInt = TreeNode(1)
                treeNodeInt.right = TreeNode(2)
                        .apply { left = TreeNode(3);  }
                treeNodeInt._log()

                ">"._logline()
                Solution().levelOrder(parseTree("3,9,20,null,null,15,7"))._log()

//                Solution().preorderTraversal(treeNodeInt)._log()
//                "*"._logline()
//                Solution().preorderTraversalImproved(treeNodeInt)._log()

//                Solution().inorderTraversal(treeNodeInt)._log()
//                "*"._logline()
//                Solution().inorderTraversalImproved(treeNodeInt)._log()
//

//                Solution().postorderTraversal(treeNodeInt)._log()
//                "*"._logline()
//                Solution().postorderTraversalImproved(treeNodeInt)._log()
            }
        }
    }
}

class Solution {

    fun levelOrder(root: TreeNode?): List<List<Int>>{
        val answer: ArrayList<List<Int>> = ArrayList()
        var currentNode: TreeNode?
        val queue: Queue<TreeNode> = LinkedList()

        root?.let { queue.offer(it) }
        while(queue.isNotEmpty()) {
            val size = queue.size
            val listLevel = LinkedList<Int>()

            for (i in 0 until size) {
                currentNode = queue.poll()
                listLevel.add(currentNode.`val`)
                currentNode.left?.let { queue.offer(it) }
                currentNode.right?.let { queue.offer(it) }
            }

            answer.add(listLevel)
        }

        return answer
    }

    fun inorderTraversal(root: TreeNode?): List<Int> {
        val orderedList = ArrayList<Int>()
        var currentNode: TreeNode? = root
        val stack = Stack<TreeNode>()

        currentNode?.let { stack.push(it) }
        while(stack.isNotEmpty() && currentNode != null){

            if(currentNode.left != null){
                val tmpCurrent = currentNode
                currentNode = currentNode.left

                tmpCurrent.left = null
                stack.push(tmpCurrent)
            } else {
                orderedList.add(currentNode!!.`val`)

                if(currentNode.right != null){
                    currentNode = currentNode.right
                } else {
                    currentNode = if(stack.isNotEmpty()) {
                        stack.pop()
                    } else {
                        null
                    }
                }
            }
        }

        return orderedList.toList()
    }

    fun inorderTraversalImproved(root: TreeNode?): List<Int> {
        val orderedList = ArrayList<Int>()
        var currentNode: TreeNode?
        val stack = Stack<TreeNode>()

        root?.let { stack.push(it) }
        while(stack.isNotEmpty()) {
            currentNode = stack.pop()

            currentNode?.right?.let { stack.push(it) }
            currentNode?.let { stack.push(TreeNode(it.`val`)) }
            currentNode?.left?.let { stack.push(it) }

            if(currentNode.left == null && currentNode.right == null) {
                currentNode = stack.pop()
                orderedList.add(currentNode.`val`)
            }
        }

        return orderedList
    }

    fun preorderTraversalImproved(root: TreeNode?): List<Int> {
        val orderedList = mutableListOf<Int>()
        var currentNode: TreeNode?
        val stack = Stack<TreeNode>()

        root?.let { stack.push(it) }
        while(stack.isNotEmpty()){
            currentNode = stack.pop()
            orderedList.add(currentNode.`val`)

            // is filled from the right to the left because here LIFO is apply
            currentNode.right?.let { stack.push(it) }
            currentNode.left?.let { stack.push(it) }
        }

        return orderedList
    }

    fun preorderTraversal(root: TreeNode?): List<Int> {
        val orderedList = mutableListOf<Int>()
        var currentNode: TreeNode? = root
        val stack = Stack<TreeNode>()

        while(currentNode != null){
            orderedList.add(currentNode.`val`)

            when {
                currentNode.left != null -> {
                    currentNode.right?.let { stack.push(it) }
                    currentNode = currentNode.left
                }
                currentNode.right != null -> {
                    currentNode = currentNode.right
                }
                else -> {
                    currentNode = null
                    if(stack.isNotEmpty()) {
                        currentNode = stack.pop()
                    }
                }
            }
        }

        return orderedList.toList()
    }

    fun postorderTraversal(root: TreeNode?): List<Int> {
        val orderedList = ArrayList<Int>()
        var currentNode = root
        val stack = Stack<TreeNode>()

        currentNode?.let { stack.push(it) }
        while(stack.isNotEmpty() && currentNode != null){

            when {
                currentNode.left != null -> {
                    val tmpCurrent = currentNode
                    currentNode = currentNode.left

                    tmpCurrent.left = null
                    stack.push(tmpCurrent)
                }
                currentNode.right != null -> {
                    val tmpCurrent = currentNode
                    currentNode = currentNode.right

                    tmpCurrent.right = null
                    stack.push(tmpCurrent)
                }
                else -> {
                    orderedList.add(currentNode.`val`)
                    currentNode = if(stack.isNotEmpty()) stack.pop() else null
                }
            }
        }

        return orderedList
    }

    fun postorderTraversalImproved(root: TreeNode?): List<Int> {
        val orderedList = ArrayList<Int>()
        var currentNode: TreeNode?
        val stack = Stack<TreeNode>()

        root?.let { stack.push(it) }
        while(stack.isNotEmpty()) {
            currentNode = stack.pop()

            currentNode?.let { stack.push(TreeNode(it.`val`)) }
            currentNode?.right?.let { stack.push(it) }
            currentNode?.left?.let { stack.push(it) }

            if(currentNode.left == null && currentNode.right == null) {
                currentNode = stack.pop()
                orderedList.add(currentNode.`val`)
            }
        }

        return orderedList
    }
}