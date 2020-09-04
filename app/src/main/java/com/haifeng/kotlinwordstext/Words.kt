package com.haifeng.kotlinwordstext

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *创建人：Liuhaifeng
 *时间：2020/9/3
 *描述：
 */
@Entity
data class Words(


    @ColumnInfo(name = "english_word")
    var english_word: String,

    @ColumnInfo(name = "chinese_message")
    var chinese_message: String,

    @ColumnInfo(name = "chick")
    var chick: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}