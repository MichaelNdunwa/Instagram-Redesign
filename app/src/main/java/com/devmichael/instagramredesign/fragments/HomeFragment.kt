package com.devmichael.instagramredesign.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.activities.MessageActivity
import com.devmichael.instagramredesign.adapters.PostAdapter
import com.devmichael.instagramredesign.adapters.StatusAdapter
import com.devmichael.instagramredesign.databinding.FragmentHomeBinding
import com.devmichael.instagramredesign.models.PostModel
import com.devmichael.instagramredesign.models.StatusModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        createStatus()
        createPost(20)

        binding.messageButton.setOnClickListener {
            val intent = Intent(context, MessageActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun createStatus() {
        val statusList = mutableListOf<StatusModel>()
        statusList.add(StatusModel(R.drawable.status_image_1, R.drawable.ayo_profile_image, "Ayo"))
        statusList.add(StatusModel(R.drawable.status_image_2, R.drawable.alex_profile_image, "Alex"))
        statusList.add(StatusModel(R.drawable.status_image_3, R.drawable.toa_profile_image, "Toa"))
        statusList.add(StatusModel(R.drawable.status_image_4, R.drawable.christina_profile_image, "Christina"))
        statusList.add(StatusModel(R.drawable.status_image_5, R.drawable.joseph_profile_image, "Joseph"))
        statusList.add(StatusModel(R.drawable.status_image_6, R.drawable.charles_profile_image, "Charles"))
        statusList.add(StatusModel(R.drawable.status_image_7, R.drawable.michael_profile_image, "Michael"))

        binding.statusRecyclerView.adapter = StatusAdapter(statusList)
        binding.statusRecyclerView.setHasFixedSize(true)
        binding.statusRecyclerView.setItemViewCacheSize(10)
        binding.statusRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createPost(number: Int) {
        val postList = mutableListOf<PostModel>()
        repeat(number) {
            postList.add(
                PostModel(R.drawable.ayo_profile_image, "Ayo", "New York",
                    "June 11", R.drawable.someone_coding_image, "New York is wonderful.")
            )
        }

        binding.postRecyclerView.apply {
            adapter = PostAdapter(postList)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}