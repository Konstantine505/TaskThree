package com.example.taskthree.fragments.presentation

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homeworkthree.presentation.base.BaseFragment
import com.example.taskthree.R
import com.example.taskthree.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    override fun setUp() {
    }

    override fun listeners() {
        with(binding) {
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_registerFragment)

            }
            btnLogIn.setOnClickListener {
                if (!etEmail.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()) {
                    loginUser(etEmail.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                    saveUserStatus(
                        requireContext(), isLoggedIn = true
                    )
                    findNavController().navigate(R.id.action_logInFragment_to_successFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun saveUserStatus(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}