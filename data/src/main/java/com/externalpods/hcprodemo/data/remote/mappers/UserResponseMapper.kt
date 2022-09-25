package com.externalpods.hcprodemo.data.remote.mappers

import com.externalpods.hcprodemo.data.remote.entities.AddressEntity
import com.externalpods.hcprodemo.data.remote.entities.CompanyEntity
import com.externalpods.hcprodemo.data.remote.entities.GeoEntity
import com.externalpods.hcprodemo.data.remote.entities.UserEntity
import com.externalpods.hcprodemo.domain.dtos.AddressDto
import com.externalpods.hcprodemo.domain.dtos.CompanyDto
import com.externalpods.hcprodemo.domain.dtos.GeoDto
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.utils.Mapper
import javax.inject.Inject

class UserResponseMapper @Inject constructor(): Mapper<UserEntity, UserDto> {
  /**
   * Transforms the input value in the output value specified
   *
   * @param value input value type
   * @return output value type if valid; otherwise null
   */
  override fun map(value: UserEntity): UserDto {
    return UserDto(
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

  private fun mapAddress(address: AddressEntity?): AddressDto? {
    return AddressDto(
      address?.city,
      mapGeo(address?.geo),
      address?.street,
      address?.suite,
      address?.zipcode
    )
  }

  private fun mapCompany(company: CompanyEntity?): CompanyDto? {
    return CompanyDto(company?.bs, company?.catchPhrase, company?.name)
  }

  private fun mapGeo(geo: GeoEntity?): GeoDto? {
    return GeoDto(geo?.lat, geo?.lng)
  }
}