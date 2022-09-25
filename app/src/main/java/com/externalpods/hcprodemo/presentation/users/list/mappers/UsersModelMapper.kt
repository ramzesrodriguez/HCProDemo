package com.externalpods.hcprodemo.presentation.users.list.mappers

import com.externalpods.hcprodemo.domain.dtos.AddressDto
import com.externalpods.hcprodemo.domain.dtos.CompanyDto
import com.externalpods.hcprodemo.domain.dtos.GeoDto
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.utils.Mapper
import com.externalpods.hcprodemo.presentation.users.list.models.AddressModel
import com.externalpods.hcprodemo.presentation.users.list.models.CompanyModel
import com.externalpods.hcprodemo.presentation.users.list.models.GeoModel
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import javax.inject.Inject

class UsersModelMapper @Inject constructor() : Mapper<UserDto, UserModel> {
  /**
   * Transforms the input value in the output value specified
   *
   * @param value input value type
   * @return output value type if valid; otherwise null
   */
  override fun map(value: UserDto): UserModel {
    return UserModel(
      mapAddress(value.address),
      mapCompany(value.company),
      value.email,
      value.id,
      value.name,
      value.username,
      value.username,
      value.website
    )
  }

  private fun mapAddress(address: AddressDto?): AddressModel? {
    return AddressModel(
      address?.city,
      mapGeo(address?.geo),
      address?.street,
      address?.suite,
      address?.zipcode
    )
  }


  private fun mapCompany(company: CompanyDto?): CompanyModel {
    return CompanyModel(company?.bs, company?.catchPhrase, company?.name)
  }

  private fun mapGeo(geo: GeoDto?): GeoModel {
    return GeoModel(geo?.lat, geo?.lng)
  }
}