package com.example.game

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game.ui.theme.GameTheme
@Composable
fun PantallaJuego(navController : NavController){

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
    //variable para el tiempo
    var tiempoRestante by remember {
        mutableStateOf(10)
    }
    // Espera 1 segundo después de responder
    LaunchedEffect(respondida) {
        if (respondida) {
            delay(1000)
            for (i in 0..3) {
                seleccionado[i] = -1
            }
            preguntaActual++
            tiempoRestante = 10
            respondida = false
        }
    }
    //evento del temporizador, me daba errores al intentar hacerlo de una
    LaunchedEffect(preguntaActual, respondida) {
        if (!respondida) {
            while (tiempoRestante > 0) {
                delay(1000L) // Espera 1 segundo
                tiempoRestante--
            }
            if (tiempoRestante == 0) {
                val partes = preguntas[preguntaActual].split("|")
                val correcta = partes[5].toInt()

                seleccionado[correcta] = 1
                respondida = true
            }
        }
    }

    if (preguntaActual < preguntas.size) {

        val partes = preguntas[preguntaActual].split("|")

        val pregunta = partes[0]
        val respuestas = partes.subList(1, 5)
        val correcta = partes[5].toInt()
        val imagen = partes[6]

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    when (imagen) {
                        "alfonso" -> R.drawable.alfonso
                        "dafoe" -> R.drawable.dafoe
                        "josh" -> R.drawable.josh
                        else -> R.drawable.alfonso
                    }
                ),
                contentDescription = imagen
            )

            Text(
                pregunta,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Tiempo: $tiempoRestante",
                color = if (tiempoRestante <= 3) Color.Red else Color.Unspecified,
                modifier = Modifier.padding(bottom = 16.dp)
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