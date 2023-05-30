package com.example.todo.ui.home.todo_list.update

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.database.TodoDatabase
import com.example.todo.database.model.Constansts
import com.example.todo.database.model.clearTimeFromDate
import com.example.todo.ui.home.todo_list.TodoListFragment
import com.google.android.material.textfield.TextInputLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class UpdateTodoActivity : AppCompatActivity() {


    lateinit var update_Title_Layout: TextInputLayout
    lateinit var update_Details_Layout: TextInputLayout
    lateinit var update_Title_Text: EditText
    lateinit var update_Details_Text: EditText
    lateinit var update_Date: TextView
    lateinit var save_Changes: Button

    var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_todo)

        initViews()
        update_Date.setOnClickListener {

            showDataPicker()
        }


        save_Changes.setOnClickListener {

            if (vaidtadeTextInputsLayout()) {

                var new_Title: String = update_Title_Layout.editText?.text.toString()
                var new_Details: String = update_Details_Layout.editText?.text.toString()
                var date: Long = calendar.clearTimeFromDate().time.time




                update_Todo_Informtion(new_Title, new_Details, date)
                finish()
                TodoListFragment.calendarView.selectedDate = CalendarDay.today()
            }
        }
    }


    private fun update_Todo_Informtion(
        newTitle: String,
        newDetails: String,
        date: Long,

        ) {

        var position_Of_Todo: Int = intent.getIntExtra(Constansts.TASK_POSITION, 0)

        //Change UI Information
        TodoListFragment.todosList[position_Of_Todo].ttile = newTitle
        TodoListFragment.todosList[position_Of_Todo].details = newDetails
        TodoListFragment.todosList[position_Of_Todo].date = date


        //Change DataBase Infomtion
        TodoDatabase.getInstance(this).getTodoDao()
            .updateTodo(TodoListFragment.todosList[position_Of_Todo])

        TodoListFragment.todo_Adapter.reload_Adapter(TodoListFragment.todosList)

        Toast.makeText(this, "updated successfully", Toast.LENGTH_LONG).show()
    }

    private fun showDataPicker() {

        var datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                update_Date.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()

    }

    fun vaidtadeTextInputsLayout(): Boolean {

        var isVaild: Boolean = true

        if (update_Title_Layout.editText?.text.toString().isBlank()) {

            update_Title_Layout.error = "Please Enter Title"
            isVaild = false
        } else {

            update_Title_Layout.error = null
        }

        if (update_Details_Layout.editText?.text.toString().isBlank()) {

            update_Details_Layout.error = "Please Enter Details"
            isVaild = false
        } else {

            update_Details_Layout.error = null
        }

        return isVaild
    }


    private fun initViews() {

        update_Title_Layout = findViewById(R.id.update_Title_Layout)
        update_Details_Layout = findViewById(R.id.update_Details_Layout)
        update_Title_Text = findViewById(R.id.update_Title)
        update_Details_Text = findViewById(R.id.update_details)
        update_Date = findViewById(R.id.update_Date)
        save_Changes = findViewById(R.id.save)



        update_Date.setText(
            "" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " /" +
                    calendar.get(Calendar.YEAR)
        )
    }


}