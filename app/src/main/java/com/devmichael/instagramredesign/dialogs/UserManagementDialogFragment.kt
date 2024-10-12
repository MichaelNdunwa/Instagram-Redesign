package com.devmichael.instagramredesign.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FragmentUserManagementBinding
import com.devmichael.instagramredesign.fragments.UserProfileFragment
import com.devmichael.instagramredesign.models.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase


class UserManagementDialogFragment(
    private val userName: String,
    val notFollowing: Boolean?,
    val firebaseUser: FirebaseUser?,
    val user: UserModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUserManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserManagementBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.removeIv.setOnClickListener {
            dismiss()
        }

        binding.username.text = userName
        binding.unfollowButton.setOnClickListener { unfollowAction() }

    }

    private fun unfollowAction() {
        if (!notFollowing!!) {
            firebaseUser?.uid.let { currentUserId ->
                FirebaseDatabase.getInstance().reference
                    .child("follow").child(currentUserId.toString())
                    .child("following").child(user.uid)
                    .removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            firebaseUser?.uid.let { currentUserId ->
                                FirebaseDatabase.getInstance().reference
                                    .child("follow").child(user.uid)
                                    .child("followers").child(currentUserId.toString())
                                    .removeValue().addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val fragment =
                                                parentFragmentManager.findFragmentById(R.id.fragmentContainer)
                                            if (fragment is UserProfileFragment) {
                                                fragment.checkFollowingStatus(user.uid, fragment.binding.followButton)
//                                                fragment.binding.followButton.text = "Follow"
                                                fragment.menuVisibility()
//                                                fragment.binding.menuButton.visibility = View.GONE
                                            }
                                            dismiss()
                                        }
                                    }
//                            }
                        }
                    }
            }
        }
    }

}