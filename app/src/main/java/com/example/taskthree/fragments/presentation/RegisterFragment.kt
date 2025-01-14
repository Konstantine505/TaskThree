package com.example.taskthree.fragments.presentation

import androidx.navigation.fragment.findNavController
import com.example.homeworkthree.presentation.base.BaseFragment
import com.example.taskthree.R
import com.example.taskthree.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun setUp() {
    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            registerUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    fun registerUser(email: String, password: String) {
        // Get instance of FirebaseAuth
        val auth = FirebaseAuth.getInstance()

        // Create a new user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    user?.let {
                        println("Registration successful: ${it.email}")
                        findNavController().navigate(R.id.action_registerFragment_to_logInFragment)

                    }
                } else {
                    // Registration failed
                    task.exception?.let { exception ->
                        println("Registration failed: ${exception.message}")
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle specific errors
                println("Error: ${exception.message}")
            }
    }
}