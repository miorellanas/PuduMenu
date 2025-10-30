package com.example.pudumenu.ui.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pudumenu.R
import com.example.pudumenu.data.model.User
import com.example.pudumenu.data.UserRepository
import com.example.pudumenu.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            if (validateInput()) {
                val email = binding.emailEditText.text.toString().trim()
                val name = binding.nameEditText.text.toString().trim()
                val lastname = binding.lastnameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()

                val newUser = User(email = email, firstName = name, lastName = lastname, password = password)
                UserRepository.addUser(newUser)

                Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
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
        binding.emailLayout.error = null
        binding.nameLayout.error = null
        binding.lastnameLayout.error = null
        binding.passwordLayout.error = null
        binding.repeatPasswordLayout.error = null

        val email = binding.emailEditText.text.toString().trim()
        val name = binding.nameEditText.text.toString().trim()
        val lastname = binding.lastnameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val repeatPassword = binding.repeatPasswordEditText.text.toString().trim()

        var isValid = true

        if (email.isEmpty()) {
            binding.emailLayout.error = getString(R.string.error_field_required)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Correo no válido"
            isValid = false
        } else if (UserRepository.findUserByEmail(email) != null) {
            binding.emailLayout.error = "El correo ya está registrado"
            isValid = false
        }

        if (name.isEmpty()) {
            binding.nameLayout.error = getString(R.string.error_field_required)
            isValid = false
        }

        if (lastname.isEmpty()) {
            binding.lastnameLayout.error = getString(R.string.error_field_required)
            isValid = false
        }

        if (password.length < 6) {
            binding.passwordLayout.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        if (password != repeatPassword) {
            binding.repeatPasswordLayout.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
