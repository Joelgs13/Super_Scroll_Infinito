package com.joel.scrollinfinitojoel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para un elemento de tarea en un RecyclerView.
 * Administra las vistas de cada tarea y el botón de finalización.
 *
 * @param view La vista correspondiente al layout de un elemento de tarea.
 */
class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)

    /**
     * Rellena la vista del elemento con los datos de la tarea y establece el comportamiento
     * del botón de finalización.
     *
     * @param task Texto de la tarea a mostrar.
     * @param onItemDone Callback que se ejecuta cuando se marca la tarea como completada,
     * pasando la posición del elemento.
     */
    fun render(task: String, onItemDone: (Int) -> Unit) {
        tvTask.text = task
        ivTaskDone.setOnClickListener { onItemDone(adapterPosition) }
    }
}
