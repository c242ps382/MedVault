package com.dicoding.medvault.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.medvault.ArtikelActivity
import com.dicoding.medvault.HomeActivity
import com.dicoding.medvault.adapter.InformasiAdapter
import com.dicoding.medvault.adapter.ViewPagerAdapter
import com.dicoding.medvault.databinding.FragmentHomeBinding
import com.dicoding.medvault.model.Article
import com.dicoding.medvault.model.StatisticData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var informasiAdapter: InformasiAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

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


        simulateApiSuccess()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        binding.viewpager2.adapter = viewPagerAdapter
    }

    private fun simulateApiSuccess() {
        viewPagerAdapter.submitList(
            listOf(
                "https://placehold.co/600x400.png",
                "https://placehold.co/600x400.png",
                "https://placehold.co/600x400.png"
            )
        )

        val dataList = listOf(
            Article(
                id = "1",
                title = "Penyakit Diabetes",
                description = "Dinas Kesehatan",
                lastUpdate = "11/24",
                imageUrl = "https://placehold.co/600x400.png"
            ),
            Article(
                id = "1",
                title = "Cek Kesehatan",
                description = "Puskesmas",
                lastUpdate = "11/22",
                imageUrl = "https://placehold.co/600x400.png"
            )
        )
        informasiAdapter.submitList(dataList)

        val statisticData = StatisticData(
            pasienCount = 5,
            diagnosaCount = 10,
            ibuAnakCount = 8
        )
        binding.tvDataPasien.text = statisticData.pasienCount.toString()
        binding.tvDataDiagnosa.text = statisticData.diagnosaCount.toString()
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
