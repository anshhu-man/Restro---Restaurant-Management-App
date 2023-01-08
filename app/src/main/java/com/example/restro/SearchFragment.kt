package com.example.restro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.restro.databinding.FragmentSearchBinding
import com.example.restro.db.Table
import com.example.restro.db.User
import com.google.firebase.database.*

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var tables: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        tables = database.getReference("Table")

        binding.apply {
            btnSearch.setOnClickListener {
                tables.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.child(name.editText?.text.toString()).exists()){
                            val bundle = bundleOf("resname" to name.editText?.text.toString())
                            it.findNavController().navigate(R.id.action_searchFragment_to_userHomeFragment,bundle)
                        } else {
                            Toast.makeText(context,"Restaurant Not Found", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }
        }
        return binding.root
    }
}