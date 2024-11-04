package com.joel.scrollinfinitojoel

import android.app.Application

/**
 * Clase TaskApplication que extiende de Application.
 * Se utiliza para inicializar y mantener una instancia única de preferencias compartidas (Preferences)
 * en toda la aplicación.
 */
class TaskApplication : Application() {
    lateinit var bbdd: DatabaseHelper
    companion object {
        /**
         * Objeto prefs de tipo Preferences para gestionar las preferencias de la aplicación.
         * Se inicializa en el método onCreate.
         */
        lateinit var prefs: Preferences
    }

    /**
     * Método de ciclo de vida onCreate. Se llama cuando la aplicación es creada.
     * Inicializa el objeto prefs con el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        bbdd = DatabaseHelper(this)

    }
}
