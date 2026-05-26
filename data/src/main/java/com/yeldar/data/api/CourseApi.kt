package com.yeldar.data.api

import com.yeldar.data.dto.CoursesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseApi {
    @GET("uc")
    suspend fun getCourses(
        @Query("id") id: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") export: String = "download"
    ): CoursesResponse
}