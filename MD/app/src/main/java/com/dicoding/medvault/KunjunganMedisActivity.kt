package com.dicoding.medvault

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.adapter.CustomAdapter
import com.dicoding.medvault.databinding.ActivityAddKunjunganMedisBinding
import com.dicoding.medvault.model.Patient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class KunjunganMedisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddKunjunganMedisBinding
    private lateinit var namaPasienAdapter: CustomAdapter

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val label = result.data?.getStringExtra("LABEL")
                binding.etDiagnosa.setText(label.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKunjunganMedisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.etTanggalKunjungan.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnGejala.setOnClickListener {
            val intent = Intent(this, AddDiagnosaActivity::class.java)
            startForResult.launch(intent)
        }

        binding.btnSimpan.setOnClickListener {
            simpan()
        }

        loadPatient()
    }

    private fun setupSpinner() {
        val spinner = binding.spNamaPasien
        namaPasienAdapter = CustomAdapter(this@KunjunganMedisActivity)
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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                binding.etTanggalKunjungan.setText(formattedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun simpan() {
        lifecycleScope.launch {
            val tanggalKunjungan = binding.etTanggalKunjungan.text.toString()
            val nomorKunjungan = binding.etNomorKunjungan.text.toString().trim()
            val selectedPatient = binding.spNamaPasien.selectedItem as? Patient
            val namaPasien = selectedPatient?.name.toString()
            val sistole = binding.etSistole.text.toString().trim()
            val suhu = binding.etSuhu.text.toString().trim()
            val detakNadi = binding.etDetakNadi.text.toString().trim()
            val beratBadan = binding.etBeratBadan.text.toString().trim()
            val tinggiBadan = binding.etTinggiBadan.text.toString().trim()
            val diagnosa = binding.etDiagnosa.text.toString().trim()

            if (nomorKunjungan.isEmpty() || tanggalKunjungan.isEmpty() || namaPasien.isEmpty() || sistole.isEmpty() || detakNadi.isEmpty() || suhu.isEmpty() || beratBadan.isEmpty() || tinggiBadan.isEmpty() || diagnosa.isEmpty()) {
                Toast.makeText(
                    this@KunjunganMedisActivity,
                    "Please fill in all required fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            Toast.makeText(
                this@KunjunganMedisActivity,
                "Data saved successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
