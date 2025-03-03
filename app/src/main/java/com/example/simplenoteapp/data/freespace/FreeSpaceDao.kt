package com.example.simplenoteapp.data.freespace

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FreeSpaceDao {
    @Query("SELECT * FROM free_space WHERE id= 1 LIMIT 1")
    fun getFreeSpace(): LiveData<FreeSpace?>

    @Query("SELECT * FROM free_space WHERE id= 1 LIMIT 1")
    suspend fun getFreeSpaceSync(): FreeSpace?  // 非同期でデータを取得するための関数

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(freeSpace: FreeSpace)

    @Update
    suspend fun update(freeSpace: FreeSpace)
}