package com.joel.scrollinfinitojoel

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joel.scrollinfinitojoel.TaskAdapter
import com.joel.scrollinfinitojoel.TaskApplication.Companion.prefs

class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask: Button
    lateinit var etTask: EditText
    lateinit var rvTask: RecyclerView
    lateinit var adapter: TaskAdapter


    var tasks = mutableListOf<String>()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi(){
        initView()
        initListeners()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTask.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTask.adapter = adapter
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        prefs.saveTasks(tasks)
        playDeleteTaskSound()
    }

    private fun initListeners() {
        btnAddTask.setOnClickListener{addTask()}
    }

    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
        tasks.add(taskToAdd)
        adapter.notifyDataSetChanged()
        etTask.setText("")
        prefs.saveTasks(tasks)
        playAddTaskSound() // Reproducir el sonido al añadir tarea
    }

    private fun playAddTaskSound() {
        // Liberar el MediaPlayer si ya se está usando
        mediaPlayer?.release()

        // Inicializar el MediaPlayer con el sonido del recurso raw
        mediaPlayer = MediaPlayer.create(this, R.raw.add_task_sound)
        mediaPlayer?.start() // Iniciar la reproducción del sonido

        // Liberar el MediaPlayer al finalizar el audio
        mediaPlayer?.setOnCompletionListener {
            it.release()
        }
    }

    private fun playDeleteTaskSound() {
        // Liberar el MediaPlayer si ya se está usando
        mediaPlayer?.release()

        // Inicializar el MediaPlayer con el sonido de eliminar tarea
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_task_sound)
        mediaPlayer?.start() // Iniciar la reproducción del sonido

        // Liberar el MediaPlayer al finalizar el audio
        mediaPlayer?.setOnCompletionListener {
            it.release()
        }
    }

    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTasks)
    }
}