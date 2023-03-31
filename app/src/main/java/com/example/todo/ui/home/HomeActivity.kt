package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.R.layout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var addActionButton: FloatingActionButton
    lateinit var listFragment: TodoListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        addActionButton = findViewById(R.id.fab)
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.ic_list) {
                listFragment = TodoListFragment()
                pushFragment(listFragment)
            } else {
                pushFragment(SittingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        bottomNavigationView.selectedItemId = R.id.ic_list
        addActionButton.setOnClickListener {
            showAddBottomSheet()
        }

    }

    private fun showAddBottomSheet() {
        val bottomSheetFragment = AddTodoBottomSheet()
        bottomSheetFragment.show(supportFragmentManager, "")
        bottomSheetFragment.onAddFinishListener = object :
            AddTodoBottomSheet.OnAddFinishListener {
            override fun onFinish() = listFragment.refreshData()
        }
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}