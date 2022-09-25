package com.externalpods.hcprodemo.domain.dtos

data class AddressDto(
  var city: String? = null,
  var geo: GeoDto? = null,
  var street: String? = null,
  var suite: String? = null,
  var zipcode: String? = null
)