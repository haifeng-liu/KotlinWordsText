package com.haifeng.kotlinwordstext

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
class WordsRepository(con: Context) {
    private val context: Context
    private val dao: WordsDao
    private val lists: LiveData<List<Words>>

    init {
        context = con
        dao = WordsDataBase.getInstance(context).getdao()
        lists = dao.getall()
    }
     fun InsertWords(words: Words){
       Insert(dao).execute(words)
    }

    fun ClearWords(){
        Clear(dao).execute()
    }
    fun DeleteitemById(id: Int){
        Deletebyid(dao).execute(id)
    }

    fun getlist():LiveData<List<Words>>{
        return dao.getall()
    }

    fun getdata(english_word:String):LiveData<List<Words>>{
        return dao.getdata(english_word)
    }

    fun update(words: Words){
        Updates(dao).execute(words)
    }



    /**
     * 添加数据
     */
    private class Insert(daos: WordsDao) : AsyncTask<Words, Void, Void>() {
        val dao: WordsDao

        init {
            dao = daos
        }

        override fun doInBackground(vararg params: Words?): Void? {
            dao.Insert(params)
            return null
        }
    }

    /**
     * 清空数据
     */
    private class Clear(daos: WordsDao) : AsyncTask<Void, Void, Void>() {
        val dao: WordsDao

        init {
            dao = daos
        }

        override fun doInBackground(vararg params: Void?): Void? {
            dao.clear()
            return null
        }
    }

    /**
     * 根据id删除数据
     */
    private  class Deletebyid(daos: WordsDao):AsyncTask<Int,Void,Void>(){

        val dao:WordsDao
        init {
            dao=daos
        }

        override fun doInBackground(vararg id: Int?): Void ?{
            id[0]?.let { dao.deletebyid(it) }
            return null
        }
    }

    /**
     * 编辑数据
     */
    private class Updates(daos:WordsDao):AsyncTask<Words,Void,Void>(){
        val dao:WordsDao
        init {
            dao=daos
        }

        override fun doInBackground(vararg params: Words?): Void ?{
            dao.update(params)
            return null
        }
    }

}