package com.example.todo.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.google.android.material.textfield.TextInputLayout

class SittingsFragment : Fragment() {
    var items_Drop_Down: Array<String> = arrayOf("Dark", "Light")
    lateinit var textInputLayout: TextInputLayout
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sittings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoCompleteTextView = requireView().findViewById(R.id.autoCompleteTextView)
        textInputLayout = requireView().findViewById(R.id.textInputLayout)

        adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.dark_text_input_item_settings,
            items_Drop_Down
        )

        autoCompleteTextView.setAdapter(adapter)


        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->

            if (parent.getItemAtPosition(position).toString() == "Light") {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else if (parent.getItemAtPosition(position).toString() == "Dark") {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }
        }

    }
}