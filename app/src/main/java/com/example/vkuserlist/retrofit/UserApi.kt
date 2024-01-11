package com.example.vkuserlist.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/method/users.get")
    suspend fun getUserById(@Query("user_ids") userIds: String,
                    @Query("access_token") accessToken: String = VkApi.TOKEN,
                    @Query("v") version: Double = VkApi.VERSION): Response
}