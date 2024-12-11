package com.dicoding.medvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.medvault.databinding.ItemPatientBinding
import com.dicoding.medvault.model.Patient

class PatientAdapter : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private val patients = mutableListOf<Patient>()
    var onEditClicked: ((Patient) -> Unit)? = null
    var onDeleteClicked: ((Int) -> Unit)? = null
    var onItemClicked: ((Patient) -> Unit)? = null

    inner class PatientViewHolder(val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.binding.apply {
            tvName.text = patient.name
            tvAddress.text = patient.address
            tvPhoneNumber.text = patient.phoneNumber
            tvDate.text = patient.patientDate

            ivEdit.setOnClickListener {
                onEditClicked?.invoke(patient)
            }

            ivTrash.setOnClickListener {
                onDeleteClicked?.invoke(patient.id)
            }

            root.setOnClickListener {
                onItemClicked?.invoke(patient)
            }
        }
    }

    override fun getItemCount(): Int = patients.size

    fun submitList(newPatients: List<Patient>) {
        patients.clear()
        patients.addAll(newPatients)
        notifyDataSetChanged()
    }
}
