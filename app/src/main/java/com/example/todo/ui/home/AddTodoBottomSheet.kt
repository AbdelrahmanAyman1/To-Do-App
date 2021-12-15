package com.example.todo.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.todo.R
import com.example.todo.database.TodoDatabase
import com.example.todo.database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTodoBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    var onAddFinishListener: OnAddFinishListener? = null

    interface OnAddFinishListener {
        fun onFinish()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        showDate()
    }

    val calendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private fun showDate() {
        dateTV.text = simpleDateFormat.format(Date(calendar.timeInMillis))
    }

    lateinit var dateTV: TextView
    lateinit var addTodoBtn: Button
    lateinit var titleTV: TextView
    lateinit var desTV: TextView

    private fun initView() {
        dateTV = requireView().findViewById(R.id.date_text_view)
        addTodoBtn = requireView().findViewById(R.id.add_todo_btn)
        titleTV = requireView().findViewById(R.id.title_edit_text)
        desTV = requireView().findViewById(R.id.description_edit_text)

        dateTV.setOnClickListener {
            showDatePacker()
        }
        addTodoBtn.setOnClickListener {

            val title = titleTV.text.toString()
            val desc = desTV.text.toString()
            if (validate(title, desc)) {
                var todo = Todo(
                    name = title, description = desc,
                    date = Date(calendar.timeInMillis), isDone = false
                )
                TodoDatabase.getInstance(requireContext().applicationContext)
                    .todoDao()
                    .addTodo(todo)
                onAddFinishListener?.onFinish()
                dismiss()
            }
        }
    }

    fun validate(title: String, desc: String): Boolean {
        var isValid = true
        if (title.isBlank()) {
            titleTV.error = "Please enter title"
            isValid = false
        } else {
            titleTV.error = null
        }

        if (desc.isBlank()) {
            desTV.error = "Please enter description"
            isValid = false
        } else {
            desTV.error = null
        }
        return isValid
    }


    private fun showDatePacker() {

        val datePacker = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    picker: DatePicker?,
                    year: Int,
                    month: Int,
                    dayOfMonth: Int
                ) {
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.YEAR, year)
                    showDate()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePacker.show()
    }
}