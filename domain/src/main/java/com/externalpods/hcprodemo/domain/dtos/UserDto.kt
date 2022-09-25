package com.externalpods.hcprodemo.domain.dtos

data class UserDto(
    var address: AddressDto? = null,
    var company: CompanyDto? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null
)