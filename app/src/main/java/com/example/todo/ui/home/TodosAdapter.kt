package com.example.todo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.model.Todo

class TodosAdapter(var items: List<Todo>? = null) :
    RecyclerView.Adapter<TodosAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title.text = item?.name
        holder.desc.text = item?.description
    }

    fun changeData(newItems: List<Todo>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val markAsDone: ImageView = itemView.findViewById(R.id.mark_as_done)
    }


}