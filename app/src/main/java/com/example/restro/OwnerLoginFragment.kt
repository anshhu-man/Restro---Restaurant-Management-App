package com.example.restro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.restro.databinding.FragmentOwnerLoginBinding
import com.example.restro.db.Owner
import com.google.firebase.database.*

class OwnerLoginFragment : Fragment() {

    private var _binding: FragmentOwnerLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var owners: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOwnerLoginBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        owners = database.getReference("Owner")

        binding.apply {
            btnSignUp.setOnClickListener{
                it.findNavController().navigate(R.id.action_ownerLoginFragment_to_ownerRegisterFragment)
            }

            btnLogin.setOnClickListener {
                if(!number.editText?.text.toString().isEmpty()){
                    number.error = ""

                    if (!password.editText?.text.toString().isEmpty()){
                        password.error = ""

                        login(it.findNavController())

                    } else{
                        password.error = "This field cannot be empty."
                    }
                } else{
                    number.error = "This field cannot be empty."
                }
            }
        }
        return binding.root
    }

    private fun login(findNavController: NavController) {
        binding.apply {
            owners.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(number.editText?.text.toString()).exists()){
                        val owner = snapshot.child(number.editText?.text.toString()).getValue(Owner::class.java)
                        if (owner!!.password == password.editText?.text.toString()) {
                            Toast.makeText(context, "SignUp Successful", Toast.LENGTH_LONG)
                                .show()
                            val bundle = bundleOf("owner" to "Owner", "name" to owner.username)
                            findNavController.navigate(R.id.action_ownerLoginFragment_to_homeFragment,bundle)

                        } else {
                            Toast.makeText(context, "Password is wrong", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        Toast.makeText(context,"Phone No. not registered", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}