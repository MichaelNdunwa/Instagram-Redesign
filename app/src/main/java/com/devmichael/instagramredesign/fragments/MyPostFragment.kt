package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.MyPostAdapter
import com.devmichael.instagramredesign.databinding.FragmentMyPostBinding
import com.devmichael.instagramredesign.models.MyPostModel

class MyPostFragment : Fragment() {

    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)

        createMyPost()
        return binding.root
    }

    private fun addImages(number: Int): MutableList<MyPostModel> {
        val myPostList = mutableListOf<MyPostModel>()
        repeat(number) {
            myPostList.add(MyPostModel(R.drawable.reading_alone))
        }
        return myPostList
    }

    private fun createMyPost() {
        val myPosts = addImages(20)
        binding.myPostItems.apply {
            adapter = MyPostAdapter(myPosts)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            layoutManager = GridLayoutManager(context, 4)
        }

    }
}