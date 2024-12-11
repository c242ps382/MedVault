package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.datamodel.RegisterRequest
import com.dicoding.medvault.databinding.ActivityRegisterBinding
import com.dicoding.medvault.model.User
import com.dicoding.medvault.pref.AppPreferences
import com.dicoding.medvault.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val address = binding.etAddress.text.toString()
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (fullName.isEmpty() || address.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    binding.loading.isVisible = true
                    val response = apiService.register(
                        RegisterRequest(
                            name = fullName,
                            email = email,
                            password = password,
                            username = username,
                            alamat = address
                        )
                    )
                    appPreferences.saveUser(
                        User(
                            username = username,
                            email = email,
                            token = response.token,
                            id = response.user.id,
                            name = response.user.name,
                            alamat = response.user.alamat,
                            imgProfile = response.user.imgProfile.orEmpty(),
                            password = password
                        )
                    )
                    Toast.makeText(
                        this@RegisterActivity,
                        "Account registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    binding.loading.isVisible = false
                } catch (ex: Exception) {
                    binding.loading.isVisible = false
                    Toast.makeText(this@RegisterActivity, ex.getErrorMessage(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
