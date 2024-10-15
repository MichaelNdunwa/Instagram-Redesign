package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.ExploreAdapter
import com.devmichael.instagramredesign.adapters.SearchAdapter
import com.devmichael.instagramredesign.databinding.FragmentExploreBinding
import com.devmichael.instagramredesign.models.ExploreModel
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private var userList: MutableList<UserModel> = mutableListOf()
    private var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        setUpExploreRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveUsers()
    }


    private fun createExplore(number: Int): MutableList<ExploreModel> {
        val exploreList = mutableListOf<ExploreModel>()

        repeat(number) {
            exploreList.add(
                ExploreModel(
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone,
                    R.drawable.reading_alone
                )
            )
        }
        return exploreList
    }

    private fun setUpExploreRecyclerView() {
        binding.exploreRecyclerView.apply {
            adapter = ExploreAdapter(createExplore(40))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun searchViewResult() {
        val searchList = userList
        val searchAdapter = SearchAdapter(searchList, requireActivity() as AppCompatActivity)

        // SearchViewResultRecyclerView:
        binding.searchViewResultsRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        //Implement SearchAdapter
        binding.searchView.editText.doOnTextChanged { text, start, before, count ->
            //TODO: Remember this retrieveUsers() function:
            searchAdapter.filter(text.toString())
        }

    }

    // Create a function to retrieve users from Firebase
    private fun retrieveUsers() {
        val usersRef = FirebaseDatabase.getInstance().getReference().child("users")
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users: MutableList<UserModel> = mutableListOf()
                users.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        if (user != null) {
                            users.add(user)
                        }
                    }
                // Remove the current user from the list
                userList = users.filter { it.uid != currentUser?.uid }.toMutableList()
                searchViewResult()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}