package com.joel.scrollinfinitojoel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que muestra una lista de tareas.
 * Se encarga de crear y enlazar los elementos de la lista con su vista.
 *
 * @param tasks Lista de tareas a mostrar.
 * @param onItemDone Callback que se ejecuta cuando se marca una tarea como realizada, con la posición de la tarea.
 */
class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemDone: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Crea un nuevo ViewHolder inflando el layout de un elemento de tarea.
     *
     * @param parent El ViewGroup al que el nuevo ViewHolder se añadirá tras ser creado.
     * @param viewType Tipo de vista del nuevo ViewHolder. No se utiliza en este adaptador.
     * @return Una instancia de TaskViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve el número de tareas en la lista.
     *
     * @return El tamaño de la lista de tareas.
     */
    override fun getItemCount() = tasks.size

    /**
     * Vincula un elemento de tarea a su ViewHolder para mostrar los datos de la tarea en la posición dada.
     *
     * @param holder ViewHolder que mostrará los datos de la tarea.
     * @param position Posición de la tarea en la lista.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position].contenido, onItemDone)
    }
}
