package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        setUpNotificationToolbar()

        return binding.root
    }

    private fun setUpNotificationToolbar() {
        val toolbar = binding.notificationToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Notifications"

        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
        binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == 0) {
                // Scrolling has stopped at the top
                binding.notificationToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            /* else if (scrollY == v.getChildAt(0).height - v.height) {
                // Scrolling has stopped at the bottom
            } */
            else {
                // Scrolling is still in progress
                binding.notificationToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

}