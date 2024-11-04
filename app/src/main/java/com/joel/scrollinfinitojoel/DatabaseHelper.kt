package com.joel.scrollinfinitojoel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase que representa una tarea.
 *
 * @property id Identificador único de la tarea.
 * @property contenido Descripción o contenido de la tarea.
 */
data class Task(val id: Long, val contenido: String)

/**
 * DatabaseHelper es una clase de ayuda para gestionar la base de datos SQLite de tareas.
 * Se encarga de la creación y actualización de la base de datos, así como de operaciones CRUD en la tabla de tareas.
 *
 * @param context Contexto de la aplicación.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private const val DATABASE_NAME = "tasks.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos
        private const val TABLA_TAREAS = "tasks" // Nombre de la tabla de tareas
        private const val COLUMN_ID = "id" // Nombre de la columna de ID
        private const val COLUMN_CONTENIDO = "contenido" // Nombre de la columna de contenido
    }

    /**
     * Crea la tabla de tareas en la base de datos cuando se llama por primera vez.
     *
     * @param db Instancia de la base de datos SQLite.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_TAREAS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CONTENIDO TEXT)"
        db.execSQL(createTable)
    }

    /**
     * Actualiza la base de datos en caso de un cambio de versión, eliminando la tabla existente
     * y recreándola para reflejar los cambios en el esquema.
     *
     * @param db Instancia de la base de datos SQLite.
     * @param oldVersion Número de la versión anterior de la base de datos.
     * @param newVersion Número de la nueva versión de la base de datos.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val eliminar = "DROP TABLE IF EXISTS $TABLA_TAREAS"
        db.execSQL(eliminar)
        onCreate(db)
    }

    /**
     * Añade una nueva tarea a la base de datos.
     *
     * @param contenido Texto que representa la descripción de la tarea.
     * @return El ID de la tarea recién insertada, o -1 si la operación falla.
     */
    fun addTarea(contenido: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONTENIDO, contenido)
        }
        return db.insert(TABLA_TAREAS, null, values)
    }

    /**
     * Obtiene todas las tareas almacenadas en la base de datos.
     *
     * @return Una lista mutable de tareas (Task) que contiene todas las tareas de la base de datos.
     */
    fun getTodasTasks(): MutableList<Task> {
        val listaTasks = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLA_TAREAS"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val task_value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENIDO))
            val task = Task(id, task_value)
            listaTasks.add(task)
        }
        cursor.close()
        return listaTasks
    }

    /**
     * Elimina una tarea de la base de datos.
     *
     * @param idTask ID de la tarea que se desea eliminar.
     */
    fun deleteTarea(idTask: Long) {
        val db = writableDatabase
        val whereClauses = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTask.toString())
        db.delete(TABLA_TAREAS, whereClauses, whereArgs)
    }
}
