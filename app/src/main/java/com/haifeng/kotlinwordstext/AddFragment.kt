package com.haifeng.kotlinwordstext

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    lateinit var model: WordsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(WordsViewModel::class.java)

        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!edit_english.getText().toString().isEmpty() && !edit_chinese.getText()
                        .toString().isEmpty()
                ) {
                    btn_submit.setEnabled(true)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

        edit_chinese.addTextChangedListener(watcher)
        edit_english.addTextChangedListener(watcher)
        btn_submit.setOnClickListener {
            var english = edit_english.text.toString().trim()
            var chinese = edit_chinese.text.toString().trim()
            var word: Words = Words( english, chinese, false)
            model.insert(word)
            var navController: NavController = Navigation.findNavController(it)
            navController.navigateUp()

        }
    }

    companion object {
        // using a hardcoded value for simplicity
        const val USER_ID = 1
    }

}