package com.yeldar.ui.adapter

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yeldar.ui.R
import com.yeldar.ui.databinding.ItemCourseBinding
import com.yeldar.ui.databinding.ItemCourseBinding.bind
import com.yeldar.ui.model.CourseUi

// не уверен правильно ли adapterdelegate использовать в общем core/ui?
fun courseAdapterDelegate(
    onDetailsClick: (CourseUi) -> Unit,
    onFavouriteClick: (CourseUi) -> Unit
) = adapterDelegateViewBinding<CourseUi, CourseUi, ItemCourseBinding>(
    { layoutInflater, root -> ItemCourseBinding.inflate(layoutInflater, root, false)}
){
    bind {
        binding.titleTextView.text = item.title
        binding.descriptionTextView.text = item.description
        binding.priceTextView.text = item.price
        binding.ratingTextView.text = item.rating
        binding.dateTextView.text = item.startDate
        binding.bookmarkButton.isSelected = item.isFavorite

        // не получаем из сервера картинку, нет поля, используем дефолт
        binding.courseImageView.load("") {
            crossfade(true)
            placeholder(R.drawable.cover)
            error(R.drawable.cover)
        }

        binding.bookmarkButton.setOnClickListener {
            onFavouriteClick(item)
        }
        binding.detailsTextView.setOnClickListener {
            onDetailsClick(item)
        }
    }
}