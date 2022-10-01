package com.externalpods.hcprodemo.presentation.users.list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
  var address: AddressModel? = null,
  var company: CompanyModel? = null,
  var email: String? = null,
  var id: Int? = null,
  var name: String? = null,
  var phone: String? = null,
  var username: String? = null,
  var website: String? = null
) : Parcelable

@Parcelize
data class AddressModel(
  var city: String? = null,
  var geo: GeoModel? = null,
  var street: String? = null,
  var suite: String? = null,
  var zipcode: String? = null
) : Parcelable

@Parcelize
data class CompanyModel(
  var bs: String? = null,
  var catchPhrase: String? = null,
  var name: String? = null
) : Parcelable

@Parcelize
data class GeoModel(
  var lat: String? = null,
  var lng: String? = null
) : Parcelable