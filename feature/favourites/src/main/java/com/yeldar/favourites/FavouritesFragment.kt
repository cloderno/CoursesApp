package com.yeldar.favourites

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
import com.yeldar.favourites.databinding.FragmentFavouritesBinding
import com.yeldar.favourites.viewmodel.FavouritesViewModel
import com.yeldar.ui.adapter.CourseDiffUtil
import com.yeldar.ui.adapter.courseAdapterDelegate
import com.yeldar.ui.model.CourseUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val viewModel: FavouritesViewModel by viewModels()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val adapter = AsyncListDifferDelegationAdapter<CourseUi>(
        CourseDiffUtil(),
        courseAdapterDelegate(
            onDetailsClick = { course ->
                detailsClick(course)
            },
            onFavouriteClick = { course ->
                favouriteClick(course)
            }
        )
    )

    fun detailsClick(course: CourseUi) {
        Toast.makeText(context, course.rating, Toast.LENGTH_SHORT).show()
    }

    fun favouriteClick(course: CourseUi) {
        viewModel.onFavoriteClick(course)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.items = state
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}