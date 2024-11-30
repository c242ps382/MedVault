package com.dicoding.medvault

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.databinding.ActivityDetailRiwayatBinding
import kotlinx.coroutines.launch

class DetailRiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRiwayatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        loadDataFromApi()
    }

    private fun loadDataFromApi() {
        lifecycleScope.launch {
            try {
                val data = fetchApiData()

                binding.tvNamaLengkap.text = getString(R.string.text_form_nama) + " " + data.namaLengkap
                binding.tvTanggalLahir.text = getString(R.string.text_form_tanggal_lahir) + " " + data.tanggalLahir
                binding.tvAlamat.text = getString(R.string.text_form_alamat) + " " + data.alamat
                binding.tvJenisKelamin.text = getString(R.string.text_form_jenis_kelamin) + " " + data.jenisKelamin
                binding.tvUsia.text = getString(R.string.text_usia) + " " + data.usia
                binding.tvNoKunjungan.text = data.noKunjungan
                binding.tvTanggalWaktu.text = data.tanggalWaktu
                binding.tvGejalaList.text = data.gejala
                binding.tvAnamnesaList.text = data.anamnesa
                binding.tvDiagnosaList.text = data.diagnosa
                binding.tvCatatanTindakan.text = data.catatanTindakan
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun fetchApiData(): ApiResponse {
        kotlinx.coroutines.delay(2000)
        return ApiResponse(
            namaLengkap = "John Doe",
            tanggalLahir = "01/01/1990",
            alamat = "123 Main Street",
            jenisKelamin = "Male",
            usia = "34",
            noKunjungan = "001",
            tanggalWaktu = "01/01/2024 10:00 AM",
            gejala = "Fever, Cough",
            anamnesa = "History of flu",
            diagnosa = "Common cold",
            catatanTindakan = "Rest and hydration"
        )
    }
}

data class ApiResponse(
    val namaLengkap: String,
    val tanggalLahir: String,
    val alamat: String,
    val jenisKelamin: String,
    val usia: String,
    val noKunjungan: String,
    val tanggalWaktu: String,
    val gejala: String,
    val anamnesa: String,
    val diagnosa: String,
    val catatanTindakan: String
)
