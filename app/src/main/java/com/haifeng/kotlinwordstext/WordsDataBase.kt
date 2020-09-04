package com.haifeng.kotlinwordstext

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
@Database(entities = [Words::class], version = 1, exportSchema = false)
abstract class WordsDataBase : RoomDatabase() {
    abstract fun getdao(): WordsDao

    companion object {
        @Volatile
        private var INSTANCE: WordsDataBase? = null

        fun getInstance(context: Context): WordsDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, WordsDataBase::class.java, "data")
                .build()

    }


}