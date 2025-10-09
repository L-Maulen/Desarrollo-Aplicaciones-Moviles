package com.example.myapplication.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.AppState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, appState: AppState) {
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var confirmPasswrd by remember { mutableStateOf(value = "") }
    var error by remember { mutableStateOf(value = "") }

    Scaffold (
        topBar = { TopAppBar(title = { Text("Registro Usuario") }) }
    ) {padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text("Email")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Contraseña")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))

            if(error.isNotEmpty()){
                Text(error, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            //faltan validaciones
            Button(
                onClick = {
                    when{
                        email.isBlank() || password.isBlank() || confirmPasswrd.isBlank() ->
                            error = "Todos los campos son obligatorios"
                        !email.contains("@") ->
                            error = "Email no válido"
                        password.length < 4 ->
                            error = "La contraseña debe tener al menos 4 caracteres"
                        password != confirmPasswrd ->
                            error = "Las contraseñas no coinciden"
                        //Con los datos validados llamo al registrarUsuario()
                        //si el usuario es nuevo lo graba y retorna true
                        !appState.registrarUsuario(email, password) ->
                            error = "El usuario ya existe"
                        else -> {
                            error = ""
                            navController.navigate("login")
                        }
                    }
               },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Registrarse")}

        }
    }
}