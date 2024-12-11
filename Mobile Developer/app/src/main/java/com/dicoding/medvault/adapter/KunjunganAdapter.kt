package com.dicoding.medvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.medvault.databinding.ItemKunjunganBinding
import com.dicoding.medvault.model.Kunjungan

class KunjunganAdapter :
    ListAdapter<Kunjungan, KunjunganAdapter.KunjunganViewHolder>(KunjunganDiffCallback()) {

    var onDeleteClicked: ((Int, String) -> Unit)? = null
    var onItemClicked: ((Kunjungan) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KunjunganViewHolder {
        val binding =
            ItemKunjunganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KunjunganViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KunjunganViewHolder, position: Int) {
        val kunjungan = getItem(position)
        holder.bind(kunjungan, onDeleteClicked, onItemClicked)
    }

    class KunjunganViewHolder(private val binding: ItemKunjunganBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            kunjungan: Kunjungan,
            onDeleteClicked: ((Int, String) -> Unit)?,
            onItemClicked: ((Kunjungan) -> Unit)?
        ) {
            binding.tvName.text = kunjungan.name
            binding.tvAddress.text = kunjungan.address
            binding.tvPhoneNumber.text = kunjungan.phoneNumber
            binding.tvDate.text = kunjungan.date
            binding.tvDiagnosa.text = kunjungan.diagnosis
            binding.tvDescription.text = kunjungan.description
            binding.ivTrash.setOnClickListener {
                onDeleteClicked?.invoke(kunjungan.id, kunjungan.type)
            }
            binding.tvType.text = kunjungan.type
            binding.root.setOnClickListener {
                onItemClicked?.invoke(kunjungan)
            }
        }
    }

    class KunjunganDiffCallback : DiffUtil.ItemCallback<Kunjungan>() {
        override fun areItemsTheSame(oldItem: Kunjungan, newItem: Kunjungan): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Kunjungan, newItem: Kunjungan): Boolean {
            return oldItem == newItem
        }
    }
}