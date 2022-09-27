package com.externalpods.hcprodemo.data.remote.entities
import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("address")
    var address: AddressRemoteEntity? = null,
    @SerializedName("company")
    var company: CompanyRemoteEntity? = null,
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