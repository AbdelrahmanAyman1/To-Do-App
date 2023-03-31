package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.TodoDatabase
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class TodoListFragment : Fragment() {
    lateinit var calendar: MaterialCalendarView
    lateinit var pleaseHolder: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pleaseHolder = view.findViewById(R.id.please_holder)
        initRecyclerView()
        initCalendarView()

    }

    fun initCalendarView() {
        val cal = Calendar.getInstance()
        cal.clear(Calendar.HOUR)
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)
        date = cal.time
        getTodosFromDB(date)
        calendar = requireView().findViewById(R.id.calendarView)
        calendar.selectedDate = CalendarDay.from(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        )
        calendar.setOnDateChangedListener { widget, date, selected ->
            reloadTodoList(
                date.day,
                date.month - 1,
                date.year
            )
        }
    }

    lateinit var date: Date
    private fun reloadTodoList(day: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.clear(Calendar.HOUR)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        date = calendar.time
        getTodosFromDB(date)

    }

    override fun onResume() {
        super.onResume()
        getTodosFromDB(date)
    }

    var todosAdapter = TodosAdapter()
    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter = todosAdapter
    }

    fun getTodosFromDB() {
        val data = TodoDatabase.getInstance(requireContext().applicationContext)
            .todoDao()
            .getTodosByData(date)
        pleaseHolder.isVisible = data.isEmpty()
        todosAdapter.changeData(data)
    }

    fun refreshData() {
        getTodosFromDB(date)
    }
}