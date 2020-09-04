package com.haifeng.kotlinwordstext

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
class WordsViewModel(application: Application) : AndroidViewModel(application) {
    val repository:WordsRepository by lazy { WordsRepository(application) }


    /**
     * 获取全部数据
     */
    fun getlist():LiveData<List<Words>>{
        return repository.getlist()
    }

    /**
     * 按照english_word查询
     */
    fun getlistbyname(english_word:String):LiveData<List<Words>>{
        return  repository.getdata(english_word)
    }


    /**
     * 添加数据
     */
    fun insert(words: Words){
        repository.InsertWords(words)
    }

    /**
     * 清空数据
     */
    fun clear(){
        repository.ClearWords()
    }

    /**
     * 根据id删除数据
     */
    fun deleteByid(id:Int){
        repository.DeleteitemById(id)
    }

    /**
     * 编辑数据
     */
    fun update(words: Words){
        repository.update(words)
    }





}