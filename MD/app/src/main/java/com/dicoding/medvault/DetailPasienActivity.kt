package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.databinding.ActivityDetailPasienBinding
import com.dicoding.medvault.databinding.DialogRemovePatientBinding
import com.dicoding.medvault.model.Patient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailPasienActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPasienBinding

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
                    deletePatient(patient?.id ?: return@launch)
                }
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun loadPatient(patient: Patient) {
        lifecycleScope.launch {
            delay(2000)
            binding.tvNamaLengkap.text = patient.name
            binding.tvNik.text = patient.nik
            binding.tvTanggalLahir.text = patient.dateOfBirth
            binding.tvJenisKelamin.text = patient.gender
            binding.tvAlamat.text = patient.address
            binding.tvAgama.text = patient.religion
            binding.tvNoHp.text = patient.phoneNumber
        }
    }

    private suspend fun deletePatient(patientId: String) {
        delay(2000)
        Toast.makeText(
            this@DetailPasienActivity,
            "Patient deleted successfully!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}
