package com.example.mynotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("id")
    val id : String = "0",
    @Expose
    @SerializedName("title")
    val title : String?,
    @Expose
    @SerializedName("content")
    val note : String?,
    @Expose
    @SerializedName("date")
    val date : String?,
    @Expose
    @SerializedName("id_room")
    val idRoom: Int

) : Parcelable
