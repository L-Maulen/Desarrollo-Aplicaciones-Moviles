package com.example.myapplication.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class Usuario(val email: String, val password: String){

}
class AppState(private val dataStore: DataStoreManager){
    val usuarios = mutableStateListOf<Usuario>()
    var usuarioConectado: Usuario? = null

    val notasUsuario = mutableStateMapOf<String, SnapshotStateList<String>>()
    // notasPorUsuario


    private var scope = CoroutineScope(Dispatchers.IO)

    // Carga de datos iniciales
    fun cargarDatos(){
        scope.launch {
            val users = dataStore.getUsers().first()
            val notes = dataStore.getNotes().first()
            // .first() -> FUncion lambda que retorna listas

            usuarios.clear()
            usuarios.addAll(users)

            notasUsuario.clear()
            notes.forEach { (k, v) -> notasUsuario[k] = v.toMutableStateList() }

        }
    }

    // Registro de usuario
    fun registrarUsuario(email: String, password: String): Boolean{
        if (usuarios.any{it.email == email}) return false
        val nuevoUsuario = Usuario(email, password)

        usuarios.add(nuevoUsuario)
        guardarUsuario()
        return true
    }

    // Guardar notas
    fun agregarNotas(nota: String){
        val email = usuarioConectado?.email?: return
        val notas = notasUsuario.getOrPut(email){
            mutableStateListOf()
        }
        notas.add(nota)
        guardarNotas()

    }


    // Guardar en DataStore
    private fun guardarUsuario(){
        scope.launch {
            dataStore.saveUsers(usuarios)
        }
    }

    //
    private fun guardarNotas(){
        scope.launch {
            dataStore.saveNotes(notasUsuario)
        }
    }

    // Login
    fun login(email: String, password: String): Boolean{
        val user = usuarios.find { it.email == email && it.password == password}

        return if (user != null){
            usuarioConectado = user
            true
        } else false

    }

    // Logout
    fun logOut(){
        usuarioConectado = null
    }

    // Obtener notas
    fun obtenerNotas(): List<String>{
        val email = usuarioConectado?.email ?: return emptyList()

        return notasUsuario[email] ?: mutableStateListOf()

    }

    // Borrar botas de a una (usuario logeado)
    fun borrarNotas(index: Int){
        val email = usuarioConectado?.email ?: return
        notasUsuario[email]?.let {
            if (index in it.indices){
                it.removeAt(index)
                guardarNotas()
            }
        }
    }
}