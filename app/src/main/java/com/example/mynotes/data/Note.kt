package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    @Expose
    @SerializedName("id")
    val id : Int = 0,
    @Expose
    @SerializedName("title")
    val title : String?,
    @Expose
    @SerializedName("content")
    val note : String?,
    @Expose
    @SerializedName("date")
    val date : String?,
//    @Expose
//    @SerializedName("content")
//    val content : String?,
    @Expose
    @SerializedName("id_room")
    val idRoom: Int

)
