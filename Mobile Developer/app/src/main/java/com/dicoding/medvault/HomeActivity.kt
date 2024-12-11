package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dicoding.medvault.databinding.ActivityHomeBinding
import com.dicoding.medvault.fragment.HomeFragment
import com.dicoding.medvault.fragment.KunjunganFragment
import com.dicoding.medvault.fragment.PasienFragment
import com.dicoding.medvault.pref.AppPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        loadFragment(HomeFragment())

        setupBottomNavigation()
        setupDrawerActions()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.nav_pasien -> {
                    loadFragment(PasienFragment())
                    true
                }

                R.id.nav_kunjungan -> {
                    loadFragment(KunjunganFragment())
                    true
                }

                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commit()
    }

    private fun setupDrawerActions() {
        binding.ivClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.llEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.llLogout.setOnClickListener {
            showLogoutDialog()
        }

        Glide.with(this)
            .load("https://placehold.co/")
            .error(R.drawable.ic_person_profile)
            .into(binding.imageProfile)
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun showLogoutDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_logout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()

        val btnNo: Button = dialogView.findViewById(R.id.btnNo)
        val btnKeluar: Button = dialogView.findViewById(R.id.btnKeluar)

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        btnKeluar.setOnClickListener {
            alertDialog.dismiss()
            logout()
        }

        alertDialog.show()
    }

    private fun logout() {
        appPreferences.clearUser()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
