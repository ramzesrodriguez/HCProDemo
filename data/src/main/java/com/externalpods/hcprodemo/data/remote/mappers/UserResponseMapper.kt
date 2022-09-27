package com.externalpods.hcprodemo.data.remote.mappers

import com.externalpods.hcprodemo.data.entities.AddressEntity
import com.externalpods.hcprodemo.data.entities.CompanyEntity
import com.externalpods.hcprodemo.data.entities.GeoEntity
import com.externalpods.hcprodemo.data.entities.UserEntity
import com.externalpods.hcprodemo.data.remote.entities.AddressRemoteEntity
import com.externalpods.hcprodemo.data.remote.entities.CompanyRemoteEntity
import com.externalpods.hcprodemo.data.remote.entities.GeoRemoteEntity
import com.externalpods.hcprodemo.data.remote.entities.UserRemoteEntity
import com.externalpods.hcprodemo.domain.utils.Mapper
import javax.inject.Inject

class UserResponseMapper @Inject constructor() : Mapper<UserRemoteEntity, UserEntity> {
  /**
   * Transforms the input value in the output value specified
   *
   * @param value input value type
   * @return output value type if valid; otherwise null
   */
  override fun map(value: UserRemoteEntity): UserEntity {
    return UserEntity(
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

  private fun mapAddress(address: AddressRemoteEntity?): AddressEntity {
    return AddressEntity(
      address?.city,
      mapGeo(address?.geo),
      address?.street,
      address?.suite,
      address?.zipcode
    )
  }

  private fun mapCompany(company: CompanyRemoteEntity?): CompanyEntity {
    return CompanyEntity(company?.bs, company?.catchPhrase, company?.name)
  }

  private fun mapGeo(geo: GeoRemoteEntity?): GeoEntity {
    return GeoEntity(geo?.lat, geo?.lng)
  }
}