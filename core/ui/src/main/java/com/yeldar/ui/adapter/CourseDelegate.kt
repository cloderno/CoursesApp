package com.yeldar.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yeldar.ui.databinding.ItemCourseBinding
import com.yeldar.ui.databinding.ItemCourseBinding.bind
import com.yeldar.ui.model.CourseUi

// не уверен правильно ли adapterdelegate использовать в общем core/ui?
fun courseAdapterDelegate() = adapterDelegateViewBinding<CourseUi, CourseUi, ItemCourseBinding>(
    { layoutInflater, root -> ItemCourseBinding.inflate(layoutInflater, root, false)}
){
    bind {
        binding.titleTextView.text = item.title
        binding.descriptionTextView.text = item.description
        binding.priceTextView.text = item.price
        binding.ratingTextView.text = item.rating
        binding.dateTextView.text = item.startDate
        binding.bookmarkButton.isEnabled = item.isFavorite
    }
}