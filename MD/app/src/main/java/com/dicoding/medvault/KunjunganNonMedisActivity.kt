package com.dicoding.medvault

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.adapter.CustomAdapter
import com.dicoding.medvault.databinding.ActivityAddKunjunganNonMedisBinding
import com.dicoding.medvault.model.Patient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KunjunganNonMedisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddKunjunganNonMedisBinding
    private lateinit var namaPasienAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKunjunganNonMedisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        setupSpinner()

        binding.btnSimpan.setOnClickListener {
            val nomorKunjungan = binding.etNomorKunjungan.text.toString()
            val tanggalKunjungan = binding.etTanggalKunjungan.text.toString()
            val keluhan = binding.etKeluhan.text.toString()
            val tindakan = binding.etTindakan.text.toString()
            val biayaPembayaran = binding.etBiayaPembayaran.text.toString()

            simulateApiCall(nomorKunjungan, tanggalKunjungan, keluhan, tindakan, biayaPembayaran)
        }

        loadPatient()
    }

    private fun setupSpinner() {
        val spinner = binding.spNamaPasien
        namaPasienAdapter = CustomAdapter(this@KunjunganNonMedisActivity)
        spinner.adapter = namaPasienAdapter
    }

    private fun loadPatient() {
        lifecycleScope.launch {
            delay(2000)
            val patients = listOf(
                Patient(id = "1", name = "John Doe", address = "123 Main St"),
                Patient(id = "2", name = "Jane Doe", address = "456 Elm St"),
                Patient(id = "3", name = "Mike Ross", address = "789 Oak St")
            )
            namaPasienAdapter.updateData(patients)
        }
    }

    private fun simulateApiCall(
        nomorKunjungan: String,
        tanggalKunjungan: String,
        keluhan: String,
        tindakan: String,
        biayaPembayaran: String
    ) {
        lifecycleScope.launch {
            delay(2000)
            Toast.makeText(
                this@KunjunganNonMedisActivity,
                "Data saved successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
