package com.dicoding.medvault.fragment

import android.content.Intent
import android.os.Bundle
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
import com.dicoding.medvault.DetailRiwayatActivity
import com.dicoding.medvault.KunjunganMedisActivity
import com.dicoding.medvault.KunjunganNonMedisActivity
import com.dicoding.medvault.adapter.KunjunganAdapter
import com.dicoding.medvault.databinding.DialogChooseKunjunganBinding
import com.dicoding.medvault.databinding.DialogRemoveKunjunganBinding
import com.dicoding.medvault.databinding.FragmentDataKunjunganBinding
import com.dicoding.medvault.model.Kunjungan
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class KunjunganFragment : Fragment() {
    private var _binding: FragmentDataKunjunganBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: KunjunganAdapter
    private var currentList: List<Kunjungan> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataKunjunganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivBack.setOnClickListener {
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }

        binding.fabAdd.setOnClickListener {
            val dialogBinding = DialogChooseKunjunganBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnMed.setOnClickListener {
                val intent = Intent(requireContext(), KunjunganMedisActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            dialogBinding.btnNonMed.setOnClickListener {
                val intent = Intent(requireContext(), KunjunganNonMedisActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            dialog.show()
        }

        binding.etSearch.addTextChangedListener { text ->
            val filteredList = filterKunjungan(text.toString())
            adapter.submitList(filteredList)
        }

        binding.ivDate.setOnClickListener {
            showDatePicker()
        }

        adapter = KunjunganAdapter()
        binding.rvPatients.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPatients.adapter = adapter

        adapter.onDeleteClicked = { patientId ->
            val dialogBinding = DialogRemoveKunjunganBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnNo.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnYes.setOnClickListener {
                lifecycleScope.launch {
                    deletePatient(patientId)
                }
                dialog.dismiss()
            }

            dialog.show()
        }

        adapter.onItemClicked = { kunjungan ->
            val intent = Intent(requireContext(), DetailRiwayatActivity::class.java).apply {
                putExtra("kunjungan", kunjungan)
            }
            startActivity(intent)
        }

        lifecycleScope.launch {
            loadKunjunganData()
        }
    }

    private fun loadKunjunganData() {
        val kunjunganList = listOf(
            Kunjungan(
                id = "1",
                name = "John Doe",
                address = "123 Main St",
                phoneNumber = "123-456-7890",
                date = "2024-11-23"
            ),
            Kunjungan(
                id = "2",
                name = "Jane Doe",
                address = "456 Elm St",
                phoneNumber = "987-654-3210",
                date = "2024-11-24"
            )
        )
        adapter.submitList(kunjunganList)
        currentList = kunjunganList
    }

    private suspend fun deletePatient(patientId: String) {
        delay(2000)
        Toast.makeText(
            requireContext(),
            "Patient deleted successfully!",
            Toast.LENGTH_SHORT
        ).show()
        loadKunjunganData()
    }

    private fun filterKunjungan(query: String): List<Kunjungan> {
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
            val filteredList = currentList.filter { it.date == selectedDate }
            adapter.submitList(filteredList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
