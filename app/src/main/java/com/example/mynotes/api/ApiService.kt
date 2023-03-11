package com.example.mynotes.api

import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("user/login?expired=1")
    suspend fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): String

    @FormUrlEncoded
    @POST("user")
    suspend fun register(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): String

    //membuat Profil
    @FormUrlEncoded
    @PATCH("user/profile")
    suspend fun updateProfile(
        @Field("name") name: String?
    ): String //<-

    @Multipart
    @PATCH("user/profile")
    suspend fun updateProfileWithPhoto(
        @Part("name") name: String?,
        @Part photo: MultipartBody.Part?
    ): String //<-

    //menampilkan Profil
    @PATCH("user/profile")
    suspend fun profile(): String //<-

    //membuat note
    @FormUrlEncoded
    @POST("note/")
    suspend fun createNote(
        @Field("title") title: String?,
        @Field("content") content: String?
    ): String //<-

    //menampilkan Note
    @GET("note/")
    suspend fun note(): String //<-


    //updateNote
    @FormUrlEncoded
    @PATCH("note/{id}")
    suspend fun updateNote(
        @Path("id") id: String?,
        @Field("title") title: String?,
        @Field("content") content: String?
    ): String //<-

    //delet note
    @DELETE("note/{id}")
    suspend fun deletNote(
        @Path("id") id: String?
    ): String //<-


    // Token
    @GET("user/get-token")
    suspend fun getToken(): String

    @GET("user/refresh-token")
    suspend fun getRenewToken(): String


}