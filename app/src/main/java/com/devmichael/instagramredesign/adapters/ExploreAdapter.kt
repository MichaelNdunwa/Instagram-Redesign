package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.databinding.ExploreViewHolderBinding
import com.devmichael.instagramredesign.models.ExploreModel

class ExploreAdapter(private var exploreList: List<ExploreModel>):
    RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    private lateinit var binding: ExploreViewHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        binding = ExploreViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding)
    }

    override fun getItemCount() = exploreList.size

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val explore = exploreList[position]
        holder.binding.apply {
            post1.setImageResource(explore.post1)
            post2.setImageResource(explore.post2)
            post3.setImageResource(explore.post3)
            post4.setImageResource(explore.post4)
            post5.setImageResource(explore.post5)
            post6.setImageResource(explore.post6)
            post7.setImageResource(explore.post7)
            post8.setImageResource(explore.post8)
            reels1.setImageResource(explore.reels1)
            reels2.setImageResource(explore.reels2)
        }
    }

    inner class ExploreViewHolder(val binding: ExploreViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}