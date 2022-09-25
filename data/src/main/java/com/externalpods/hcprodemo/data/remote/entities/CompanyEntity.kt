package com.externalpods.hcprodemo.data.remote.entities

import com.google.gson.annotations.SerializedName

data class CompanyEntity(
  @SerializedName("bs")
  var bs: String? = null,
  @SerializedName("catchPhrase")
  var catchPhrase: String? = null,
  @SerializedName("name")
  var name: String? = null
)