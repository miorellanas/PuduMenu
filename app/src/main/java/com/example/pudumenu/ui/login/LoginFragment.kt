package com.example.pudumenu.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pudumenu.R
import com.example.pudumenu.databinding.FragmentLoginBinding
import com.example.pudumenu.data.UserRepository

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            if (validateInput()) {
                for(user in UserRepository.users){
                    if(user.email == binding.emailEditText.text.toString() && user.password == binding.passwordEditText.text.toString()){
                        findNavController().navigate(R.id.action_login_to_main)
                    }
                    else if(user.email != binding.emailEditText.text.toString()){
                        binding.emailLayout.error = getString(R.string.error_invalid_email)
                    }
                    else if(user.password != binding.passwordEditText.text.toString()){
                        binding.passwordLayout.error = getString(R.string.error_field_required)
                    }
                }
            }



        }

        binding.registerLinkText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    private fun validateInput(): Boolean {
        // Clear previous errors
        binding.emailLayout.error = null
        binding.passwordLayout.error = null

        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        var isValid = true

        if (email.isEmpty()) {
            binding.emailLayout.error = getString(R.string.error_field_required)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = getString(R.string.error_invalid_email)
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = getString(R.string.error_field_required)
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
