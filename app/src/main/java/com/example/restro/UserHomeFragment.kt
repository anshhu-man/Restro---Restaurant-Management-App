package com.example.restro

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.restro.databinding.FragmentUserHomeBinding
import com.example.restro.db.Table
import com.example.restro.db.User
import com.google.firebase.database.*

class UserHomeFragment : Fragment() {

    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var tables: DatabaseReference
    private lateinit var table: Table

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserHomeBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        tables = database.getReference("Table")

        val name = requireArguments().getString("resname")
//        Toast.makeText(context,name, Toast.LENGTH_LONG).show()

        binding.apply {
            tables.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(name.toString()).exists()) {
                        table = snapshot.child(name.toString()).getValue(Table::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
//            table.t1?.let { validate(it, iv1, tv1, cv1) }
//            table.t2?.let { validate(it, iv2, tv2, cv2) }
//            table.t3?.let { validate(it, iv3, tv3, cv3) }
//            table.t4?.let { validate(it, iv4, tv4, cv4) }
//            table.t5?.let { validate(it, iv5, tv5, cv5) }
//            table.t6?.let { validate(it, iv6, tv6, cv6) }
//            table.t7?.let { validate(it, iv7, tv7, cv7) }
//            table.t8?.let { validate(it, iv8, tv8, cv8) }
//            table.t9?.let { validate(it, iv9, tv9, cv9) }
//            table.t10?.let { validate(it, iv10, tv10, cv10) }

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