package com.example.restro

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.restro.databinding.FragmentStatusBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StatusFragment : Fragment() {

    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseDatabase
    private lateinit var tables: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatusBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance()
        tables = database.getReference("Table")

        var status = requireArguments().getString("status")
        binding.apply {
            if(status == "AVAILABLE"){
                iv.setImageDrawable(context?.let { it ->
                    ContextCompat.getDrawable(
                        it,R.drawable.free_table)
                })
                tv.text = "AVAILABLE"
                tv.setTextColor(Color.parseColor("#58D13E"))
                cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                btnBook.text = "BOOK THE TABLE"
            }
            else{
                iv.setImageDrawable(context?.let { it1 -> ContextCompat.getDrawable(it1,R.drawable.book_table) })
                tv.text = "BOOKED"
                tv.setTextColor(Color.parseColor("#D53636"))
                cv.setCardBackgroundColor(Color.parseColor("#FFB8B8"))
                btnBook.text = "FREE THE TABLE"
            }
            btnBook.setOnClickListener {
                if(status == "BOOKED"){
                    val bundle = bundleOf("update" to "AVAILABLE")
                    it.findNavController().navigate(R.id.action_statusFragment_to_homeFragment2,bundle)
                }
                else{
                    val bundle = bundleOf("update" to "BOOKED")
                    it.findNavController().navigate(R.id.action_statusFragment_to_homeFragment2,bundle)
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}