package com.dicoding.medvault

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.datamodel.Anamnesa
import com.dicoding.medvault.databinding.ActivityDetailRiwayatBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject

@AndroidEntryPoint
class DetailRiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRiwayatBinding

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kunjunganId = intent.getIntExtra("kunjunganId", -1)
        val type = intent.getStringExtra("type")

        binding.ivBack.setOnClickListener {
            finish()
        }

        if (kunjunganId != -1 && type?.isNotBlank() == true) {
            loadDataFromApi(kunjunganId, type)
        }
    }

    private fun loadDataFromApi(id: Int, type: String) {
        lifecycleScope.launch {
            try {
                if (type == "Medis") {
                    val detail = apiService.getMedisDetail(id)
                    binding.tvNamaLengkap.text =
                        getString(R.string.text_form_nama) + " " + detail.user.nama
                    binding.tvTanggalLahir.text =
                        getString(R.string.text_form_tanggal_lahir) + " " + detail.user.tanggalLahir
                    binding.tvAlamat.text =
                        getString(R.string.text_form_alamat) + " " + detail.user.alamat
                    binding.tvJenisKelamin.text =
                        getString(R.string.text_form_jenis_kelamin) + " " + detail.user.jenisKelamin
                    binding.tvUsia.text = getString(R.string.text_usia) + " " + detail.user.umur
                    binding.tvNoKunjungan.text = detail.kunjungan.nomorKunjungan
                    binding.tvTanggalWaktu.text = detail.kunjungan.tanggalKunjungan
                    val gson = Gson()
                    val gejalaSb = StringBuilder()
                    try {
                        val gejalaList = jsonArrayToList(detail.kunjungan.gejala)
                        gejalaList.forEach {
                            gejalaSb.append("• $it\n")
                        }
                    } catch (ex: Exception) {

                    }
                    binding.tvGejalaList.text = gejalaSb.toString()
                    val anamnesaSb = StringBuilder()
                    try {
                        val anamnesa =
                            gson.fromJson(detail.kunjungan.anamnesa, Anamnesa::class.java)
                        anamnesaSb.append("• ${anamnesa.sistoleDiastol} mmHg\n")
                        anamnesaSb.append("• ${anamnesa.suhu} °C\n")
                        anamnesaSb.append("• ${anamnesa.detakNadi} bpm\n")
                        anamnesaSb.append("• ${anamnesa.beratBadan} kg\n")
                        anamnesaSb.append("• ${anamnesa.tinggiBadan} cm\n")
                    } catch (ex: Exception) {

                    }
                    binding.tvAnamnesaList.text = anamnesaSb.toString()
                    binding.tvDiagnosaList.text = "• ${detail.kunjungan.hasilDiagnosa}"
                    binding.tvCatatanTindakan.text = detail.kunjungan.tindakan
                    binding.tvBiaya.isVisible = false
                    binding.tvBiayaLabel.isVisible = false
                    binding.tvKeluhanLabel.isVisible = false
                    binding.tvKeluhan.isVisible = false
                } else {
                    val detail = apiService.getNonMedisDetail(id)
                    binding.tvNamaLengkap.text =
                        getString(R.string.text_form_nama) + " " + detail.user.nama
                    binding.tvTanggalLahir.text =
                        getString(R.string.text_form_tanggal_lahir) + " " + detail.user.tanggalLahir
                    binding.tvAlamat.text =
                        getString(R.string.text_form_alamat) + " " + detail.user.alamat
                    binding.tvJenisKelamin.text =
                        getString(R.string.text_form_jenis_kelamin) + " " + detail.user.jenisKelamin
                    binding.tvUsia.text = getString(R.string.text_usia) + " " + detail.user.umur
                    binding.tvNoKunjungan.text = detail.kunjungan.nomorKunjungan
                    binding.tvTanggalWaktu.text = detail.kunjungan.tanggalKunjungan
                    binding.tvGejalaList.isVisible = false
                    binding.tvGejala.isVisible = false
                    binding.tvAnamnesaList.isVisible = false
                    binding.tvAnamnesa.isVisible = false
                    binding.tvDiagnosaList.isVisible = false
                    binding.tvDiagnosa.isVisible = false
                    binding.tvBiaya.isVisible = true
                    binding.tvBiayaLabel.isVisible = true
                    binding.tvBiaya.text = detail.kunjungan.biaya
                    binding.tvKeluhanLabel.isVisible = true
                    binding.tvKeluhan.isVisible = true
                    binding.tvKeluhan.text = detail.kunjungan.keluhan
                    binding.tvCatatanTindakan.text = detail.kunjungan.tindakan
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetailRiwayatActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun jsonArrayToList(jsonArrayString: String): List<String> {
        val jsonArray = JSONArray(jsonArrayString)

        val list = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }

        return list
    }
}