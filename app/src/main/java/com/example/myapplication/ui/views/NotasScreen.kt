package com.example.myapplication.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Bienvenido a tus notas") }) }
    ) { padding ->
        var nota by remember { mutableStateOf("") }
        val notaList = remember { mutableStateListOf<String>() }

        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = nota,
                onValueChange = { nota = it },
                label = { Text("Nota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (nota.isNotBlank()) {
                        notaList.add(nota)
                        nota = ""
                    }

                }
            ) {Text("Guardar nota") }
            Spacer(Modifier.height(16.dp))

            Text("Tus notas guardadas:")
            notaList.forEach {
                nota -> Text("* $nota")
            }
        }
    }
}