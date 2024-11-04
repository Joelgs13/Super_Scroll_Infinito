package com.joel.scrollinfinitojoel

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity es la interfaz principal de la aplicación. Administra la lista de tareas,
 * permitiendo a los usuarios agregar y eliminar tareas, reproduciendo sonidos respectivos para estas acciones.
 */
class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask: Button
    lateinit var etTask: EditText
    lateinit var rvTask: RecyclerView
    lateinit var adapter: TaskAdapter

    var tasks = mutableListOf<Task>() //cambia String x Task
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Método de ciclo de vida onCreate. Configura la actividad e inicializa los componentes de la interfaz
     * de usuario llamando a initUi().
     *
     * @param savedInstanceState contiene los datos previamente guardados si la actividad fue cerrada y reabierta.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    /**
     * Inicializa la interfaz de usuario configurando las vistas, los listeners y el RecyclerView.
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Configura el RecyclerView con un LinearLayoutManager y establece el adaptador de tareas.
     * Carga las tareas guardadas previamente en la vista.
     */
    private fun initRecyclerView() {
        tasks = (application as TaskApplication).bbdd.getTodasTasks()
        rvTask.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTask.adapter = adapter

        // Configuración de Swipe to Delete para eliminar tareas al deslizar
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTask(viewHolder.adapterPosition) // Llama al método para eliminar la tarea en la posición deslizada
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvTask) // Asigna el helper al RecyclerView
    }

    /**
     * Elimina una tarea de la lista, actualiza el RecyclerView y la base de datos.
     * Reproduce un sonido al eliminar la tarea.
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
     * Establece el listener para el botón de añadir tarea, llamando a addTask() al hacer clic.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener { addTask() }
    }

    /**
     * Añade una nueva tarea a la lista, actualiza el RecyclerView y la base de datos,
     * y reproduce un sonido al añadir la tarea.
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
            playNullTaskSound()
        }
    }

    /**
     * Reproduce un sonido al añadir una tarea y libera el MediaPlayer cuando se completa la reproducción.
     */
    private fun playAddTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.add_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Reproduce un sonido al eliminar una tarea y libera el MediaPlayer cuando se completa la reproducción.
     */
    private fun playDeleteTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Reproduce un sonido cuando se intenta añadir una tarea vacía y libera el MediaPlayer cuando se completa.
     */
    private fun playNullTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.nulo)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Inicializa las vistas, asignando los elementos de la interfaz a sus variables correspondientes.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTasks)
    }
}
