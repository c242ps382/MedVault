package com.dicoding.medvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.medvault.databinding.ItemSeputarInformasiBinding
import com.dicoding.medvault.model.Article

class InformasiAdapter : RecyclerView.Adapter<InformasiAdapter.InformasiViewHolder>() {

    private val items: ArrayList<Article> = arrayListOf()
    var onItemClicked: ((Article) -> Unit)? = null

    inner class InformasiViewHolder(private val binding: ItemSeputarInformasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(binding.ivImage)
            binding.title.text = item.title
            binding.root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiViewHolder {
        val binding = ItemSeputarInformasiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InformasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformasiViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<Article>) {
        this.items.clear()
        this.items.addAll(newList)
        notifyDataSetChanged()
    }
}
