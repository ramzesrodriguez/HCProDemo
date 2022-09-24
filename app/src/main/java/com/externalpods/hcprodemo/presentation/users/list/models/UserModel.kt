package com.externalpods.hcprodemo.presentation.users.list.models

data class UserModel(
    var address: AddressModel? = null,
    var company: CompanyModel? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null
)

data class AddressModel(
    var city: String? = null,
    var geo: GeoModel? = null,
    var street: String? = null,
    var suite: String? = null,
    var zipcode: String? = null
)

data class CompanyModel(
    var bs: String? = null,
    var catchPhrase: String? = null,
    var name: String? = null
)

data class GeoModel(
    var lat: String? = null,
    var lng: String? = null
)