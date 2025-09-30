package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.views.LoginScreen
import com.example.myapplication.ui.views.NotasScreen
import com.example.myapplication.ui.views.RegistroScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    //primera pagina para inciar la aplicacion
    NavHost(navController = navController, startDestination = "LoginScreen"){
        //definir rutas
        composable("LoginScreen") { LoginScreen(navController) }
        composable("RegisterScreen") { RegistroScreen(navController) }
        composable("NotasScreen") { NotasScreen(navController) }
    }

}
