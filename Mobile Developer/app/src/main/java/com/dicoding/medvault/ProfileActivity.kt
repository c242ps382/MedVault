package com.dicoding.medvault

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.databinding.ActivityProfileBinding
import com.dicoding.medvault.model.User
import com.dicoding.medvault.pref.AppPreferences
import com.dicoding.medvault.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var userData: User? = null
    private var uri: Uri? = null
    private lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var apiService: ApiService

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                this.uri = uri
                Glide.with(this)
                    .load(uri)
                    .into(binding.ivProfileImage)
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

        appPreferences = AppPreferences(this)

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
            val address = binding.etAddress.text.toString()

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                updateUser(name, email, username, password, address)
            }
        }

        loadUser()
    }

    private fun loadUser() {
        lifecycleScope.launch {
            try {
                userData = appPreferences.getUser()
                binding.etNama.setText(userData!!.name)
                binding.etEmail.setText(userData!!.email)
                binding.etUsername.setText(userData!!.username)
                binding.etAddress.setText(userData!!.alamat)
                binding.etPassword.setText(userData!!.password)
                Glide.with(this@ProfileActivity)
                    .load(userData?.imgProfile.orEmpty())
                    .error(R.drawable.ic_person_profile)
                    .into(binding.ivProfileImage)
            } catch (ex: Exception) {
                Toast.makeText(this@ProfileActivity, "Failed to get user data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateUser(
        name: String,
        email: String,
        username: String,
        password: String,
        alamat: String
    ) {
        lifecycleScope.launch {
            try {
                val nameRequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val usernameRequestBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
                val addressRequestBody = alamat.toRequestBody("text/plain".toMediaTypeOrNull())
                val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
                val passwordConfirmationRequestBody =
                    password.toRequestBody("text/plain".toMediaTypeOrNull())
                val response = apiService.updateUser(
                    id = userData!!.id,
                    name = nameRequestBody,
                    email = emailRequestBody,
                    username = usernameRequestBody,
                    alamat = addressRequestBody,
                    password = passwordRequestBody,
                    passwordConfirmation = passwordConfirmationRequestBody,
                    imgProfile = if (uri == null) {
                        null
                    } else {
                        createMultipartBodyFromUri(this@ProfileActivity, uri!!)
                    }
                )
                appPreferences.saveUser(
                    appPreferences.getUser()!!.copy(
                        username = response.user.username,
                        email = response.user.email,
                        id = response.user.id,
                        name = response.user.name,
                        alamat = response.user.alamat,
                        imgProfile = response.user.imgProfile.orEmpty(),
                        password = password,
                    )
                )
                Toast.makeText(
                    this@ProfileActivity,
                    "Profile saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@ProfileActivity,
                    ex.getErrorMessage(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createMultipartBodyFromUri(context: Context, uri: Uri): MultipartBody.Part? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "temp_image_file.jpg")

            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (input.read(buffer).also { length = it } != -1) {
                        output.write(buffer, 0, length)
                    }
                }
            }

            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)

            return MultipartBody.Part.createFormData("imgProfile", tempFile.name, requestFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
