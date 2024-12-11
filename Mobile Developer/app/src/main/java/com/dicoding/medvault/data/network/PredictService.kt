package com.dicoding.medvault.data.network

import com.dicoding.medvault.data.network.datamodel.PredictRequest
import com.dicoding.medvault.data.network.datamodel.PredictResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PredictService {

    @POST("predict")
    suspend fun predict(@Body request: PredictRequest): List<PredictResponse>
}