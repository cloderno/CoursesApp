package com.yeldar.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        courseAdapterDelegate(
            onDetailsClick = { course ->
                detailsClick(course)
            },
            onFavouriteClick = {

            }
        )
    )

    fun detailsClick(course: CourseUi) {
        Toast.makeText(context, course.rating, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.loadData()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when(state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.recyclerView.adapter = adapter
                            adapter.items = state.data
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTextView.text = "Error"
                            binding.errorTextView.visibility = View.VISIBLE
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