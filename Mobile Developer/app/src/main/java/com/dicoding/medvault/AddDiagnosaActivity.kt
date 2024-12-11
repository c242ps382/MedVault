package com.dicoding.medvault

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dicoding.medvault.data.network.PredictService
import com.dicoding.medvault.data.network.datamodel.PredictRequest
import com.dicoding.medvault.data.network.datamodel.PredictResponse
import com.dicoding.medvault.databinding.ActivityAddDiagnosaBinding
import com.dicoding.medvault.util.getErrorMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddDiagnosaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDiagnosaBinding

    private var result: ArrayList<PredictResponse> = arrayListOf()

    @Inject
    lateinit var predictService: PredictService

    val symptoms = listOf(
        "Kecemasan dan kegelisahan",
        "Depresi",
        "Sesak napas",
        "Gejala depresi atau psikotis",
        "Nyeri dada tajam",
        "Pusing",
        "Insomnia",
        "Gerakan involunter abnormal",
        "Sesak dada",
        "Sensasi jantung berdegup kencang (Palpitasi)",
        "Detak jantung tidak teratur",
        "Pernapasan cepat",
        "Suara serak",
        "Sakit tenggorokan",
        "Kesulitan berbicara",
        "Batuk",
        "Hidung tersumbat",
        "Pembengkakan tenggorokan",
        "Pendengaran berkurang",
        "Benjolan di tenggorokan",
        "Tenggorokan terasa sesak",
        "Kesulitan menelan",
        "Pembengkakan kulit",
        "Kesulitan memulai buang air kecil (Retensi)",
        "Massa di selangkangan",
        "Nyeri kaki",
        "Nyeri pinggul",
        "Nyeri suprapubik",
        "Darah dalam tinja",
        "Kekurangan pertumbuhan",
        "Gejala emosional",
        "Kelemahan siku",
        "Kelemahan punggung",
        "Nanah dalam dahak",
        "Gejala pada skrotum dan testis",
        "Pembengkakan skrotum",
        "Nyeri pada testis",
        "Kentut (Flatulence)",
        "Nanah keluar dari telinga",
        "Penyakit kuning (Jaundice)",
        "Massa di skrotum",
        "Cairan putih dari mata",
        "Bayi rewel",
        "Penyalahgunaan alkohol",
        "Pingsan",
        "Perilaku bermusuhan",
        "Penyalahgunaan narkoba",
        "Nyeri perut tajam",
        "Merasa sakit",
        "Muntah",
        "Sakit kepala",
        "Mual",
        "Diare",
        "Gatal pada vagina",
        "Kekeringan vagina",
        "Nyeri saat buang air kecil",
        "Buang air kecil tidak terkendali",
        "Nyeri saat berhubungan seksual",
        "Sering buang air kecil",
        "Nyeri perut bagian bawah",
        "Keputihan",
        "Darah dalam urin",
        "Sensasi panas mendadak",
        "Perdarahan intermenstruasi",
        "Nyeri tangan atau jari",
        "Nyeri pergelangan tangan",
        "Pembengkakan tangan atau jari",
        "Nyeri lengan",
        "Pembengkakan pergelangan tangan",
        "kekakuan atau ketegangan pada lengan",
        "Pembengkakan lengan",
        "kekakuan atau ketegangan pada tangan atau jari",
        "Kekakuan atau ketegangan pada pergelangan tangan",
        "Pembengkakan bibir",
        "Sakit gigi",
        "Kulit tampak abnormal",
        "Lesi kulit",
        "Jerawat",
        "Bibir kering",
        "Nyeri wajah",
        "Sariawan",
        "Pertumbuhan kulit",
        "Deviasi mata",
        "Penglihatan berkurang",
        "Penglihatan ganda",
        "Mata juling",
        "Gejala pada mata",
        "Nyeri mata",
        "Mata bergerak abnormal",
        "Gerakan kelopak mata abnormal",
        "Sensasi benda asing di mata",
        "Kulit kepala tampak tidak teratur",
        "Pembengkakan kelenjar getah bening",
        "Nyeri punggung",
        "Nyeri leher",
        "Nyeri punggung bawah",
        "Nyeri pada anus",
        "Nyeri saat kehamilan",
        "Nyeri panggul",
        "Disfungsi ereksi",
        "Bayi memuntahkan susu",
        "Muntah darah",
        "Regurgitasi",
        "Nyeri perut terasa terbakar",
        "Gelisah",
        "Gejala pada bayi",
        "Suara napas berbunyi (Mengi)",
        "Edema perifer",
        "Massa di leher",
        "Nyeri telinga",
        "Pembengkakan rahang",
        "Mulut kering",
        "Pembengkakan leher",
        "Nyeri lutut",
        "Nyeri kaki atau jari kaki",
        "Kaki bengkok atau lutut berbentuk O atau X",
        "Nyeri pergelangan kaki",
        "Nyeri tulang",
        "Kelemahan lutut",
        "Nyeri siku",
        "Pembengkakan lutut",
        "Tahi lalat kulit",
        "Benjolan atau massa pada lutut",
        "Kenaikan berat badan",
        "Masalah dengan pergerakan",
        "kekakuan atau ketegangan pada lutut",
        "Pembengkakan kaki",
        "Pembengkakan kaki atau jari kaki",
        "Nyeri ulu hati",
        "Masalah merokok",
        "Nyeri otot",
        "Masalah pemberian makan pada bayi",
        "Penurunan berat badan baru-baru ini",
        "Masalah bentuk atau ukuran payudara",
        "Kekurangan berat badan",
        "Kesulitan makan",
        "Perdarahan menstruasi sedikit",
        "Nyeri pada vagina",
        "Kemerahan pada vagina",
        "Iritasi vulva",
        "Kelemahan",
        "Penurunan detak jantung",
        "Peningkatan detak jantung",
        "Pendarahan atau cairan dari puting susu",
        "Telinga berdenging (Tinnitus)",
        "Telinga terasa tersumbat",
        "Telinga gatal",
        "Sakit kepala depan",
        "Cairan di telinga",
        "kekakuan atau ketegangan pada leher",
        "Bintik atau kabut dalam penglihatan",
        "Mata merah",
        "Air mata berlebihan (Lakrimasi)",
        "Mata gatal",
        "Kebutaan",
        "Mata terbakar atau perih",
        "Kelopak mata gatal",
        "Merasa kedinginan",
        "Nafsu makan menurun",
        "Nafsu makan berlebihan",
        "Kemarahan berlebihan",
        "Kehilangan sensasi",
        "Kelemahan fokal",
        "Pengucapan kata tidak jelas (Bicara cadel)",
        "Gejala pada wajah",
        "Gangguan memori",
        "Kesemutan atau mati rasa (Parestesia)",
        "Nyeri samping",
        "Demam",
        "Nyeri bahu",
        "kekakuan atau ketegangan pada bahu",
        "Kelemahan bahu",
        "Kram atau kejang lengan",
        "Pembengkakan bahu",
        "Lesi lidah",
        "Kram atau kejang kaki",
        "Lidah yang tampak abnormal",
        "Nyeri di seluruh tubuh",
        "Nyeri tubuh bagian bawah",
        "Masalah selama kehamilan",
        "Bercak atau perdarahan selama kehamilan",
        "Kram dan kejang",
        "Nyeri perut bagian atas",
        "Perut kembung",
        "Perubahan penampilan tinja",
        "Warna atau bau urin yang tidak biasa",
        "Massa ginjal",
        "Pembengkakan perut",
        "Gejala prostat",
        "kekakuan atau ketegangan pada kaki",
        "Kesulitan bernapas",
        "Nyeri tulang rusuk",
        "Nyeri sendi",
        "kekakuan atau ketegangan pada otot",
        "Pucat",
        "Benjolan atau massa pada tangan atau jari",
        "Menggigil",
        "Nyeri selangkangan",
        "Kelelahan",
        "Distensi perut",
        "Regurgitasi.1",
        "Gejala ginjal",
        "Tinja berwarna hitam (Melena)",
        "Wajah memerah",
        "Batuk berdahak",
        "Kejang",
        "Delusi atau halusinasi",
        "Kram atau kejang bahu",
        "Kekakuan atau ketegangan pada sendi",
        "Nyeri atau sakit pada payudara",
        "Buang air kecil berlebihan di malam hari",
        "Perdarahan dari mata",
        "Perdarahan rektal",
        "Sembelit",
        "Masalah temperamen",
        "Pilek (Coryza)",
        "Kelemahan pergelangan tangan",
        "Mata lelah",
        "Batuk darah",
        "Pembengkakan kelenjar getah bening (Limfedema)",
        "Kulit pada tungkai atau kaki tampak terinfeksi",
        "Reaksi alergi",
        "Konjungsi dada",
        "Pembengkakan otot",
        "Nanah dalam urin",
        "Ukuran atau bentuk telinga yang abnormal",
        "Kelemahan punggung bawah",
        "Rasa mengantuk",
        "Pernapasan terhenti sementara (Apnea)",
        "Suara napas abnormal",
        "Pertumbuhan berlebihan",
        "Kram atau kejang siku",
        "Merasa panas dan dingin",
        "Gumpalan darah selama menstruasi",
        "Tidak menstruasi",
        "Menarik telinga",
        "Nyeri gusi",
        "Kemerahan pada telinga",
        "Retensi cairan",
        "Sindrom seperti flu",
        "Penyumbatan sinus",
        "Nyeri sinus",
        "Ketakutan dan fobia",
        "Kehamilan baru-baru ini",
        "Kontraksi rahim",
        "Nyeri dada terasa terbakar",
        "Kram atau kejang punggung",
        "Kekakuan di seluruh tubuh",
        "Kram otot, kontraktur, atau kejang",
        "Kram atau kejang punggung bawah",
        "Benjolan atau massa di punggung",
        "Mimisan",
        "Periode menstruasi panjang",
        "Aliran menstruasi deras",
        "Menstruasi tidak teratur",
        "Nyeri saat menstruasi",
        "Ketidaksuburan (Infertilitas)",
        "Menstruasi yang sering",
        "Berkeringat",
        "Massa di kelopak mata",
        "Mata bengkak",
        "Pembengkakan kelopak mata",
        "Lesi atau ruam pada kelopak mata",
        "Pertumbuhan rambut yang tidak diinginkan",
        "Gejala kandung kemih",
        "Kuku tampak tidak teratur",
        "Kulit gatal",
        "Sakit saat bernapas",
        "Kebiasaan menggigit kuku",
        "Kulit kering, mengelupas, bersisik, atau kasar",
        "Kulit di lengan atau tangan tampak terinfeksi",
        "Iritasi kulit",
        "Kulit kepala gatal",
        "Pembengkakan pinggul",
        "Ketidakmampuan menahan buang air besar (Inkontinensia)",
        "Kram atau kejang kaki atau jari kaki",
        "Kutil",
        "Benjolan pada penis",
        "Rambut terlalu sedikit",
        "Benjolan atau massa di kaki atau jari kaki",
        "Ruam kulit",
        "Massa atau pembengkakan di sekitar anus",
        "Pembengkakan punggung bawah",
        "Pembengkakan pergelangan kaki",
        "Benjolan atau massa di pinggul",
        "Drainase di tenggorokan",
        "Kulit kepala kering atau mengelupas",
        "Ketegangan atau iritabilitas pramenstruasi",
        "Merasa panas",
        "Kaki terputar ke dalam",
        "Kekakuan atau ketegangan pada kaki atau jari kaki",
        "Tekanan panggul",
        "Pembengkakan siku",
        "Kekakuan atau ketegangan pada siku",
        "Menopause dini atau terlambat",
        "Massa di telinga",
        "Perdarahan dari telinga",
        "Kelemahan tangan atau jari",
        "Rasa percaya diri rendah",
        "Iritasi tenggorokan",
        "Gatal pada anus",
        "Pembengkakan atau kemerahan pada amandel",
        "Bentuk pusar tidak teratur",
        "Pembengkakan lidah",
        "Luka pada bibir",
        "Luka pada vulva",
        "Kekakuan atau ketegangan pada pinggul",
        "Nyeri mulut",
        "Kelemahan lengan",
        "Benjolan atau massa pada kaki",
        "Gangguan penciuman atau pengecapan",
        "Cairan atau zat abnormal dalam tinja",
        "Nyeri pada penis",
        "Kehilangan gairah seksual",
        "Obsesi dan kompulsif",
        "Perilaku antisosial",
        "Kram atau kejang leher",
        "Pupil mata tidak sama",
        "Sirkulasi darah buruk",
        "Haus",
        "Berjalan saat tidur",
        "Kulit berminyak",
        "Bersin",
        "Massa kandung kemih",
        "Kram atau kejang pada lutut",
        "Ejakulasi dini",
        "Kelemahan kaki",
        "Masalah postur tubuh",
        "Perdarahan di mulut",
        "Perdarahan pada lidah",
        "Perubahan ukuran atau warna tahi lalat kulit",
        "Kemerahan pada penis",
        "Sekret penis",
        "Benjolan atau massa pada bahu",
        "Buang air kecil dengan volume urin banyak (Poliuria)",
        "Mata kabur",
        "Perilaku histeris",
        "Benjolan atau massa pada lengan",
        "Mimpi buruk",
        "Gusi berdarah",
        "Nyeri gusi.1",
        "Mengompol",
        "Ruam popok",
        "Benjolan atau massa pada payudara",
        "Perdarahan vagina setelah menopause",
        "Menstruasi yang jarang",
        "Massa pada vulva",
        "Nyeri rahang",
        "Gatal pada skrotum",
        "Masalah pada payudara setelah melahirkan",
        "Kelopak mata tertarik",
        "Keraguan",
        "Benjolan atau massa pada siku",
        "Kelemahan otot",
        "Kemerahan pada tenggorokan",
        "Pembengkakan sendi",
        "Nyeri lidah",
        "Kemerahan di sekitar hidung",
        "Keriput pada kulit",
        "Kelemahan pada kaki atau jari kaki",
        "Kram atau kejang pada tangan atau jari tangan",
        "Kekakuan atau ketegangan pada punggung",
        "Benjolan atau massa pada pergelangan tangan",
        "Nyeri kulit",
        "Kekakuan atau ketegangan pada punggung bawah",
        "Keluaran urin rendah",
        "Kulit di kepala atau leher tampak terinfeksi",
        "Gagap atau terbata-bata",
        "Masalah dengan orgasme",
        "Kelainan bentuk atau struktur hidung",
        "Benjolan di rahang",
        "Luka pada hidung",
        "Kelemahan pinggul",
        "Pembengkakan punggung",
        "Kekakuan atau ketegangan pada pergelangan kaki",
        "Kelemahan pergelangan kaki",
        "Kelemahan leher",
    )

    private var gejala1: String = ""
    private var gejala2: String = ""
    private var gejala3: String = ""
    private var gejala4: String = ""
    private var gejala5: String = ""

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val newIntent = result.data?.apply {
                    putExtra(
                        "gejala",
                        Gson().toJson(listOf(gejala1, gejala2, gejala3, gejala4, gejala5))
                    )
                }
                setResult(RESULT_OK, newIntent)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiagnosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, symptoms)
        binding.etGejala.setAdapter(adapter)

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, symptoms)
        binding.etGejala2.setAdapter(adapter1)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, symptoms)
        binding.etGejala3.setAdapter(adapter2)

        val adapter3 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, symptoms)
        binding.etGejala4.setAdapter(adapter3)

        val adapter4 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, symptoms)
        binding.etGejala5.setAdapter(adapter4)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val resultIntent = Intent().apply {
                    putParcelableArrayListExtra("diagnose", result)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })

        binding.btnSimpan.setOnClickListener {
            lifecycleScope.launch {
                gejala1 = binding.etGejala.text.toString()
                gejala2 = binding.etGejala2.text.toString()
                gejala3 = binding.etGejala3.text.toString()
                gejala4 = binding.etGejala4.text.toString()
                gejala5 = binding.etGejala5.text.toString()

                var number = 1
                val sb = StringBuilder()
                if (gejala1.isNotEmpty()) {
                    sb.append("${number}. $gejala1\n")
                    number += 1
                }
                if (gejala2.isNotEmpty()) {
                    sb.append("$number. $gejala2\n")
                    number += 1
                }
                if (gejala3.isNotEmpty()) {
                    sb.append("$number. $gejala3\n")
                    number += 1
                }
                if (gejala4.isNotEmpty()) {
                    sb.append("$number. $gejala4\n")
                    number += 1
                }
                if (gejala5.isNotEmpty()) {
                    sb.append("$number. $gejala5\n")
                    number += 1
                }

                try {
                    binding.loading.isVisible = true
                    val response = predictService.predict(
                        PredictRequest(
                            symptoms = listOf(
                                gejala1,
                                gejala2,
                                gejala3,
                                gejala4,
                                gejala5
                            )
                        )
                    )
                    result = ArrayList(response)

                    val intent =
                        Intent(this@AddDiagnosaActivity, DiagnosaResultActivity::class.java).apply {
                            putExtra("RESULT", sb.toString())
                            putParcelableArrayListExtra("diagnose", result)
                        }

                    startForResult.launch(intent)
                    binding.loading.isVisible = false
                } catch (ex: Exception) {
                    Toast.makeText(
                        this@AddDiagnosaActivity,
                        ex.getErrorMessage(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loading.isVisible = false
                }
            }
        }
    }
}
