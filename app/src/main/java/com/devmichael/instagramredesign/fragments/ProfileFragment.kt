package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.HighlightAdapter
import com.devmichael.instagramredesign.adapters.ViewPagerAdapter
import com.devmichael.instagramredesign.databinding.FragmentProfileBinding
import com.devmichael.instagramredesign.models.HighlightModel
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setUpProfileToolbar()


        createHighlight()
        tabLayoutAndViewPager()
        settingsDialog()
        return binding.root
    }

    private fun setUpProfileToolbar() {
        val toolbar = binding.navAndTitleToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "wanda.s"
        //        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.backward_icon)

        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun tabLayoutAndViewPager() {
        val tabIcons = arrayOf(
            R.drawable.grid_icon,
            R.drawable.video_file_icon,
            R.drawable.tag_icon,
            R.drawable.love_icon
        )
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()
    }


    private fun createHighlight() {
        val highlightList = mutableListOf<HighlightModel>()
        highlightList.add(HighlightModel(R.drawable.status_image_1, "Ayo"))
        highlightList.add(HighlightModel(R.drawable.status_image_2,  "Alex"))
        highlightList.add(HighlightModel(R.drawable.status_image_3, "Toa"))
        highlightList.add(HighlightModel(R.drawable.status_image_4,  "Christina"))
        highlightList.add(HighlightModel(R.drawable.status_image_5,"Joseph"))
        highlightList.add(HighlightModel(R.drawable.status_image_6,  "Charles"))
        highlightList.add(HighlightModel(R.drawable.status_image_7,  "Michael"))

        binding.highlightRecyclerView.apply {
            adapter = HighlightAdapter(highlightList)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun settingsDialog() {
        binding.menuButton.setOnClickListener {
            val bottomSheetDialog = ProfileSettingsDialogFragment()
            bottomSheetDialog.show(this.childFragmentManager, "ProfileSettingsDialogFragment")
        }
    }
}