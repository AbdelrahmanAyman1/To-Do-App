package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.ui.home.settings.SittingsFragment
import com.example.todo.ui.home.todo_list.TodoListFragment
import com.example.todo.ui.home.todo_list.add.AddTodoBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var floatingAction: FloatingActionButton
    lateinit var bottomSheetDialogFragment: AddTodoBottomSheet
    var listFragment = TodoListFragment()
    var sittingsFragment = SittingsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.background = null
        floatingAction = findViewById(R.id.fab)

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
        floatingAction.setOnClickListener {
            showAddBottomSheet()
        }

    }

    private fun pushFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun showAddBottomSheet() {
        bottomSheetDialogFragment = AddTodoBottomSheet()
        bottomSheetDialogFragment.show(supportFragmentManager, "")

        bottomSheetDialogFragment.onTodoAdded =
            object : AddTodoBottomSheet.onTaskAddedSuccessfully {
                override fun onTaskAdded() {

                    if (listFragment.isVisible)
                        listFragment.get_All_Todos_From_DB_By_Day();
                }

            }

    }
}