package com.dicoding.medvault

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.RetrofitClient
import com.dicoding.medvault.data.network.datamodel.SavePatientRequest
import com.dicoding.medvault.data.network.datamodel.UpdatePatientRequest
import com.dicoding.medvault.databinding.ActivityAddPatientBinding
import com.dicoding.medvault.model.Patient
import com.dicoding.medvault.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPatientBinding
    private var patient: Patient? = null
    private var lastDate: String? = null

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        patient = intent.getParcelableExtra("patient")
        if (patient != null) {
            binding.etNama.setText(patient!!.name)
            binding.etNIK.setText(patient!!.nik)
            binding.etTanggalLahir.setText(patient!!.dateOfBirth)
            lastDate = patient!!.dateOfBirth
            binding.etUmur.setText(patient!!.age)
            binding.etAlamat.setText(patient!!.address)
            if (patient!!.gender == "Perempuan") {
                binding.rgGender.check(R.id.rbFemale)
            } else {
                binding.rgGender.check(R.id.rbMale)
            }
            binding.etPhoneNumber.setText(patient!!.phoneNumber)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            val name = binding.etNama.text.toString()
            val nik = binding.etNIK.text.toString()
            val birthDate = binding.etTanggalLahir.text.toString()
            val age = binding.etUmur.text.toString()
            val address = binding.etAlamat.text.toString()
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                binding.rbMale.id -> "Laki-laki"
                binding.rbFemale.id -> "Perempuan"
                else -> ""
            }
            val phoneNumber = binding.etPhoneNumber.text.toString()

            if (validateInput(name, nik, birthDate, age, address, gender, phoneNumber)) {
                if (patient == null) {
                    savePatientData(name, nik, birthDate, age, address, gender, phoneNumber)
                } else {
                    editPatientData(name, nik, birthDate, age, address, gender, phoneNumber)
                }
            }
        }

        binding.etTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun validateInput(
        name: String, nik: String, birthDate: String, age: String,
        address: String, gender: String, phoneNumber: String
    ): Boolean {
        if (name.isEmpty() || nik.isEmpty() || birthDate.isEmpty() || age.isEmpty() ||
            address.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()
        ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun savePatientData(
        name: String, nik: String, birthDate: String, age: String,
        address: String, gender: String, phoneNumber: String
    ) {
        lifecycleScope.launch {
            try {
                binding.loading.visibility = View.VISIBLE

                val response = apiService.savePatient(
                    SavePatientRequest(
                        name = name,
                        nik = nik,
                        tanggalLahir = birthDate,
                        alamat = address,
                        jenisKelamin = gender,
                        umur = age,
                        nomorHp = phoneNumber,
                    )
                )

                binding.loading.visibility = View.GONE

                Toast.makeText(
                    this@AddPatientActivity,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (ex: Exception) {
                Toast.makeText(this@AddPatientActivity, ex.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editPatientData(
        name: String, nik: String, birthDate: String, age: String,
        address: String, gender: String, phoneNumber: String
    ) {
        lifecycleScope.launch {
            try {
                binding.loading.visibility = View.VISIBLE

                val response = apiService.updatePatient(
                    id = patient!!.id,
                    request = UpdatePatientRequest(
                        nama = name,
                        nik = nik,
                        tanggalLahir = birthDate,
                        alamat = address,
                        jenisKelamin = gender,
                        umur = age,
                        nomorHp = phoneNumber,
                    )
                )

                binding.loading.visibility = View.GONE

                Toast.makeText(
                    this@AddPatientActivity,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (ex: Exception) {
                binding.loading.visibility = View.GONE
                Toast.makeText(this@AddPatientActivity, ex.getErrorMessage(), Toast.LENGTH_SHORT).show()
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
            binding.etTanggalLahir.setText(selectedDate)
            lastDate = selectedDate
        }, year, month, day).show()
    }
}
