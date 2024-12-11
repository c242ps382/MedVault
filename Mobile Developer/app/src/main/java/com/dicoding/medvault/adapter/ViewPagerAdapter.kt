package com.dicoding.medvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.medvault.databinding.ItemViewPagerImageBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private val imageUrls = mutableListOf<Int>()

    inner class ViewPagerViewHolder(private val binding: ItemViewPagerImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: Int) {
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemViewPagerImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size

    fun submitList(images: List<Int>) {
        imageUrls.clear()
        imageUrls.addAll(images)
        notifyDataSetChanged()
    }
}
