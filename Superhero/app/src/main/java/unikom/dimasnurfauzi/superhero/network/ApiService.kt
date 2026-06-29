package unikom.dimasnurfauzi.superhero.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import unikom.dimasnurfauzi.superhero.model.SuperheroResponse

interface ApiService {
    @GET("api.php/b7e66dc152628b07ce431a91bc1f2b4f/{id}")
    fun getSuperhero(@Path("id") id: String): Call<SuperheroResponse>
}
