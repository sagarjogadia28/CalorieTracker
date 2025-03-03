package com.sagarjogadia28.tracker_data.remote

import com.sagarjogadia28.tracker_data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFoodApi {
    @GET("cgi/search.pl")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search_simple") searchSimple: Int = 1,
        @Query("json") json: Int = 1,
        @Query("action") action: String = "process",
        @Query("fields") fields: String = "product_name,nutriments,image_front_thumb_url"
    ): SearchDto
}