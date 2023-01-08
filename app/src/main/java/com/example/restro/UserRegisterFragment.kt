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
import com.example.restro.databinding.FragmentUserRegisterBinding
import com.example.restro.db.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserRegisterFragment : Fragment() {

    private var _binding: FragmentUserRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var users: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegisterBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        users = database.getReference("User")
        mAuth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_userRegisterFragment_to_userLoginFragment)
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
            val user = User(
                username.editText?.text.toString(),
                email.editText?.text.toString(),
                number.editText?.text.toString(),
                password.editText?.text.toString()
            )

            users.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(user.number?.let { snapshot.child(it).exists() } == true){
                        Toast.makeText(context,"${number.editText?.text.toString()} already exists",
                            Toast.LENGTH_LONG).show()
                    } else {
                        user.number?.let { users.child(it).setValue(user) }
                        Toast.makeText(context,"Account created successfully", Toast.LENGTH_LONG).show()
                        val bundle = bundleOf("owner" to "User","name" to user.username)
                        findNavController().navigate(R.id.action_userRegisterFragment_to_searchFragment,bundle)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}