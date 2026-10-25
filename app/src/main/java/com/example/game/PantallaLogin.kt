package com.example.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.User
import com.example.game.data.UserDatabase
import com.example.game.data.UserRepository
import kotlinx.coroutines.launch

@Composable
fun PantallaLogin(navController: NavController, userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Inserta usuario") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mensaje,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de usuario") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    val usuario = userRepository.getUser(nombre)

                    if (usuario != null) {
                        navController.navigate("Inicio/${usuario.userName}")
                    } else {
                        mensaje = "No hay usuario"
                    }
                }
            }
        ) {
            Text("Iniciar sesión")
        }
        Button(
            onClick = {
                scope.launch {
                    val usuario = userRepository.getUser(nombre)
                    if (nombre.trim().isBlank()) {
                        mensaje = "Introduce un nombre"
                        return@launch
                    }
                    if (usuario == null) {
                        val newUser = User(
                            userName = nombre,
                            bestScore = 0.0,
                            points = 0
                        )
                        userRepository.insertUser(newUser)
                        navController.navigate("Inicio/${newUser.userName}")
                    } else {
                        mensaje = "No hay usuario"
                    }
                }
            }
        ) {
            Text("Registrar usuario")
        }
    }
}