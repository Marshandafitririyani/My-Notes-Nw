package com.example.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.crocodic.core.data.CoreDao
import retrofit2.http.PATCH

@Dao
interface UserDao : CoreDao<User> {
    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Query("SELECT * FROM User WHERE idRoom = 1")
    fun getUser() : LiveData<User>

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE idRoom + 1)")
    suspend fun isLogin(): Boolean

}

