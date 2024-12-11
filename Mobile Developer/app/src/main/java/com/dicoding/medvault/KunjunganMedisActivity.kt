package com.dicoding.medvault

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.adapter.CustomAdapter
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.datamodel.Anamnesa
import com.dicoding.medvault.data.network.datamodel.MedicsRequest
import com.dicoding.medvault.data.network.datamodel.PredictResponse
import com.dicoding.medvault.databinding.ActivityAddKunjunganMedisBinding
import com.dicoding.medvault.model.Patient
import com.dicoding.medvault.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class KunjunganMedisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddKunjunganMedisBinding
    private lateinit var namaPasienAdapter: CustomAdapter
    private var lastDate: String? = null

    @Inject
    lateinit var apiService: ApiService

    var gejala: String = ""

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val diagnose = result.data?.getParcelableArrayListExtra<PredictResponse>("diagnose")
                if (diagnose?.isNotEmpty() == true) {
                    binding.etDiagnosa.setText(diagnose.first().disease)
                }
                gejala = result.data?.getStringExtra("gejala") ?: ""
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
                    patientDate = DateUtil.convertToFormattedDate(it.updatedAt)
                )
            }
            namaPasienAdapter.updateData(patients)
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
            Triple(
                calendar2.get(Calendar.YEAR),
                calendar2.get(Calendar.MONTH),
                calendar2.get(Calendar.DAY_OF_MONTH)
            )
        } else {
            Triple(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        val datePickerDialog = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${selectedDay}-${selectedMonth + 1}-$selectedYear"
                binding.etTanggalKunjungan.setText(formattedDate)
                lastDate = formattedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun simpan() {
        lifecycleScope.launch {
            try {
                val patient = binding.spNamaPasien.selectedItem as? Patient
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
                val tindakan = binding.etTindakan.text.toString().trim()

                if (patient == null || nomorKunjungan.isEmpty() || tanggalKunjungan.isEmpty() || namaPasien.isEmpty() || sistole.isEmpty() || detakNadi.isEmpty() || suhu.isEmpty() || beratBadan.isEmpty() || tinggiBadan.isEmpty() || diagnosa.isEmpty()) {
                    Toast.makeText(
                        this@KunjunganMedisActivity,
                        "Please fill in all required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                binding.loading.isVisible = true
                val response = apiService.createMedics(
                    MedicsRequest(
                        visitNumber = nomorKunjungan,
                        visitDate = tanggalKunjungan,
                        patientName = patient.name,
                        anamnesa = Anamnesa(
                            sistoleDiastol = sistole,
                            suhu = suhu,
                            detakNadi = detakNadi,
                            beratBadan = beratBadan,
                            tinggiBadan = tinggiBadan
                        ),
                        diagnosisResult = diagnosa,
                        action = tindakan,
                        gejala = gejala
                    )
                )
                Toast.makeText(
                    this@KunjunganMedisActivity,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
                binding.loading.isVisible = false
            } catch (ex: Exception) {
                Toast.makeText(
                    this@KunjunganMedisActivity,
                    ex.message,
                    Toast.LENGTH_SHORT
                ).show()
                binding.loading.isVisible = false
            }
        }
    }
}
