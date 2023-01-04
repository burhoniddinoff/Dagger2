package com.example.dagger2.network

import com.example.dagger2.model.UserDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<UserDTO>

}