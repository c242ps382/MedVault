package com.dicoding.medvault.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.medvault.AddPatientActivity
import com.dicoding.medvault.DetailPasienActivity
import com.dicoding.medvault.adapter.PatientAdapter
import com.dicoding.medvault.databinding.DialogRemovePatientBinding
import com.dicoding.medvault.databinding.FragmentDataPasienBinding
import com.dicoding.medvault.model.Patient
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PasienFragment : Fragment() {

    private var _binding: FragmentDataPasienBinding? = null
    private val binding get() = _binding!!
    private var adapter = PatientAdapter()
    private var currentList: List<Patient> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPasienBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivBack.setOnClickListener {
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddPatientActivity::class.java)
            startActivity(intent)
        }

        binding.etSearch.addTextChangedListener { text ->
            val filteredList = filterPatients(text.toString())
            adapter.submitList(filteredList)
        }

        binding.ivDate.setOnClickListener {
            showDatePicker()
        }

        adapter = PatientAdapter()
        binding.rvPatients.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPatients.adapter = adapter

        adapter.onEditClicked = { patient ->
            val intent = Intent(requireContext(), AddPatientActivity::class.java).apply {
                putExtra("patient", patient)
            }
            startActivity(intent)
        }

        adapter.onDeleteClicked = { patientId ->
            val dialogBinding = DialogRemovePatientBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnNo.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnKeluar.setOnClickListener {
                lifecycleScope.launch {
                    deletePatient(patientId)
                }
                dialog.dismiss()
            }

            dialog.show()
        }

        adapter.onItemClicked = { patient ->
            val intent = Intent(requireContext(), DetailPasienActivity::class.java).apply {
                putExtra("patient", patient)
            }
            startActivity(intent)
        }

        lifecycleScope.launch {
            fetchPatients()
        }
    }

    private suspend fun fetchPatients() {
        delay(2000)
        val patients = listOf(
            Patient("", "John Doe", "123 Street Name", "1234567890", patientDate = "2024-11-22"),
            Patient("", "Jane Smith", "456 Another St", "9876543210", patientDate = "2024-11-21")
        )
        adapter.submitList(patients)
        currentList = patients
    }

    private suspend fun deletePatient(patientId: String) {
        delay(2000)
        Toast.makeText(
            requireContext(),
            "Patient deleted successfully!",
            Toast.LENGTH_SHORT
        ).show()
        fetchPatients()
    }

    private fun filterPatients(query: String): List<Patient> {
        return currentList.filter { patient ->
            patient.name.contains(query, ignoreCase = true) ||
                    patient.address.contains(query, ignoreCase = true) ||
                    patient.phoneNumber.contains(query, ignoreCase = true)
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener { timestamp ->
            val selectedDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
            val filteredList = currentList.filter { it.patientDate == selectedDate }
            adapter.submitList(filteredList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
