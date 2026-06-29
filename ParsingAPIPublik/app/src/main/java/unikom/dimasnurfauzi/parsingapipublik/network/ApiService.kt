package unikom.dimasnurfauzi.parsingapipublik.network

import retrofit2.Call
import retrofit2.http.*
import unikom.dimasnurfauzi.parsingapipublik.model.UserResponse

interface ApiService {
    @GET("users")
    fun getListUsers(@Query("page") page: String): Call<UserResponse>
}