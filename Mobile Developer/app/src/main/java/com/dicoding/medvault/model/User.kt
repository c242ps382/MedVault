package com.dicoding.medvault.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val alamat: String,
    val imgProfile: String,
    val token: String,
    val password: String
)