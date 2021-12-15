package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.TodoDatabase

class TodoListFragment : Fragment() {
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
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        getTodosFromDB()
    }

    var todosAdapter = TodosAdapter()
    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter = todosAdapter
    }

    fun getTodosFromDB() {
        val data = TodoDatabase.getInstance(requireContext().applicationContext)
            .todoDao()
            .getAllTodo()
        todosAdapter.changeData(data)
    }

    fun refreshData() {
        getTodosFromDB()
    }
}