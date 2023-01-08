package com.example.restro

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.restro.databinding.FragmentHomeBinding
import com.example.restro.db.Table
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var tables: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        tables = database.getReference("Table")

        val name = requireArguments().getString("name")
        var status1 = true
        var status2 = true
        var status3 = true
        var status4 = true
        var status5 = true
        var status6 = true
        var status7 = true
        var status8 = true
        var status9 = true
        var status10 = true

        binding.apply {
            table1.setOnClickListener {
                status1 = !status1
                validate(status1, iv1, tv1, cv1)
            }
            table2.setOnClickListener {
                status2 = !status2
                validate(status2, iv2, tv2, cv2);
            }
            table3.setOnClickListener {
                status3 = !status3
                validate(status3, iv3, tv3, cv3);
            }
            table4.setOnClickListener {
                status4 = !status4
                validate(status4, iv4, tv4, cv4);
            }
            table5.setOnClickListener {
                status5 = !status5
                validate(status5, iv5, tv5, cv5);
            }
            table6.setOnClickListener {
                status6 = !status6
                validate(status6, iv6, tv6, cv6);
            }
            table7.setOnClickListener {
                status7 = !status7
                validate(status7, iv7, tv7, cv7);
            }
            table8.setOnClickListener {
                status8 = !status8
                validate(status8, iv8, tv8, cv8);
            }
            table9.setOnClickListener {
                status9 = !status9
                validate(status9, iv9, tv9, cv9);
            }
            table10.setOnClickListener {
                status10 = !status10
                validate(status10, iv10, tv10, cv10);
            }
            val table = Table(
                name,
                status1,
                status2,
                status3,
                status4,
                status5,
                status6,
                status7,
                status8,
                status9,
                status10
            )
            tables.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    table.name?.let { tables.child(it).setValue(table) }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        return binding.root
    }

    private fun validate(status: Boolean, iv: ImageView, tv: TextView, cv: CardView, ){
        if(status){
            iv.setImageDrawable(context?.let { ContextCompat.getDrawable(it,R.drawable.free_table) })
            tv.text = "AVAILABLE"
            tv.setTextColor(Color.parseColor("#58D13E"))
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else{
            iv.setImageDrawable(context?.let { it1 -> ContextCompat.getDrawable(it1,R.drawable.book_table) })
            tv.text = "BOOKED"
            tv.setTextColor(Color.parseColor("#D53636"))
            cv.setCardBackgroundColor(Color.parseColor("#FFB8B8"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

