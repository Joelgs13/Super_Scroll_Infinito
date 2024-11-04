package com.joel.scrollinfinitojoel


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



data class Task(val id: Long, val contenido: String)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {
    companion object {
        private const val DATABASE_NAME = "tasks.db"
        private const val DATABASE_VERSION = 1
        private const val TABLA_TAREAS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CONTENIDO = "contenido"
    }



    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $DATABASE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CONTENIDO TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val eliminar = "DROP TABLE IF EXISTS $TABLA_TAREAS"
        db.execSQL(eliminar)
        onCreate(db)
    }

    fun addTarea(contenido: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONTENIDO, contenido)
        }
        db.close()
        return db.insert(TABLA_TAREAS, null, values)
    }


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
        db.close()
        return listaTasks
    }

    fun deleteTarea(idTask: Long) {
        val db = writableDatabase
        val whereClauses = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTask.toString())
        //eliminar el objeto
        db.delete(TABLA_TAREAS, whereClauses, whereArgs)
        db.close()
    }

}
