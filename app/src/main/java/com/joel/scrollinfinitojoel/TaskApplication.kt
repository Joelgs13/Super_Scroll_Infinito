package com.joel.scrollinfinitojoel

import android.app.Application

/**
 * Clase [TaskApplication] que extiende de [Application].
 * Se utiliza para inicializar y mantener una instancia única de la base de datos [DatabaseHelper],
 * permitiendo el acceso a los datos de la aplicación en cualquier parte.
 */
class TaskApplication : Application() {

    /**
     * Instancia de [DatabaseHelper] que proporciona acceso a la base de datos de tareas.
     */
    lateinit var bbdd: DatabaseHelper

    /**
     * Método de ciclo de vida [onCreate], que se llama cuando la aplicación se inicia.
     * Inicializa el objeto [bbdd] para acceder a la base de datos en el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        bbdd = DatabaseHelper(this)
    }
}
