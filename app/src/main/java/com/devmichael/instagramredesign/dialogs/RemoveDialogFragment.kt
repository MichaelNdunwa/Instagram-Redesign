package com.devmichael.instagramredesign.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.RemoveBottomSheetDialogBinding
import com.devmichael.instagramredesign.models.UserModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class RemoveDialogFragment(val user: UserModel, val activity: AppCompatActivity) :
    BottomSheetDialogFragment() {
    private lateinit var binding: RemoveBottomSheetDialogBinding
    private var loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RemoveBottomSheetDialogBinding.inflate(inflater, container, false)

        removeAction()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun removeAction() {
        binding.apply {
            Picasso.get().load(user.profileImage).placeholder(R.drawable.default_profile_image)
                .into(profileImage)
            val username = user.username
            removePromptTextview.text =
                "We won't tell $username they were removed from your followers."
//            profileImage.setImageResource(user.profileImage)

            // Do something when the remove button is clicked:
            removeButton.setOnClickListener {
                loggedInUser?.uid.let { loggedInUserId ->
                    FirebaseDatabase.getInstance().reference
                        .child("follow").child(loggedInUserId.toString())
                        .child("followers").child(user.uid)
                        .removeValue().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                FirebaseDatabase.getInstance().reference
                                    .child("follow").child(user.uid)
                                    .child("following").child(loggedInUserId.toString())
                                    .removeValue().addOnCompleteListener { secondTask ->
                                        if (secondTask.isSuccessful) {
                                            //Snackbar.make(requireActivity().findViewById(android.R.id.content), "Removed", Snackbar.LENGTH_SHORT).show()
                                            Toast.makeText(activity, "Removed", Toast.LENGTH_SHORT).show()
                                            dismiss()
                                        }
                                    }
                            }
                        }
                }

            }
        }
    }
}