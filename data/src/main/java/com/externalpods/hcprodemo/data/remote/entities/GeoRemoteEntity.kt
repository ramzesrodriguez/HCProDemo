package com.externalpods.hcprodemo.data.remote.entities

import com.google.gson.annotations.SerializedName

data class GeoRemoteEntity(
  @SerializedName("lat")
  var lat: String? = null,
  @SerializedName("lng")
  var lng: String? = null
)