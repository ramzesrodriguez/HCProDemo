package com.externalpods.hcprodemo.data.remote.entities
import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("address")
    var address: AddressEntity? = null,
    @SerializedName("company")
    var company: CompanyEntity? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("website")
    var website: String? = null
)