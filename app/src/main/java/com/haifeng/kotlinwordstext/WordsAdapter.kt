package com.haifeng.kotlinwordstext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_words.view.*
import kotlinx.android.synthetic.main.item_words2.view.*

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
class WordsAdapter(viewmodel:WordsViewModel,con:Context,flg: Boolean) : ListAdapter<Words, WordsViewHolder>(DIFFCALLBACK) {
    private var f: Boolean
    private var context:Context;
    private val model:WordsViewModel
    init {
        f = flg
        context=con
        model=viewmodel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val holder1 = WordsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        )
        val holder2 = WordsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_words2, parent, false)
        )
        holder1.itemView.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            var w:Words= holder1.itemView.tv_english.tag as Words
            if (isChecked) {
                holder1.itemView.switch1.text="显示"
                holder1.itemView.tv_chinese.visibility=View.GONE
                w.chick=true
                model.update(w)
            } else {
                holder1.itemView.tv_chinese.visibility=View.VISIBLE
                holder1.itemView.switch1.text="隐藏"
                w.chick=false
                model.update(w)
            }
        }

        holder2.itemView.switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            var w:Words= holder2.itemView.tv_english2.tag as Words
            if (isChecked) {
                holder1.itemView.switch1.text="显示"
                holder2.itemView.tv_chinese2.visibility=View.GONE
                w.chick=true
                model.update(w)
            } else {
                holder2.itemView.switch2.text="隐藏"
                holder2.itemView.tv_chinese2.visibility=View.VISIBLE
                w.chick=false
                model.update(w)
            }
        }
        if (!f) {
            return holder1
        } else {
            return holder2
        }
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        var w: Words = getItem(position)
        if (!f) {
            holder.itemView.tv_english.tag = w
            holder.itemView.tv_id.text = w.id.toString()
            holder.itemView.tv_chinese.text = w.chinese_message
            holder.itemView.tv_english.text = w.english_word
            holder.itemView.switch1.isChecked = w.chick
            if (w.chick){
                holder.itemView.tv_chinese.visibility=View.GONE
            }else{
                holder.itemView.tv_chinese.visibility=View.VISIBLE
            }
        } else {
            holder.itemView.tv_english2.tag = w
            holder.itemView.tv_id2.text = w.id.toString()
            holder.itemView.tv_english2.text = w.english_word
            holder.itemView.tv_chinese2.text = w.chinese_message
            holder.itemView.switch2.isChecked = w.chick
            if (w.chick){
                holder.itemView.tv_chinese2.visibility=View.GONE
            }else{
                holder.itemView.tv_chinese2.visibility=View.VISIBLE
            }
        }
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<Words>() {
        override fun areItemsTheSame(oldItem: Words, newItem: Words): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Words, newItem: Words): Boolean {
            return oldItem.id == newItem.id
        }

    }
}

class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView);