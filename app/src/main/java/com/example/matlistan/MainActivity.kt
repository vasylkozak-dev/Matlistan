package com.example.matlistan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.matlistan.ui.theme.MatlistanTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration


data class ShoppingItem(
    val name: String,
    val isBought: Boolean = false
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatlistanTheme {
                ShoppingScreen()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ShoppingScreen() {
        var item by remember { mutableStateOf("") }
        val items = remember { mutableStateListOf<ShoppingItem>() }
        val remaining = items.count { !it.isBought }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Matlistan",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold
                                )
                            Text(
                                text = "$remaining av ${items.size} kvar att köpa",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically

                ) {
                    TextField(
                        value = item,
                        onValueChange = {item = it},
                        placeholder = {Text("Ny vara...")},
                        singleLine = true,
                        shape = CircleShape,
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFC7B1ED),
                            unfocusedContainerColor = Color(0xFFC7B1ED),


                    ),
                    )
                Button(
                    onClick = {
                        items.add(ShoppingItem(item.trim()))
                        item = ""
                    },
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp),
                    enabled = item.isNotBlank(),
                    shape = CircleShape,
                ) {
                    Text(text = "+",
                        fontSize = 28.sp)
                }
            }
            }

        ) { innerPadding ->
                Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                    if (items.isEmpty()) {
                        Text(
                            text = "Din inköpslista är tom",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    items.forEach { product ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = product.isBought,
                                onCheckedChange = { isChecked ->
                                    val index = items.indexOf(product)

                                    if (index != -1) {
                                        items[index] = product.copy(isBought = isChecked)
                                    }
                                }
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = product.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                textDecoration = if (product.isBought) TextDecoration.LineThrough
                                else TextDecoration.None,
                                color = if (product.isBought) MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(
                                onClick = {
                                    items.remove(product)
                                }
                            ) {
                                Text(
                                    text = "x",
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }



            if (items.any { it.isBought }) {
                        OutlinedButton(
                            onClick = {
                                items.removeAll { it.isBought }
                            }
                        ) {
                            Text("Ta bort alla köpta varor")
                        }
                    }
            }
        }
    }

@Preview(showBackground = true)
@Composable
    fun ShoppingScreenPreview() {
        MatlistanTheme {
            ShoppingScreen()
        }
    }