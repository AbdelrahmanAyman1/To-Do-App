package com.example.todo.ui.home.todo_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.TodoDatabase
import com.example.todo.database.model.Constansts
import com.example.todo.database.model.Todo
import com.example.todo.database.model.clearTimeFromDate
import com.example.todo.ui.home.todo_list.adapter.TodosAdapter
import com.example.todo.ui.home.todo_list.update.UpdateTodoActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar

class TodoListFragment : Fragment() {

    lateinit var todos_Recycler_View: RecyclerView
    lateinit var pleaseHolder: LinearLayout
    var calendar: Calendar = Calendar.getInstance()

    companion object {
        lateinit var todosList: MutableList<Todo>
        var todo_Adapter: TodosAdapter = TodosAdapter(null)
        lateinit var calendarView: MaterialCalendarView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pleaseHolder = view.findViewById(R.id.please_holder)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        get_All_Todos_From_DB_By_Day()

    }

    fun get_All_Todos_From_DB_By_Day() {

        todosList = TodoDatabase.getInstance(requireContext()).getTodoDao()
            .getTodoByDay(calendar.clearTimeFromDate().time.time)

        Log.e("date time ", "" + calendar.clearTimeFromDate().time.time)

        todo_Adapter.reload_Adapter(todosList)
        pleaseHolder.isVisible = todosList.isEmpty()

    }

    fun deleteTodoFromDB() {

        todo_Adapter.onTodoSwiped = object : TodosAdapter.onTodoClickListener {
            override fun onTodoCliked(position: Int) {

                TodoDatabase.getInstance(requireContext()).getTodoDao()
                    .deleteTodo(todosList[position])
                todosList.removeAt(position)
                todo_Adapter.reload_Adapter(todosList)
                pleaseHolder.isVisible = true
                Toast.makeText(requireContext(), "deleted successfully", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun moveToUpdateActivity() {

        todo_Adapter.onTaskClicked = object : TodosAdapter.onTodoClickListener {
            override fun onTodoCliked(position: Int) {

                var intent: Intent = Intent(requireContext(), UpdateTodoActivity::class.java)

                intent.putExtra(Constansts.TASK_POSITION, position)

                startActivity(intent)

            }
        }
    }

    fun markAsDone() {

        todo_Adapter.onTodoMarkAsDone = object : TodosAdapter.onTodoClickListener {
            override fun onTodoCliked(position: Int) {

                todosList[position].isDone = true
                TodoDatabase.getInstance(requireContext()).getTodoDao()
                    .updateTodo(todosList[position])
                todo_Adapter.notifyItemChanged(position)
                todo_Adapter.reload_Adapter(todosList)
            }

        }
    }


    private fun initViews() {

        calendarView = requireView().findViewById(R.id.calendarView)
        todos_Recycler_View = requireView().findViewById(R.id.todos_Recycler_View)
        calendarView.selectedDate = CalendarDay.today()
        todos_Recycler_View.adapter = todo_Adapter

        calendarView.setOnDateChangedListener { widget, calendarDay, selected ->


            calendar.set(Calendar.DAY_OF_MONTH, calendarDay.day)
            calendar.set(Calendar.MONTH, calendarDay.month - 1)
            calendar.set(Calendar.YEAR, calendarDay.year)


            get_All_Todos_From_DB_By_Day()


        }
        deleteTodoFromDB()
        moveToUpdateActivity()
        markAsDone()

    }
}