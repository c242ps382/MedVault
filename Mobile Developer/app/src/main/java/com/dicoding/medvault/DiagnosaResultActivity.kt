package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.medvault.databinding.ActivityHasilDiagnosaBinding

class DiagnosaResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilDiagnosaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDiagnosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("RESULT")
        val label = intent.getStringExtra("LABEL")
        val value = intent.getStringExtra("VALUE")
        val label2 = intent.getStringExtra("LABEL2")
        val value2 = intent.getStringExtra("VALUE2")
        val label3 = intent.getStringExtra("LABEL3")
        val value3 = intent.getStringExtra("VALUE3")

        binding.tvResult.text = result
        binding.tvLabel.text = label
        binding.tvValue.text = value
        binding.tvLabel2.text = label2
        binding.tvValue2.text = value2
        binding.tvLabel3.text = label3
        binding.tvValue3.text = value3

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}