package com.example.game

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.game.ui.theme.GameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val preguntas = remember {
        context.resources
            .openRawResource(R.raw.preguntas)
            .bufferedReader()
            .readText()
            .split(";")
            .filter { it.isNotBlank() }
    }

    var preguntaActual by remember {
        mutableStateOf(0)
    }
    var seleccionado = remember {
        mutableStateListOf(-1, -1, -1, -1)
    }
    var respondida by remember {
        mutableStateOf(false)
    }

    // Espera 1 segundo después de responder
    LaunchedEffect(respondida) {
        if (respondida) {
            delay(1000)
            for (i in 0..3) {
                seleccionado[i] = -1
            }
            preguntaActual++
            respondida = false
        }
    }

    if (preguntaActual < preguntas.size) {

        val partes = preguntas[preguntaActual].split("|")

        val pregunta = partes[0]
        val respuestas = partes.subList(1, 5)
        val correcta = partes[5].toInt()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                pregunta,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )

            for (j in 0..1) {
                Row {
                    for (i in 0..1) {

                        val boton = j * 2 + i

                        Button(
                            onClick = {

                                if (!respondida) {
                                    seleccionado[boton]++;
                                    if (boton == correcta){
                                        seleccionado[boton]++;
                                    }
                                    else{
                                        seleccionado[correcta]=1;
                                    }

                                    // Marca que ya se ha respondido
                                    respondida = true
                                }
                            },colors = ButtonDefaults.buttonColors(
                                containerColor = when (seleccionado[boton]) {
                                    1 -> Color(0xFF246227)
                                    0 -> Color(0xFFBB291F)
                                    else -> Color(0xFF6750A4)
                                }
                            ),
                            modifier = Modifier
                                .padding(5.dp)
                                .size(150.dp, 80.dp)
                        ) {
                            Text(
                                respuestas[boton],
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

    } else {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "¡No quedan más preguntas!",
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
GameTheme {
Greeting("Android")
}
}