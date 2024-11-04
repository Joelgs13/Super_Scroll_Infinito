package com.joel.scrollinfinitojoel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para representar y administrar un elemento de tarea en un RecyclerView.
 * Muestra la información de cada tarea y proporciona una opción para marcarla como completada.
 *
 * @param view La vista correspondiente al layout de un elemento de tarea, que contiene la tarea y un botón de finalización.
 */
class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)

    /**
     * Rellena la vista del elemento con los datos de la tarea y establece el comportamiento
     * del botón para marcar la tarea como completada.
     *
     * @param task Texto que representa el contenido de la tarea.
     * @param onItemDone Función callback que se invoca cuando se marca la tarea como completada.
     * La función recibe como parámetro la posición actual del elemento en el adaptador.
     */
    fun render(task: String, onItemDone: (Int) -> Unit) {
        tvTask.text = task
        ivTaskDone.setOnClickListener { onItemDone(adapterPosition) }
    }
}
