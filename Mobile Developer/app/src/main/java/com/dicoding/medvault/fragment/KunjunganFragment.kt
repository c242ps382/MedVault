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
import com.dicoding.medvault.DetailRiwayatActivity
import com.dicoding.medvault.KunjunganMedisActivity
import com.dicoding.medvault.KunjunganNonMedisActivity
import com.dicoding.medvault.adapter.KunjunganAdapter
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.databinding.DialogChooseKunjunganBinding
import com.dicoding.medvault.databinding.DialogRemoveKunjunganBinding
import com.dicoding.medvault.databinding.FragmentDataKunjunganBinding
import com.dicoding.medvault.model.Kunjungan
import com.dicoding.medvault.util.DateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class KunjunganFragment : Fragment() {
    private var _binding: FragmentDataKunjunganBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: KunjunganAdapter
    private var currentList: List<Kunjungan> = arrayListOf()

    @Inject
    lateinit var apiService: ApiService

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

        adapter.onDeleteClicked = { kunjunganId, type ->
            val dialogBinding = DialogRemoveKunjunganBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnNo.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnYes.setOnClickListener {
                deleteKunjungan(kunjunganId, type)
                dialog.dismiss()
            }

            dialog.show()
        }

        adapter.onItemClicked = { kunjungan ->
            val intent = Intent(requireContext(), DetailRiwayatActivity::class.java).apply {
                putExtra("kunjunganId", kunjungan.id)
                putExtra("type", kunjungan.type)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadKunjunganData()
    }

    private fun loadKunjunganData() {
        lifecycleScope.launch {
            try {
                val remoteVisits = apiService.getVisits()
                val visits = remoteVisits.visits.map {
                    Kunjungan(
                        id = it.id,
                        name = it.username.orEmpty(),
                        address = it.address.orEmpty(),
                        phoneNumber = it.phoneNumber.orEmpty(),
                        date = DateUtil.convertToFormattedDateDefault(it.visitDate),
                        diagnosis = it.diagnosisResult,
                        description = it.action,
                        visitNumber = it.visitNumber,
                        type = it.type
                    )
                }
                adapter.submitList(visits)
                currentList = visits
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteKunjungan(kunjunganId: Int, type: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.deleteKunjungan(kunjunganId, type)
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                loadKunjunganData()
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }
        }
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
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(Date(timestamp))
            val filteredList = currentList.filter { it.date == selectedDate }
            adapter.submitList(filteredList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
