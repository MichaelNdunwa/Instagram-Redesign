package com.devmichael.instagramredesign.fragments.main_user_profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devmichael.instagramredesign.activities.SettingsAndPrivacyActivity
import com.devmichael.instagramredesign.databinding.ProfileSettingsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileSettingsDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ProfileSettingsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileSettingsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Implement click listeners
        binding.removeIv.setOnClickListener {
            dismiss()
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(context, SettingsAndPrivacyActivity::class.java)
            requireActivity().startActivity(intent)
//            startActivity(intent)

        }
    }



}