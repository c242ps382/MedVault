package com.dicoding.medvault

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.medvault.databinding.ActivityArticleBinding
import com.dicoding.medvault.model.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>("article")

        setupUI()
        if (article != null) {
            loadArticleData(article)
        }
    }

    private fun setupUI() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun loadArticleData(article: Article) {
        binding.tvLastUpdate.text = "Diperbarui : ${article.lastUpdate}"
        binding.tvArticleTitle.text = article.title
        Glide.with(this).load(article.imageUrl).into(binding.ivArticleImage)
        binding.tvInformationTitle.text = article.informationTitle
        binding.tvInformationDescription.text = article.informationDescription
    }
}
