package com.externalpods.hcprodemo.domain.dtos

class UserModelDto : ArrayList<UserModelDtoItem>()

data class UserModelDtoItem(
    var address: AddressDto? = null,
    var company: CompanyDto? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null
)

data class AddressDto(
    var city: String? = null,
    var geo: GeoDto? = null,
    var street: String? = null,
    var suite: String? = null,
    var zipcode: String? = null
)

data class CompanyDto(
    var bs: String? = null,
    var catchPhrase: String? = null,
    var name: String? = null
)

data class GeoDto(
    var lat: String? = null,
    var lng: String? = null
)