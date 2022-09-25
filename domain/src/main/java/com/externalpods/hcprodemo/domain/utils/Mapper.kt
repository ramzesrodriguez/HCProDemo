package com.externalpods.hcprodemo.domain.utils

/**
 * Mapper interface
 */
interface Mapper<T1, T2> {
  /**
   * Transforms the input value in the output value specified
   *
   * @param value input value type
   * @return output value type if valid; otherwise null
   */
  fun map(value: T1): T2

  /**
   * Transforms the output value in the input value specified
   *
   * @param value input value type
   * @return output value type if valid; otherwise null;
   */
  fun reverseMap(value: T2): T1 = throw UnsupportedOperationException()

  /**
   * Transforms the collection input values in the collection output values specified
   *
   * @param values collection output values type
   * @return collection input values type if valid; otherwise null
   */
  fun mapCollection(values: List<T1>) = values.map { t1 -> map(t1) }

  /**
   * Transforms the collection output values in the collection input values specified
   *
   * @param values collection output values type
   * @return collection input values type if valid; otherwise null
   */
  fun reverseMapCollection(values: List<T2>) = values.map { t2 -> reverseMap(t2) }
}