package com.dicoding.medvault

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.medvault.databinding.ActivityArticleBinding
import com.dicoding.medvault.model.Article

class ArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadArticleData()
    }

    private fun setupUI() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun loadArticleData() {
        val article = getArticleFromAPI()

        binding.tvLastUpdate.text = "Diperbarui : ${article.lastUpdate}"
        binding.tvArticleTitle.text = article.title
        Glide.with(this).load(article.imageUrl).into(binding.ivArticleImage)
        binding.tvInformationTitle.text = article.informationTitle
        binding.tvInformationDescription.text = article.informationDescription
    }

    private fun getArticleFromAPI(): Article {
        return Article(
            lastUpdate = "03 November 2023",
            title = "Stunting",
            imageUrl = "https://placehold.co/600x400.png",
            informationTitle = "Mengenal Apa Itu Stunting",
            informationDescription = "Menurut WHO (2015), stunting adalah gangguan pertumbuhan dan perkembangan anak akibat kekurangan gizi kronis dan infeksi berulang..."
        )
    }
}
