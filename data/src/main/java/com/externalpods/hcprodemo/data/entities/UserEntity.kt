package com.externalpods.hcprodemo.data.entities

data class UserEntity(
    var address: AddressEntity? = null,
    var company: CompanyEntity? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null
)