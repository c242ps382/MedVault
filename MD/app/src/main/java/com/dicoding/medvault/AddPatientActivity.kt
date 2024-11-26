package com.dicoding.medvault

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.databinding.ActivityAddPatientBinding
import com.dicoding.medvault.model.Patient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class AddPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPatientBinding
    private var patient: Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        patient = intent.getParcelableExtra("patient")
        if (patient != null) {
            binding.etNama.setText(patient!!.name)
            binding.etNIK.setText(patient!!.nik)
            binding.etTanggalLahir.setText(patient!!.dateOfBirth)
            binding.etAgama.setText(patient!!.religion)
            binding.etAlamat.setText(patient!!.address)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            val name = binding.etNama.text.toString()
            val nik = binding.etNIK.text.toString()
            val birthDate = binding.etTanggalLahir.text.toString()
            val religion = binding.etAgama.text.toString()
            val address = binding.etAlamat.text.toString()
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                binding.rbMale.id -> "Male"
                binding.rbFemale.id -> "Female"
                else -> ""
            }
            val phoneNumber = binding.etPhoneNumber.text.toString()

            if (validateInput(name, nik, birthDate, religion, address, gender, phoneNumber)) {
                if (patient == null) {
                    savePatientData(name, nik, birthDate, religion, address, gender, phoneNumber)
                } else {
                    editPatientData(name, nik, birthDate, religion, address, gender, phoneNumber)
                }
            }
        }

        binding.etTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun validateInput(
        name: String, nik: String, birthDate: String, religion: String,
        address: String, gender: String, phoneNumber: String
    ): Boolean {
        if (name.isEmpty() || nik.isEmpty() || birthDate.isEmpty() || religion.isEmpty() ||
            address.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()
        ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun savePatientData(
        name: String, nik: String, birthDate: String, religion: String,
        address: String, gender: String, phoneNumber: String
    ) {
        lifecycleScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
            delay(2000)
//            binding.progressBar.visibility = View.GONE

            Toast.makeText(
                this@AddPatientActivity,
                "Patient added successfully!",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun editPatientData(
        name: String, nik: String, birthDate: String, religion: String,
        address: String, gender: String, phoneNumber: String
    ) {
        lifecycleScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
            delay(2000)
//            binding.progressBar.visibility = View.GONE

            Toast.makeText(
                this@AddPatientActivity,
                "Patient edited successfully!",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.etTanggalLahir.setText(selectedDate)
        }, year, month, day).show()
    }
}
