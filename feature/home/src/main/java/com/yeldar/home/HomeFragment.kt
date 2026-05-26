package com.yeldar.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.yeldar.home.databinding.FragmentHomeBinding
import com.yeldar.home.viewmodel.HomeViewModel
import com.yeldar.ui.adapter.CourseDiffUtil
import com.yeldar.ui.adapter.courseAdapterDelegate
import com.yeldar.ui.model.CourseUi
import com.yeldar.ui.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = AsyncListDifferDelegationAdapter<CourseUi>(
        CourseDiffUtil(),
        courseAdapterDelegate()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.loadData()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when(state) {
                        is UiState.Loading -> {
                            android.util.Log.d("HomeFragment", "Загрузка...")
                        }
                        is UiState.Success -> {
                            android.util.Log.d("HomeFragment", "Данные получены: ${state.data.size} курсов")
                            binding.recyclerView.adapter = adapter
                            adapter.items = state.data
                            android.util.Log.d("HomeFragment", "Адаптеру передано элементов: ${adapter.itemCount}")
                        }
                        is UiState.Error -> {
                            android.util.Log.e("HomeFragment", "Ошибка: ${state.message}")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}