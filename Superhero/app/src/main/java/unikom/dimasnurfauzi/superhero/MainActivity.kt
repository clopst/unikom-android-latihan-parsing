package unikom.dimasnurfauzi.superhero

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unikom.dimasnurfauzi.superhero.model.SuperheroResponse
import unikom.dimasnurfauzi.superhero.network.ApiConfig

class MainActivity : AppCompatActivity() {

    private lateinit var ivSuperhero: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvPowerstats: TextView
    private lateinit var tvBiography: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivSuperhero = findViewById(R.id.iv_superhero)
        tvName = findViewById(R.id.tv_name)
        tvPowerstats = findViewById(R.id.tv_powerstats)
        tvBiography = findViewById(R.id.tv_biography)
        progressBar = findViewById(R.id.progressBar)

        getSuperheroDetail("322")
    }

    private fun getSuperheroDetail(id: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getSuperhero(id)
        client.enqueue(object : Callback<SuperheroResponse> {
            override fun onResponse(
                call: Call<SuperheroResponse>,
                response: Response<SuperheroResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setSuperheroData(responseBody)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SuperheroResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSuperheroData(superhero: SuperheroResponse) {
        tvName.text = superhero.name
        
        val powerstats = """
            Intelligence: ${superhero.powerstats.intelligence}
            Strength: ${superhero.powerstats.strength}
            Speed: ${superhero.powerstats.speed}
            Durability: ${superhero.powerstats.durability}
            Power: ${superhero.powerstats.power}
            Combat: ${superhero.powerstats.combat}
        """.trimIndent()
        tvPowerstats.text = powerstats

        val biography = """
            Full Name: ${superhero.biography.fullName}
            Alter Egos: ${superhero.biography.alterEgos}
            Aliases: ${superhero.biography.aliases.joinToString(", ")}
            Place of Birth: ${superhero.biography.placeOfBirth}
            First Appearance: ${superhero.biography.firstAppearance}
            Publisher: ${superhero.biography.publisher}
            Alignment: ${superhero.biography.alignment}
        """.trimIndent()
        tvBiography.text = biography

        Glide.with(this)
            .load(R.drawable.ic_image)
            .into(ivSuperhero)
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
