package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.databinding.ActivityAddDiagnosaBinding
import kotlinx.coroutines.launch

class AddDiagnosaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDiagnosaBinding

    private var result: String = ""
    private var label: String = ""
    private var value: String = ""
    private var label2: String = ""
    private var value2: String = ""
    private var label3: String = ""
    private var value3: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiagnosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("LABEL", label)
                putExtra("VALUE", value)
                putExtra("LABEL2", label2)
                putExtra("VALUE2", value2)
                putExtra("LABEL3", label3)
                putExtra("VALUE3", value3)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val resultIntent = Intent().apply {
                    putExtra("LABEL", label)
                    putExtra("VALUE", value)
                    putExtra("LABEL2", label2)
                    putExtra("VALUE2", value2)
                    putExtra("LABEL3", label3)
                    putExtra("VALUE3", value3)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })

        binding.btnSimpan.setOnClickListener {
            lifecycleScope.launch {
                val gejala1 = binding.etGejala.text.toString()
                val gejala2 = binding.etGejala2.text.toString()
                val gejala3 = binding.etGejala3.text.toString()
                val gejala4 = binding.etGejala4.text.toString()
                val gejala5 = binding.etGejala5.text.toString()

                val sb = StringBuilder()
                sb.append("1. $gejala1")
                sb.append("2. $gejala2")
                sb.append("3. $gejala3")
                sb.append("4. $gejala4")
                sb.append("5. $gejala5")

                result = "Sample Result"
                label = "Sample Label"
                value = "100%"
                label2 = "Sample Label 2"
                value2 = "70%"
                label3 = "Sample Label 3"
                value3 = "50%"

                val intent =
                    Intent(this@AddDiagnosaActivity, DiagnosaResultActivity::class.java).apply {
                        putExtra("RESULT", sb.toString())
                        putExtra("LABEL", label)
                        putExtra("VALUE", value)
                        putExtra("LABEL2", label2)
                        putExtra("VALUE2", value2)
                        putExtra("LABEL3", label3)
                        putExtra("VALUE3", value3)
                    }

                startActivity(intent)
            }
        }
    }
}
