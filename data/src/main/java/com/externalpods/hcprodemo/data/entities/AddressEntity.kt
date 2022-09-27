package com.externalpods.hcprodemo.data.entities

data class AddressEntity(
  var city: String? = null,
  var geo: GeoEntity? = null,
  var street: String? = null,
  var suite: String? = null,
  var zipcode: String? = null
)