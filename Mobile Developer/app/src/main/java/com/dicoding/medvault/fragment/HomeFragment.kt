package com.dicoding.medvault.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.medvault.ArtikelActivity
import com.dicoding.medvault.HomeActivity
import com.dicoding.medvault.R
import com.dicoding.medvault.adapter.InformasiAdapter
import com.dicoding.medvault.adapter.ViewPagerAdapter
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.RetrofitClient
import com.dicoding.medvault.databinding.FragmentHomeBinding
import com.dicoding.medvault.model.Article
import com.dicoding.medvault.model.StatisticData
import com.dicoding.medvault.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var informasiAdapter: InformasiAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupRecyclerView()

        binding.ivChevronLeft.setOnClickListener {
            val current = binding.viewpager2.currentItem
            if (current > 0) {
                binding.viewpager2.currentItem = current - 1
            }
        }

        binding.icChevronRight.setOnClickListener {
            val current = binding.viewpager2.currentItem
            if (current < viewPagerAdapter.itemCount - 1) {
                binding.viewpager2.currentItem = current + 1
            }
        }

        binding.ivMenu.setOnClickListener {
            // Open the drawer
            (activity as? HomeActivity)?.openDrawer()
        }

        fetchData()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        binding.viewpager2.adapter = viewPagerAdapter
    }

    private fun fetchData() {
        viewPagerAdapter.submitList(
            listOf(
                R.drawable.carousel1,
                R.drawable.carousel2,
                R.drawable.carousel3
            )
        )

        lifecycleScope.launch {
            try {
                val article = apiService.getArticle()
                informasiAdapter.submitList(article.data.map { 
                    Article(
                        id = it.id,
                        lastUpdate = it.update,
                        title = it.title,
                        description = it.description,
                        imageUrl = it.imageUrl,
                        informationTitle = it.infoTitle,
                        informationDescription = it.description

                    )
                })
            } catch (ex: Exception) {

            }
        }

        lifecycleScope.launch {
            try {
                val patientCount = apiService.getPatients().data.size
                val visitorCount = apiService.getVisits().visits.size
                val statisticData = StatisticData(
                    pasienCount = patientCount,
                    diagnosaCount = visitorCount
                )
                binding.tvDataPasien.text = statisticData.pasienCount.toString()
                binding.tvDataDiagnosa.text = statisticData.diagnosaCount.toString()
            } catch (ex: Exception) {
                val errorMessage = ex.getErrorMessage()
                if (errorMessage.isNotBlank()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        informasiAdapter = InformasiAdapter()
        binding.rvInformasiList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = informasiAdapter
        }

        informasiAdapter.onItemClicked = { article ->
            val intent = Intent(requireContext(), ArtikelActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
