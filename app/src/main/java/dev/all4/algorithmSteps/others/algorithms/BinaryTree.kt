package dev.all4.algorithmSteps.others.algorithms

import dev.all4.algorithmSteps.others.algorithms.resources.TreeNode
import java.util.*
import kotlin.collections.ArrayList

object BinaryTree {
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

        root?.let { stack.push(it) }
        while(stack.isNotEmpty() && currentNode != null){

            if(currentNode.left != null){
                val tmpCurrent = currentNode
                currentNode = currentNode.left

                tmpCurrent.left = null
                stack.push(tmpCurrent)
            } else {
                orderedList.add(currentNode.`val`)

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

        root?.let { stack.push(it) }
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