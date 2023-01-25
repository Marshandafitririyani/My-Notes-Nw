package com.example.mynotes.api

import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): String

     @FormUrlEncoded
     @POST("User")
     suspend fun register(
         @Field("name") name: String?,
         @Field("email") email: String?,
         @Field("password") password: String?
     ): String

     @FormUrlEncoded
     @POST("note/")
     suspend fun createNote(
         @Field("title") title: String?,
         @Field("content") content: String?
     ): String

     @FormUrlEncoded
     @PATCH("user/profile")
     suspend fun updateProfile(
     @Field("name") name: String?,
     @Field("email") email: String?
//     @Field("photo") photo: String?
     ): String


     //regiono Token
     @GET("user/get-token")
     suspend fun getToken(): String

     @GET("token/renew")
     suspend fun getRenewToken(): String

//profil
     @FormUrlEncoded
     @POST("user/profile")
     suspend fun profile(
    @Field("name") name: String?,
    @Field("email") email: String?,
    @Field("photo") photo: String?
     ): String




 }