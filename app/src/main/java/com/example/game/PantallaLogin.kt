package com.example.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.UserDatabase
import com.example.game.data.UserRepository
import com.example.game.ui.UserDetailViewModelFactory

@Composable
fun PantallaLogin(navController : NavController){
    val context = LocalContext.current
    val dataBase = OfflineUsersRepository(UserDatabase.getDataBase(context).userDao())
    var nombre by remember { mutableStateOf("")}
        Column {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Introduce tu nombre") }
        )
    }
    Column(
        modifier=Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var mensaje by remember {
            mutableStateOf("Inserta usuario")
        }
        Text(mensaje, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier= Modifier.height(24.dp))
        val scope = rememberCoroutineScope()
        Button(onClick = {
            scope.launch {
                val usuario = dataBase.getUser(nombre)

                if (usuario != null) {
                    navController.navigate("Inicio")
                } else {
                    mensaje = "No hay usuario"
                }
            }
        }) {
            Text("PLAY")
        }

    }
}