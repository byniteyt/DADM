package com.example.game

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
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.game.data.UserRepository

@Composable
fun PantallaJuego(
    navController: NavController,
    userRepository: UserRepository,
    nombreUsuario: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val preguntas = remember {
        context.resources.openRawResource(R.raw.preguntas)
            .bufferedReader()
            .readText()
            .split(";")
            .filter { it.isNotBlank() }
    }

    var points by remember {
        mutableStateOf(0)
    }

    var preguntaActual by remember {
        mutableStateOf(0)
    }

    val seleccionado = remember {
        mutableStateListOf(-1, -1, -1, -1)
    }

    var respondida by remember {
        mutableStateOf(false)
    }

    // Variable para el tiempo
    var tiempoRestante by remember {
        mutableStateOf(10)
    }

    // Espera un segundo después de responder y avanza
    LaunchedEffect(respondida) {
        if (respondida) {
            delay(1000)

            if (preguntaActual < preguntas.size - 1) {
                for (i in 0..3) {
                    seleccionado[i] = -1
                }

                preguntaActual++
                tiempoRestante = 10
                respondida = false
            } else {
                // Finalizar el juego tras la última pregunta
                preguntaActual = preguntas.size
            }
        }
    }

    // Temporizador de cada pregunta
    LaunchedEffect(preguntaActual, respondida) {
        if (!respondida && preguntaActual < preguntas.size) {
            while (tiempoRestante > 0 && !respondida) {
                delay(1000L)
                tiempoRestante--
            }

            if (
                tiempoRestante == 0 &&
                !respondida &&
                preguntaActual < preguntas.size
            ) {
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
                        "donkeykong" -> R.drawable.donkeykong
                        "cuphead" -> R.drawable.cuphead
                        "cloud" -> R.drawable.cloud
                        "carl" -> R.drawable.carl
                        "matt" -> R.drawable.matt
                        "zombie" -> R.drawable.zombie
                        "dedede" -> R.drawable.dedede
                        "tennis" -> R.drawable.tennis
                        "nintendo" -> R.drawable.nintendo
                        "mario" -> R.drawable.mario
                        "angrybirds" -> R.drawable.angrybirds
                        "sims" -> R.drawable.sims
                        "soniccolors" -> R.drawable.soniccolors
                        "littlenightmares" -> R.drawable.littlenightmares
                        "dreamcast" -> R.drawable.dreamcast
                        "gba" -> R.drawable.gba
                        "steamdeck" -> R.drawable.steamdeck
                        "lanza" -> R.drawable.lanza
                        "alice" -> R.drawable.alice
                        "sonic" -> R.drawable.sonic
                        else -> R.drawable.nintendo
                    }
                ),
                contentDescription = imagen,
                modifier = Modifier.size(150.dp).padding(bottom = 16.dp)
            )

            Text(
                text = pregunta,
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
                                    if (boton == correcta) {
                                        points += 1 + tiempoRestante
                                        seleccionado[boton] = 1
                                    } else {
                                        seleccionado[boton] = 0
                                        seleccionado[correcta] = 1
                                    }

                                    // Marcar pregunta como respondida
                                    respondida = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
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
                                text = respuestas[boton],
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    } else {
        LaunchedEffect(nombreUsuario, points) {
            val usuario = userRepository.getUser(nombreUsuario)

            if (usuario != null) {
                val usuarioActualizado = usuario.copy(
                    points = usuario.points + points,
                    bestScore = maxOf(
                        usuario.bestScore,
                        points.toDouble()
                    )
                )

                userRepository.updateUser(usuarioActualizado)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡No quedan más preguntas!\nHas conseguido $points puntos",
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    navController.navigate("Inicio/$nombreUsuario") {
                        popUpTo("Juego/$nombreUsuario") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Volver al menú de inicio")
            }
        }
    }
}