package com.externalpods.hcprodemo.data.remote.entities

import com.google.gson.annotations.SerializedName

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