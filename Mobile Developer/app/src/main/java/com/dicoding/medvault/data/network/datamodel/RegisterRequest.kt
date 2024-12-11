package com.dicoding.medvault.data.network.datamodel

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val username: String,
    val alamat: String
)
