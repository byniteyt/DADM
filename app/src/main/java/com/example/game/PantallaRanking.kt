package com.example.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.User
import com.example.game.data.UserDatabase
import kotlinx.coroutines.launch

@Composable
fun PantallaRanking(navController: NavController, nombreUsuario: String) {
    val context = LocalContext.current

    val repository = remember {
        OfflineUsersRepository(
            UserDatabase.getDataBase(context).userDao()
        )
    }

    val usuarios by repository.getRanking()
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ranking de jugadores",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jugador", style = MaterialTheme.typography.titleMedium)
            Text("Puntos", style = MaterialTheme.typography.titleMedium)
        }

        HorizontalDivider()

        LazyColumn {
            itemsIndexed(usuarios) { index, usuario ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${index + 1}. ${usuario.userName}")

                    Text("${usuario.points}")
                }

                HorizontalDivider()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("Inicio/$nombreUsuario") {
                    popUpTo("Ranking/$nombreUsuario") {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al menú de inicio")
        }
    }
}