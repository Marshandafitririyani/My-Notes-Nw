package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @Expose
    @SerializedName("id_room")
    val idRoom: Int,
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name : String,
    @Expose
    @SerializedName("email")
    val email : String,
    @Expose
    @SerializedName("photo")
    val photo : String?,

    //tak tambah
    @Expose
    @SerializedName("password")
    val password : String?,
    @Expose
    @SerializedName("confirm_password")
    val confirmPassword : String?,

    //tak tambah
    @Expose
    @SerializedName("title")
    val title : String?,
    @Expose
    @SerializedName("note")
    val note : String?,
    @Expose
    @SerializedName("date")
    val date : String?,
    @Expose
    @SerializedName("content")
    val content : String?






)