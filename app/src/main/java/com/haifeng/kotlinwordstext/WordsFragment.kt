package com.haifeng.kotlinwordstext

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_words.*


/**
 * A simple [Fragment] subclass.
 * Use the [WordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordsFragment : Fragment() {

    lateinit var model: WordsViewModel;
    lateinit var adapter1: WordsAdapter;
    lateinit var adapter2: WordsAdapter
    lateinit var alllist: LiveData<List<Words>>
    lateinit var list: List<Words>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_words, container, false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.chance -> {
                var sha: SharedPreferences =
                    requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
                var flg: Boolean = sha.getBoolean("type", false)
                var editor: SharedPreferences.Editor = sha.edit()
                if (flg) {
                    recycler.adapter = adapter1
                    editor.putBoolean("type", false)
                } else {
                    recycler.adapter = adapter2
                    editor.putBoolean("type", true)
                }
                editor.apply()
            }
            R.id.clear -> {
                val dialog =
                    AlertDialog.Builder(requireActivity())
                dialog.setTitle("清空数据")
                dialog.setPositiveButton(
                    "确定"
                ) { dialog, which -> model.clear() }
                dialog.setNegativeButton(
                    "取消"
                ) { dialog, which -> }
                dialog.create()
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menus, menu)
        var searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.maxWidth = 500
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                var text: String = newText?.trim() ?: ""
                Log.d("TAG", "onQueryTextChange: " + text)
                alllist.removeObserver { viewLifecycleOwner }
                alllist = model.getlistbyname("%$text%")
                alllist.observe(viewLifecycleOwner, Observer {
                    var index1: Int = adapter1.itemCount
                    var index2: Int = adapter2.itemCount
                    list = it
                    if (index1 != list.size) {
                        adapter1.submitList(list)
                    }
                    if (index2 != list.size) {
                        adapter2.submitList(list)
                    }
                })

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProvider(this).get(WordsViewModel::class.java)
        adapter1 = WordsAdapter(model, requireActivity(), false)
        adapter2 = WordsAdapter(model, requireActivity(), true)
        recycler.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        var sha: SharedPreferences =
            requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        var flg: Boolean = sha.getBoolean("type", false)
        if (!flg) {
            recycler.adapter = adapter1
        } else {
            recycler.adapter = adapter2
        }
        alllist = model.getlist()
        alllist.observe(viewLifecycleOwner, Observer {
            var index1: Int = adapter1.itemCount
            var index2: Int = adapter2.itemCount
            list = it
            if (index1 != list.size) {
                if (index1 < list.size) {
                    recycler.smoothScrollBy(0, -200)
                }
                adapter1.submitList(list)
            }
            if (index2 != list.size) {
                if (index2 < list.size) {
                    recycler.smoothScrollBy(0, -200)

                }
                adapter2.submitList(list)
            }
        })
        btn_add.setOnClickListener {
            var navController: NavController = Navigation.findNavController(it)
            navController.navigate(R.id.action_wordsFragment_to_addFragment)
        }


        ItemTouchHelper(object :
            SimpleCallback(0, START or END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val words = list[viewHolder.adapterPosition]

                    model.deleteByid(words.id)
                    Snackbar.make(
                        view!!.findViewById(R.id.coord),
                        "删除一条数据",
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction("撤销") { model.insert(words) }.show()


            }
        }).attachToRecyclerView(recycler)


    }


}

