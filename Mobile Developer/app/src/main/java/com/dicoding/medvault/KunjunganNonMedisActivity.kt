package com.dicoding.medvault

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.adapter.CustomAdapter
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.RetrofitClient
import com.dicoding.medvault.data.network.datamodel.CreateNonMedicsRequest
import com.dicoding.medvault.databinding.ActivityAddKunjunganNonMedisBinding
import com.dicoding.medvault.model.Patient
import com.dicoding.medvault.util.DateUtil
import com.dicoding.medvault.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class KunjunganNonMedisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddKunjunganNonMedisBinding
    private lateinit var namaPasienAdapter: CustomAdapter
    private var lastDate: String? = null

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKunjunganNonMedisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        setupSpinner()

        binding.etTanggalKunjungan.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSimpan.setOnClickListener {
            val patient = binding.spNamaPasien.selectedItem as Patient
            val nomorKunjungan = binding.etNomorKunjungan.text.toString()
            val tanggalKunjungan = binding.etTanggalKunjungan.text.toString()
            val keluhan = binding.etKeluhan.text.toString()
            val tindakan = binding.etTindakan.text.toString()
            val biayaPembayaran = binding.etBiayaPembayaran.text.toString()

            save(patient.name, nomorKunjungan, tanggalKunjungan, keluhan, tindakan, biayaPembayaran)
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
            val remotePatients = apiService.getPatients()
            val patients = remotePatients.data.map {
                Patient(
                    id = it.id,
                    name = it.name,
                    address = it.alamat,
                    phoneNumber = it.nomorHp,
                    dateOfBirth = it.tanggalLahir,
                    gender = it.jenisKelamin,
                    age = it.umur,
                    nik = it.nik,
                    patientDate = DateUtil.convertToFormattedDateDefault(it.updatedAt)
                )
            }
            namaPasienAdapter.updateData(patients)
        }
    }

    private fun save(
        patientName: String,
        nomorKunjungan: String,
        tanggalKunjungan: String,
        keluhan: String,
        tindakan: String,
        biayaPembayaran: String
    ) {
        lifecycleScope.launch {
            try {
                val response = apiService.createNonMedics(
                    CreateNonMedicsRequest(
                        visitNumber = nomorKunjungan,
                        visitDate = tanggalKunjungan,
                        patientName = patientName,
                        complaint = keluhan,
                        action = tindakan,
                        paymentCost = biayaPembayaran.toInt()
                    )
                )
                Toast.makeText(
                    this@KunjunganNonMedisActivity,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@KunjunganNonMedisActivity,
                    ex.getErrorMessage(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val (year, month, day) = if (lastDate?.isNotBlank() == true) {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val calendar2 = Calendar.getInstance()
            try {
                val defaultDate = dateFormat.parse(lastDate!!)
                calendar2.time = defaultDate ?: Date()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Triple(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH))
        } else {
            Triple(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        }

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
            binding.etTanggalKunjungan.setText(selectedDate)
            lastDate = selectedDate
        }, year, month, day).show()
    }
}
