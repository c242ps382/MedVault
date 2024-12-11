package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.medvault.data.network.datamodel.PredictResponse
import com.dicoding.medvault.databinding.ActivityHasilDiagnosaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiagnosaResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilDiagnosaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDiagnosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("RESULT")
        val diagnose = intent.getParcelableArrayListExtra<PredictResponse>("diagnose")

        binding.tvResult.text = result

        if (diagnose?.isNotEmpty() == true) {
            binding.tvCode.text = diagnose.first().code
            binding.tvLabel.text = diagnose.first().disease
            binding.tvValue.text = (diagnose.first().probability).toString()
        }

        if (diagnose?.getOrNull(1) != null) {
            binding.tvCode2.text = diagnose[1].code
            binding.tvLabel2.text = diagnose[1].disease
            binding.tvValue2.text = (diagnose[1].probability).toString()
        }

        if (diagnose?.getOrNull(2) != null) {
            binding.tvCode3.text = diagnose[2].code
            binding.tvLabel3.text = diagnose[2].disease
            binding.tvValue3.text = (diagnose[2].probability).toString()
        }

        binding.ivBack.setOnClickListener {
            val resultIntent = Intent().apply {
                putParcelableArrayListExtra("diagnose", diagnose)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val resultIntent = Intent().apply {
                    putParcelableArrayListExtra("diagnose", diagnose)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
    }
}