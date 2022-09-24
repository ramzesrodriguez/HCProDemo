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

data class AddressEntity(
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("geo")
    var geo: GeoEntity? = null,
    @SerializedName("street")
    var street: String? = null,
    @SerializedName("suite")
    var suite: String? = null,
    @SerializedName("zipcode")
    var zipcode: String? = null
)

data class CompanyEntity(
    @SerializedName("bs")
    var bs: String? = null,
    @SerializedName("catchPhrase")
    var catchPhrase: String? = null,
    @SerializedName("name")
    var name: String? = null
)

data class GeoEntity(
    @SerializedName("lat")
    var lat: String? = null,
    @SerializedName("lng")
    var lng: String? = null
)