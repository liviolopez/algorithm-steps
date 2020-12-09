package dev.all4.algorithmSteps.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import dev.all4.algorithmSteps.others.algorithms.BinaryTree
import dev.all4.algorithmSteps.others.algorithms.resources.*
import dev.all4.algorithmSteps.utils._log
import dev.all4.algorithmSteps.utils._logline
import dev.all4.algorithmSteps.utils._output
import java.util.*

/**
 * Created by Livio Lopez on 12/6/20.
 */
@Composable
fun Spacer(){
    Spacer(modifier = Modifier.padding(3.dp))
}

@Composable
fun HomeScreen() {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(bottom = 50.dp)) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp)) {
            Text(text = "Algorithms", style = TextStyle(fontSize = TextUnit.Sp(18), fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.padding(8.dp))

            val promptPrinter = remember { mutableStateOf("") }
            val logcatPrinter = remember { mutableStateOf("Logcat ...\n") }
            val logcatScrollState = rememberScrollState()

            val defaultSerializedTree = "1,2,3"
            val serializeTree = remember { mutableStateOf(TextFieldValue(defaultSerializedTree)) }
            val history = Stack<String>()
            history.push(defaultSerializedTree)
            promptPrinter.value = parseTree(defaultSerializedTree)._pretty()

            Card(elevation = 8.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                                modifier = Modifier.weight(1f),
                                value = serializeTree.value,
                                onValueChange = {
                                    if (history.peek() != it.text) {
                                        history.push(it.text)
                                        promptPrinter.value = parseTree(it.text)._pretty()
                                    }

                                    serializeTree.value = it
                                },
                                maxLines = 1,
                                label = { Text("Serialize Tree") },
                        )

                        Spacer()

                        IconButton(
                                modifier = Modifier.height(55.dp).background(color = MaterialTheme.colors.onSurface.copy(alpha = ContainerAlpha)),
                                onClick = {
                                    serializeTree.value = if (history.isNotEmpty()) {
                                        TextFieldValue(history.pop())
                                    } else {
                                        history.push(defaultSerializedTree)
                                        TextFieldValue(defaultSerializedTree)
                                    }
                                }
                        ) {
                            Icon(Icons.Filled.Replay)
                        }
                    }

                    Spacer()

                    ScrollableRow(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                        Button(onClick = {
                            val bt = parseTree(serializeTree.value.text)
                            val result = BinaryTree.levelOrder(bt)

                            promptPrinter.value = bt._pretty()
                            logcatPrinter.value += result._output()
                            logcatScrollState.smoothScrollBy(100F)

                            "-"._logline()
                            result._log()
                        }) { Text("Level Order") }

                        Spacer()

                        Button(onClick = {
                            val bt = parseTree(serializeTree.value.text)
                            promptPrinter.value = bt._pretty()

                            val result = BinaryTree.preorderTraversal(bt)
                            logcatPrinter.value += result._output()
                            logcatScrollState.smoothScrollBy(100F)

                            "-"._logline()
                            result._log()
                        }) { Text("Preorder Traversal") }

                        Spacer()

                        Button(onClick = {
                            val bt = parseTree(serializeTree.value.text)
                            promptPrinter.value = bt._pretty()

                            val result = BinaryTree.inorderTraversal(bt)
                            logcatPrinter.value += result._output()
                            logcatScrollState.smoothScrollBy(100F)

                            "-"._logline()
                            result._log()
                        }) { Text("Inorder Traversal") }

                        Spacer()

                        Button(onClick = {
                            val bt = parseTree(serializeTree.value.text)
                            promptPrinter.value = bt._pretty()

                            val result = BinaryTree.postorderTraversal(bt)
                            logcatPrinter.value += result._output()
                            logcatScrollState.smoothScrollBy(100F)

                            "-"._logline()
                            result._log()
                        }) { Text("Postorder Traversal") }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))


            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

                val modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                        .background(color = Color.DarkGray)
                        .border(border = BorderStroke(1.dp, Color.White))

                ScrollableRow(modifier = modifier) {
                    ScrollableColumn(modifier = Modifier.padding(5.dp)) {
                        Text(text = promptPrinter.value, fontFamily = FontFamily.Monospace)
                    }
                }

                ScrollableRow(modifier = modifier) {
                    ScrollableColumn(modifier = Modifier.padding(5.dp), scrollState = logcatScrollState) {
                        Text(text = logcatPrinter.value, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = false)
//@Composable
//private fun PreviewHomeScreen(){
//    HomeScreen()
//}