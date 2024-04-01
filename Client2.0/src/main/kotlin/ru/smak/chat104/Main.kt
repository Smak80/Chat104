package ru.smak.chat104

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

val mvm = MainViewModel()

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxSize().padding(4.dp)){
            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .border(1.dp, Color.Black, RectangleShape),
                    state = mvm.scrollState
                ) {
                    items(mvm.messages) {
                        Text(it, Modifier.fillMaxWidth())
                    }
                }
                VerticalScrollbar(mvm.sbAdapter)
            }
            Text(mvm.serverMessage)
            Row(Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = mvm.userInput, onValueChange = {
                        mvm.userInput = it
                    },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    mvm.send()
                }){
                    Text("Отправить")
                }
            }
        }
    }

}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
