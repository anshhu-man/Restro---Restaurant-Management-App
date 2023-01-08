package com.example.restro

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.restro.databinding.FragmentOwnerRegisterBinding
import com.example.restro.db.Owner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OwnerRegisterFragment : Fragment() {

    private var _binding: FragmentOwnerRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var owners: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOwnerRegisterBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        owners = database.getReference("Owner")
        mAuth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_ownerRegisterFragment_to_ownerLoginFragment)
            }

            btnSignUp.setOnClickListener {
                if (!username.editText?.text.toString().isEmpty()) {
                    username.error = ""

                    if (!email.editText?.text.toString().isEmpty()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(email.editText?.text.toString())
                                .matches()
                        ) {
                            email.error = null
                        } else {
                            email.error = "Invalid Email"
                        }

                        if (!number.editText?.text.toString().isEmpty()) {
                            number.error = ""

                            if (!password.editText?.text.toString().isEmpty()) {
                                password.error = null

                                if (confirmPassword.editText?.text.toString() == password.editText?.text.toString()
                                ) {
                                    confirmPassword.error = null

                                    signUp()

                                } else {
                                    confirmPassword.error = "Password does not match."
                                }
                            } else {
                                password.error = "This field cannot be empty."
                            }
                        } else{
                            number.error = "This field cannot be empty."
                        }
                    } else {
                        email.error = "This field cannot be empty."
                    }
                } else {
                    username.error = "This field cannot be empty."
                }
            }
        }
        return binding.root
    }

    private fun signUp() {
        binding.apply {
            val owner = Owner(
                username.editText?.text.toString(),
                email.editText?.text.toString(),
                number.editText?.text.toString(),
                password.editText?.text.toString()
            )

            owners.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(owner.number?.let { snapshot.child(it).exists() } == true){
                        Toast.makeText(context,"${number.editText?.text.toString()} already exists",
                            Toast.LENGTH_LONG).show()
                    } else {
                        owner.number?.let { owners.child(it).setValue(owner) }
                        Toast.makeText(context,"Account created successfully", Toast.LENGTH_LONG).show()
                        val bundle = bundleOf("owner" to "Owner","name" to owner.username)
                        findNavController().navigate(R.id.action_ownerRegisterFragment_to_homeFragment,bundle)
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
