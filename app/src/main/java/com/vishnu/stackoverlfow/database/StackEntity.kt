package com.vishnu.stackoverlfow.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stackoverflow")
class StackEntity(
    @PrimaryKey(autoGenerate = false) var id :Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "displayName") val displayName:String,
    @ColumnInfo(name="viewCount")val viewCount:String,
    @ColumnInfo(name="answerCount")val answerCount:String,
    @ColumnInfo(name = "creationDate")val creationDate :String,
    @ColumnInfo(name = "profileImage")val profileImage :String,
    @ColumnInfo(name = "link")val link :String

    )