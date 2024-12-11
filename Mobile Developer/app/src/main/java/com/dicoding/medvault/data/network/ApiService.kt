package com.dicoding.medvault.data.network

import com.dicoding.medvault.data.network.datamodel.ArticleResponse
import com.dicoding.medvault.data.network.datamodel.CreateMedicsResponse
import com.dicoding.medvault.data.network.datamodel.CreateNonMedicsRequest
import com.dicoding.medvault.data.network.datamodel.GetPatientResponse
import com.dicoding.medvault.data.network.datamodel.LoginRequest
import com.dicoding.medvault.data.network.datamodel.LoginResponse
import com.dicoding.medvault.data.network.datamodel.MedicsRequest
import com.dicoding.medvault.data.network.datamodel.MedisDetailResponse
import com.dicoding.medvault.data.network.datamodel.MessageResponse
import com.dicoding.medvault.data.network.datamodel.NonMedisDetailResponse
import com.dicoding.medvault.data.network.datamodel.RegisterRequest
import com.dicoding.medvault.data.network.datamodel.RegisterResponse
import com.dicoding.medvault.data.network.datamodel.SavePatientRequest
import com.dicoding.medvault.data.network.datamodel.SavePatientResponse
import com.dicoding.medvault.data.network.datamodel.UpdateMedicsResponse
import com.dicoding.medvault.data.network.datamodel.UpdateNonMedicsRequest
import com.dicoding.medvault.data.network.datamodel.UpdateNonMedicsResponse
import com.dicoding.medvault.data.network.datamodel.UpdatePatientRequest
import com.dicoding.medvault.data.network.datamodel.UpdatePatientResponse
import com.dicoding.medvault.data.network.datamodel.UpdateUserResponse
import com.dicoding.medvault.data.network.datamodel.UserData
import com.dicoding.medvault.data.network.datamodel.VisitResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @Multipart
    @PUT("api/user/update/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("username") username: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") passwordConfirmation: RequestBody,
        @Part imgProfile: MultipartBody.Part?
    ): UpdateUserResponse

    @POST("api/logout")
    suspend fun logout(): MessageResponse

    @GET("api/patients")
    suspend fun getPatients(): GetPatientResponse

    @POST("api/patients/store")
    suspend fun savePatient(
        @Body request: SavePatientRequest
    ): SavePatientResponse

    @PUT("api/patients/update/{id}")
    suspend fun updatePatient(
        @Path("id") id: Int,
        @Body request: UpdatePatientRequest
    ): UpdatePatientResponse

    @DELETE("api/patients/delete/{id}")
    suspend fun deletePatient(@Path("id") id: Int): MessageResponse

    @PUT("api/nonmedics/update/{id}")
    suspend fun updateNonMedics(
        @Path("id") id: Int,
        @Body request: UpdateNonMedicsRequest
    ): UpdateNonMedicsResponse

    @POST("api/nonmedics/store")
    suspend fun createNonMedics(@Body request: CreateNonMedicsRequest): MessageResponse

    @PUT("api/medics/update/{id}")
    suspend fun updateMedics(@Body updateMedicsRequest: MedicsRequest): UpdateMedicsResponse

    @POST("api/medics/store")
    suspend fun createMedics(@Body updateMedicsRequest: MedicsRequest): CreateMedicsResponse

    @GET("api/kunjungan")
    suspend fun getVisits(): VisitResponse

    @DELETE("api/kunjungan/{type}/{id}")
    suspend fun deleteKunjungan(@Path("id") id: Int, @Path("type") type: String): MessageResponse

    @GET("api/kunjungandetail/non-medis/{id}")
    suspend fun getNonMedisDetail(@Path("id") id: Int): NonMedisDetailResponse

    @GET("api/kunjungandetail/medis/{id}")
    suspend fun getMedisDetail(@Path("id") id: Int): MedisDetailResponse

    @GET("api/berita")
    suspend fun getArticle(): ArticleResponse
}
