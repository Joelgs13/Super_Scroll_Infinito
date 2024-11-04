package com.joel.scrollinfinitojoel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que gestiona y muestra una lista de tareas.
 * Cada elemento de la lista representa una tarea, permitiendo marcarla como realizada.
 *
 * @param tasks Lista mutable de objetos [Task] que representa las tareas a mostrar.
 * @param onItemDone Callback que se ejecuta cuando se realiza una acción en una tarea, con la posición de la tarea en la lista.
 */
class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemDone: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para una tarea, inflando el layout correspondiente.
     *
     * @param parent El ViewGroup al que el nuevo ViewHolder se añadirá después de ser creado.
     * @param viewType Tipo de vista del nuevo ViewHolder. Este adaptador utiliza un único tipo de vista.
     * @return Una nueva instancia de [TaskViewHolder] configurada para un elemento de tarea.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve la cantidad de tareas en la lista.
     *
     * @return El número de elementos en la lista de tareas.
     */
    override fun getItemCount() = tasks.size

    /**
     * Vincula un objeto [Task] a su ViewHolder para mostrar los datos de la tarea en una posición específica.
     *
     * @param holder ViewHolder que contendrá los datos de la tarea.
     * @param position Posición de la tarea en la lista de tareas.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position].contenido, onItemDone)
    }
}
