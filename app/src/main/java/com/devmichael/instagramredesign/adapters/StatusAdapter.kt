package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.databinding.StatusViewHolderBinding
import com.devmichael.instagramredesign.models.StatusModel

class StatusAdapter(private val statusList: MutableList<StatusModel>) :
    RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {

    private lateinit var binding: StatusViewHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = StatusViewHolderBinding.inflate(from, parent, false)
        return StatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val status = statusList[position]
        holder.binding.statusImage.setImageResource(status.statusImage)
        holder.binding.profileImage.setImageResource(status.profileImage)
        holder.binding.name.text = status.name
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    inner class StatusViewHolder(val binding: StatusViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}