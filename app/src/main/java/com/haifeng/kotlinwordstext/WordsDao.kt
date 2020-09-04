package com.haifeng.kotlinwordstext

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
@Dao
interface WordsDao{
    @Insert
    fun Insert(words: Array<out Words?>)

    @Query("SELECT * FROM words ORDER BY ID DESC")
    fun getall():LiveData<List<Words>>

    @Query("DELETE  FROM words")
    fun clear()

    @Query("DELETE FROM words WHERE ID=:id")
    fun deletebyid(id:Int)

    @Query("SELECT * FROM words WHERE english_word LIKE :parent ORDER BY ID DESC")
    fun getdata(parent:String):LiveData<List<Words>>

    @Update
    fun update(words: Array<out Words?>)
}