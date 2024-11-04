package com.joel.scrollinfinitojoel

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/**
 * MainActivity es la actividad principal de la aplicación. Maneja la interfaz de usuario para
 * la lista de tareas, permitiendo agregar y eliminar tareas y reproducir sonidos al realizar
 * estas acciones.
 */
class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask: Button
    lateinit var etTask: EditText
    lateinit var rvTask: RecyclerView
    lateinit var adapter: TaskAdapter

    var tasks = mutableListOf<Task>() //cambia String x Task
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Método de ciclo de vida onCreate. Configura la actividad y llama a initUi() para inicializar la interfaz de usuario.
     *
     * @param savedInstanceState contiene los datos previamente guardados de la actividad si fue cerrada y reabierta.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    /**
     * Inicializa la interfaz de usuario llamando a los métodos initView(), initListeners() e initRecyclerView().
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Configura el RecyclerView con un LinearLayoutManager y el adaptador de tareas, además carga las tareas guardadas.
     */
    private fun initRecyclerView() {
        tasks = (application as TaskApplication).bbdd.getTodasTasks()
        rvTask.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTask.adapter = adapter
    }

    /**
     * Elimina una tarea de la lista y actualiza el RecyclerView y las preferencias. Reproduce un sonido de eliminación.
     *
     * @param position la posición de la tarea a eliminar en la lista.
     */
    private fun deleteTask(position: Int) {
        val tarea = tasks[position]
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        (application as TaskApplication).bbdd.deleteTarea(tarea.id)

        playDeleteTaskSound()
    }

    /**
     * Establece el listener para el botón de añadir tarea, que llama a addTask() cuando se presiona.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener { addTask() }
    }

    /**
     * Añade una nueva tarea a la lista de tareas, actualiza el RecyclerView y las preferencias, y reproduce un sonido.
     */
    private fun addTask() {

        val taskContenido = etTask.text.toString().trim()

        if (taskContenido.isNotEmpty()) {
            val bbdd = (application as TaskApplication).bbdd
            val taskId = bbdd.addTarea(taskContenido)
            val nuevaTarea = Task(id = taskId, contenido = taskContenido)
            tasks.add(nuevaTarea)

            adapter.notifyDataSetChanged()
            etTask.setText("")
            playAddTaskSound()
        } else {
            etTask.error = "Escribe una tarea!"
            playDeleteTaskSound()
        }


    }

    /**
     * Reproduce un sonido al añadir una tarea, liberando el MediaPlayer después de que se complete la reproducción.
     */
    private fun playAddTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.add_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Reproduce un sonido al eliminar una tarea, liberando el MediaPlayer después de que se complete la reproducción.
     */
    private fun playDeleteTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Inicializa las vistas asignando los elementos de la interfaz a sus respectivas variables.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTasks)
    }
}
