package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.datamodel.LoginRequest
import com.dicoding.medvault.databinding.ActivityLoginBinding
import com.dicoding.medvault.model.User
import com.dicoding.medvault.pref.AppPreferences
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        binding.btnMasuk.setOnClickListener {
            val identifier = binding.etIdentifier.text.toString()
            val password = binding.etPassword.text.toString()

            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter identifier and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    binding.loading.isVisible = true
                    val loginResponse = apiService.login(
                        LoginRequest(
                            identifier = identifier,
                            password = password
                        )
                    )

                    appPreferences.saveUser(
                        User(
                            username = loginResponse.user.username,
                            email = loginResponse.user.email,
                            token = loginResponse.token,
                            id = loginResponse.user.id,
                            name = loginResponse.user.name,
                            alamat = loginResponse.user.alamat,
                            imgProfile = loginResponse.user.imgProfile.orEmpty(),
                            password = password
                        )
                    )
                    Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    binding.loading.isVisible = false
                } catch (ex: Exception) {
                    val message = when (ex) {
                        is HttpException -> {
                            try {
                                val errorBody = ex.response()?.errorBody()?.string()
                                if (errorBody != null) {
                                    val errorResponse =
                                        Gson().fromJson(errorBody, Error::class.java)
                                    errorResponse.error
                                } else {
                                    "Unknown error"
                                }
                            } catch (e: Exception) {
                                "Error parsing response: ${e.message}"
                            }
                        }

                        is CancellationException -> {
                            ""
                        }

                        else -> {
                            ex.message ?: ex.stackTraceToString()
                        }
                    }
                    binding.loading.isVisible = false
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    data class Error(
        @SerializedName("error")
        val error: String
    )
}
