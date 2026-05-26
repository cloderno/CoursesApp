package com.yeldar.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.yeldar.ui.model.CourseUi

class CourseDiffUtil : DiffUtil.ItemCallback<CourseUi>() {
    override fun areItemsTheSame(oldItem: CourseUi, newItem: CourseUi) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CourseUi, newItem: CourseUi) = oldItem == newItem
}