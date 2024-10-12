package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.databinding.HighlightViewHolderBinding
import com.devmichael.instagramredesign.models.HighlightModel

class HighlightAdapter(private val highlights: MutableList<HighlightModel>) :
    RecyclerView.Adapter<HighlightAdapter.HighlightHolder>() {

    private lateinit var binding: HighlightViewHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightHolder {
        val from = LayoutInflater.from(parent.context)
        binding = HighlightViewHolderBinding.inflate(from, parent, false)
        return HighlightHolder(binding)
    }

    override fun getItemCount(): Int {
        return highlights.size
    }

    override fun onBindViewHolder(holder: HighlightHolder, position: Int) {
        val highlight = highlights[position]
        holder.binding.apply {
            highLightImage.setImageResource(highlight.image)
            title.text = highlight.title
        }
    }

    inner class HighlightHolder(val binding: HighlightViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}