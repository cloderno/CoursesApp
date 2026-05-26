package com.yeldar.auth

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.yeldar.auth.databinding.FragmentLoginBinding
import com.yeldar.common.utils.blockCyrillicInput
import com.yeldar.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import com.yeldar.common.utils.blockSpaces

@AndroidEntryPoint
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

        binding.emailEditText.blockSpaces()
        binding.emailEditText.blockSpaces()
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

            val request = Screens.createDeeLinkRequest(Screens.HOME_DESTINATION)

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.login_nav_graph, true)
                .build()

            findNavController().navigate(
                request, navOptions
            )
        }
        binding.vkButton.setOnClickListener {
            openBrowser(URL_VK)
        }
        binding.okButton.setOnClickListener {
            openBrowser(URL_OK)
        }
    }

    fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoginButtonEnabled.collect { isEnabled ->
                    binding.loginButton.isEnabled = isEnabled
                }
            }
        }
    }

    private fun openBrowser(url: String) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        intent.data = url.toUri()
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val URL_VK = "https://vk.com"
        private const val URL_OK = "https://ok.ru"
    }
}