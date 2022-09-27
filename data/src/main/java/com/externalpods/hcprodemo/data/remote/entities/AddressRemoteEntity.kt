package com.externalpods.hcprodemo.data.remote.entities

import com.google.gson.annotations.SerializedName

data class AddressRemoteEntity(
  @SerializedName("city")
  var city: String? = null,
  @SerializedName("geo")
  var geo: GeoRemoteEntity? = null,
  @SerializedName("street")
  var street: String? = null,
  @SerializedName("suite")
  var suite: String? = null,
  @SerializedName("zipcode")
  var zipcode: String? = null
)