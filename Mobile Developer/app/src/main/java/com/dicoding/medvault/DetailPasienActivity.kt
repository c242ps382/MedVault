package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.RetrofitClient
import com.dicoding.medvault.databinding.ActivityDetailPasienBinding
import com.dicoding.medvault.databinding.DialogRemovePatientBinding
import com.dicoding.medvault.model.Patient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailPasienActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPasienBinding

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPasienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        val patient: Patient? = intent.getParcelableExtra("patient")

        if (patient == null) {
            Toast.makeText(
                this@DetailPasienActivity,
                "Something went wrong. Please try again",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        loadPatient(patient)

        binding.ivEdit.setOnClickListener {
            val intent = Intent(this@DetailPasienActivity, AddPatientActivity::class.java).apply {
                putExtra("patient", patient)
            }
            startActivity(intent)
        }

        binding.ivTrash.setOnClickListener {
            val dialogBinding = DialogRemovePatientBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnNo.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnKeluar.setOnClickListener {
                lifecycleScope.launch {
                    deletePatient(patient.id)
                }
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun loadPatient(patient: Patient) {
        lifecycleScope.launch {
            binding.tvNamaLengkap.text = patient.name
            binding.tvNik.text = patient.nik
            binding.tvTanggalLahir.text = patient.dateOfBirth
            binding.tvJenisKelamin.text = patient.gender
            binding.tvAlamat.text = patient.address
            binding.tvUmur.text = patient.age
            binding.tvNoHp.text = patient.phoneNumber
        }
    }

    private fun deletePatient(patientId: Int) {
        lifecycleScope.launch {
            try {
                val response = apiService.deletePatient(patientId)
                Toast.makeText(this@DetailPasienActivity, response.message, Toast.LENGTH_SHORT)
                    .show()
            } catch (ex: Exception) {
                Toast.makeText(this@DetailPasienActivity, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}
