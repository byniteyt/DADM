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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.navigation.NavController

@Composable
fun PantallaInicio(navController : NavController){
    Column(
        modifier=Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Bienvenido a nuestro juego", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier= Modifier.height(24.dp))
        Button(onClick = {navController.navigate("Juego")}) { Text("PLAY") }

    }
}