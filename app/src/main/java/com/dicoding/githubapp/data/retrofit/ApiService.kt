package com.dicoding.githubapp.data.retrofit

import com.dicoding.githubapp.data.response.DetailUserResponse
import com.dicoding.githubapp.data.response.ItemsItem
import com.dicoding.githubapp.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}