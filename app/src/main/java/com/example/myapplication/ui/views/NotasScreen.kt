package com.example.myapplication.ui.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.AppState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(navController: NavController, appState: AppState) {
    var nota by remember {mutableStateOf("")}

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text("Bienvenido a tus notas")
                },
                actions = {
                    // Agrega la opcion de salir
                    TextButton(onClick = {
                        appState.logOut()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }) {
                        Text("Salir",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold)
                    }
                }

            ) }
    ) { padding ->
        // nota by remember { mutableStateOf("") }
        // val notaList = remember { mutableStateListOf<String>() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding((padding))
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = nota,
                onValueChange = { nota = it },
                label = { Text("Escribe una nota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    if (nota.isNotBlank()) {
                       // notaList.add(nota)
                        appState.agregarNotas(nota)
                        nota = ""
                    }
                }
            ) {
                Text("Guardar nota")
            }
            Spacer(Modifier.height(16.dp))

            Text("Notas Guardadas:")
            LazyColumn { //Obtengo las notas almacenadas y las cargo en pantalla
                itemsIndexed(appState.obtenerNotas()) { index, n ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("• $n")
                        //Agrego la opción de eliminar una nota del listado
                        TextButton(onClick = { appState.borrarNotas(index) }) {
                            Text("Borrar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}