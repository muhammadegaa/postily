package com.example.postily.model.profile

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,  // Use @SerializedName if JSON keys differ
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: Company
)