package com.example.todo.ui.home.todo_list.add
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.todo.R
import com.example.todo.database.TodoDatabase
import com.example.todo.database.model.Todo
import com.example.todo.database.model.clearTimeFromDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AddTodoBottomSheet : BottomSheetDialogFragment() {

    lateinit var selectDate: TextView
    lateinit var title_Layout: TextInputLayout
    lateinit var details_Layout: TextInputLayout
    lateinit var add_Task: TextView
    var calendar: Calendar = Calendar.getInstance()
    lateinit var title_Of_Task: EditText
    lateinit var details_Of_Task: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }


    private fun insertTodo(title: String, details: String) {

        var todo: Todo =
            Todo(
                ttile = title,
                details = details,
                date = calendar.clearTimeFromDate().time.time
            )

        Log.e("date insert ", "" + calendar.clearTimeFromDate().time.time)

        TodoDatabase.getInstance(requireContext().applicationContext).getTodoDao().insertTodo(todo)

        Toast.makeText(requireContext(), "Added Sucessfully", Toast.LENGTH_LONG).show()

        onTodoAdded?.onTaskAdded()

        dismiss()
    }

    var onTodoAdded: onTaskAddedSuccessfully? = null

    interface onTaskAddedSuccessfully {

        fun onTaskAdded()
    }

    private fun initViews() {
        selectDate = requireView().findViewById(R.id.select_Data)
        title_Layout = requireView().findViewById(R.id.title_Layout)
        details_Layout = requireView().findViewById(R.id.details_Layout)
        add_Task = requireView().findViewById(R.id.add_Task_Button)
        title_Of_Task = requireView().findViewById(R.id.task_Title)
        details_Of_Task = requireView().findViewById(R.id.task_Details)



        selectDate.setText(
            "" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1)
                    + "/" + calendar.get(Calendar.DAY_OF_MONTH)
        )

        selectDate.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })

        add_Task.setOnClickListener(View.OnClickListener {

            if (vaildateForm()) {


                var title_Of_Task: String = title_Layout.editText?.text.toString()
                var details_Of_Task: String = details_Layout.editText?.text.toString()

                insertTodo(title_Of_Task, details_Of_Task)
            }
        })
    }

    fun vaildateForm(): Boolean {

        var isVaild: Boolean = true

        if (title_Layout.editText?.text.toString().isBlank()) {

            title_Layout.error = "Please Enter Title"
            isVaild = false
        } else {

            title_Layout.error = null
        }


        if (details_Layout.editText?.text.toString().isBlank()) {

            details_Layout.error = "Please Enter Description"
            isVaild = false

        } else {

            details_Layout.error = null
        }


        return isVaild
    }


    fun showDatePicker() {

        var datePicker: DatePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                selectDate.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth)
            }, calendar.get(Calendar.YEAR),
            (calendar.get(Calendar.MONTH)),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}