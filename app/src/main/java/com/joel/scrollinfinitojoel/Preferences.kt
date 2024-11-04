package com.joel.scrollinfinitojoel

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase Preferences que gestiona el almacenamiento y recuperación de datos persistentes en
 * SharedPreferences, específicamente la lista de tareas.
 *
 * @param context El contexto de la aplicación necesario para acceder a SharedPreferences.
 */
class Preferences(context: Context) {

    companion object {
        /** Nombre del archivo de preferencias compartidas. */
        const val PREFS_NAME = "myDatabase"

        /** Clave para almacenar y recuperar la lista de tareas. */
        const val TASKS = "tasks_value"
    }

    /** Instancia de SharedPreferences para manejar las preferencias. */
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Guarda la lista de tareas en SharedPreferences.
     *
     * @param tasks Lista de tareas a guardar.
     */
    fun saveTasks(tasks: List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    /**
     * Recupera la lista de tareas almacenadas en SharedPreferences.
     *
     * @return Una lista mutable de tareas guardadas, o una lista vacía si no existen.
     */
    fun getTasks(): MutableList<String> {
        return prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
    }
}
