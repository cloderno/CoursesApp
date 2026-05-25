package com.yeldar.auth

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yeldar.auth.databinding.FragmentLoginBinding
import com.yeldar.common.utils.blockCyrillicInput


class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setupFilters()
        setupListeners()
        observeViewModel()
    }

    private fun setupFilters() {
        binding.emailEditText.blockCyrillicInput()
    }

    private fun setupListeners() {
        binding.emailEditText.doAfterTextChanged { text ->
            viewModel.emailFlow.value = text?.toString().orEmpty()
        }
        binding.passwordEditText.doAfterTextChanged { text ->
            viewModel.passwordFlow.value = text?.toString().orEmpty()
        }
        binding.loginButton.setOnClickListener {
            viewModel.onLoginClicked()

            findNavController().navigate(
                R.id.act
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}