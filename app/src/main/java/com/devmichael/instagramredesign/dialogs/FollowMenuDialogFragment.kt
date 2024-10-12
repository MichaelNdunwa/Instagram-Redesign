package com.devmichael.instagramredesign.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FollowingMenuBottomSheetDialogBinding
import com.devmichael.instagramredesign.models.UserModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class FollowMenuDialogFragment(val user: UserModel) : BottomSheetDialogFragment() {
    private lateinit var binding: FollowingMenuBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowingMenuBottomSheetDialogBinding.inflate(inflater, container, false)
        userDetails()
        return binding.root
    }

    private fun userDetails() {
        binding.apply {
            profileName.text = user.username
            Picasso.get().load(user.profileImage).placeholder(R.drawable.default_profile_image)
                .into(profileImage)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }
}