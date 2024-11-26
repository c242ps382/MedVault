package com.dicoding.medvault

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivProfileImage.setImageURI(uri)
                binding.ivProfileImage.setPadding(0, 0, 0, 0)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getImageFromGallery.launch("image/*")
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.flProfileImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getImageFromGallery.launch("image/*")
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getImageFromGallery.launch("image/*")
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

        binding.btnSimpan.setOnClickListener {
            val name = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                simulateApiCall(name, email, username, password, phoneNumber)
            }
        }
    }

    private fun simulateApiCall(
        name: String,
        email: String,
        username: String,
        password: String,
        phoneNumber: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val isSuccess = true

            withContext(Dispatchers.Main) {
                if (isSuccess) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Profile saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Failed to save profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
