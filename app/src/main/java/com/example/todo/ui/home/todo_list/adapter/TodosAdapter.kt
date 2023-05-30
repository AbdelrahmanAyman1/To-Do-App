package com.example.todo.ui.home.todo_list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.model.Todo

class TodosAdapter(var todos_List: MutableList<Todo>?) :
    RecyclerView.Adapter<TodosAdapter.TodosListViewHolder>() {


    class TodosListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title_Of_Task: TextView = itemView.findViewById(R.id.title_Of_Task)
        var details_Of_Task: TextView = itemView.findViewById(R.id.details_Of_Task)
        var delete: ImageView = itemView.findViewById(R.id.left_view)
        var task_Item: CardView = itemView.findViewById(R.id.drag_item)
        var markIsDone: ImageView = itemView.findViewById(R.id.mark_As_Done)
        var view: View = itemView.findViewById(R.id.line)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosListViewHolder {

        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)

        return TodosListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodosListViewHolder, position: Int) {


        var items: Todo = todos_List!!.get(position)

        holder.title_Of_Task.setText(items.ttile)
        holder.details_Of_Task.setText(items.details)


        holder.title_Of_Task.setTextColor(Color.parseColor("#5D9CEC"))
        holder.details_Of_Task.setTextColor(Color.BLACK)
        holder.view.setBackgroundColor(Color.parseColor("#5D9CEC"))
        holder.markIsDone.setImageResource(R.drawable.ic_check)


        if (items.isDone == true) {

            holder.title_Of_Task.setTextColor(Color.GREEN)
            holder.details_Of_Task.setTextColor(Color.GREEN)
            holder.view.setBackgroundColor(Color.GREEN)
            holder.markIsDone.setImageResource(R.drawable.ic_check)
            holder.markIsDone.setBackgroundColor(Color.GREEN)
        }

        if (onTodoSwiped != null)
            holder.delete.setOnClickListener {

                onTodoSwiped?.onTodoCliked(position)
            }

        if (onTaskClicked != null) {
            holder.task_Item.setOnClickListener {


                onTaskClicked?.onTodoCliked(position)


            }


        }

        if (onTodoMarkAsDone != null) {

            holder.markIsDone.setOnClickListener {


                onTodoMarkAsDone?.onTodoCliked(position)

                if (items.isDone == true) {

                    holder.title_Of_Task.setTextColor(Color.GREEN)
                    holder.details_Of_Task.setTextColor(Color.GREEN)
                    holder.view.setBackgroundColor(Color.GREEN)

                }
            }
        }

    }

    var onTodoSwiped: onTodoClickListener? = null
    var onTaskClicked: onTodoClickListener? = null
    var onTodoMarkAsDone: onTodoClickListener? = null

    interface onTodoClickListener {

        fun onTodoCliked(position: Int)
    }

    fun reload_Adapter(todos_List: MutableList<Todo>) {

        this.todos_List = todos_List
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return todos_List?.size ?: 0
    }
}