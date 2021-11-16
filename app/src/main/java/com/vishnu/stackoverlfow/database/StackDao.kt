package com.vishnu.stackoverlfow.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Entity

@Dao
interface StackDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: com.vishnu.stackoverlfow.database.StackEntity)


    @Query("Select * from stackoverflow order by id ASC")
    fun getStackdata(): LiveData<List<StackEntity>>



}