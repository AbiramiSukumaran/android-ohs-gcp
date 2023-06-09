/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.fhir

import java.lang.NullPointerException
import java.math.BigDecimal
import java.math.MathContext
import kotlin.jvm.Throws
import org.fhir.ucum.Decimal
import org.fhir.ucum.Pair
import org.fhir.ucum.UcumEssenceService
import org.fhir.ucum.UcumException

/**
 * Canonicalizes unit values to UCUM base units.
 *
 * For details of UCUM, see http://unitsofmeasure.org/
 *
 * For using UCUM with FHIR, see https://www.hl7.org/fhir/ucum.html
 *
 * For the implementation of UCUM with FHIR, see https://github.com/FHIR/Ucum-java
 */
object UnitConverter {
  private val ucumService by lazy {
    UcumEssenceService(this::class.java.getResourceAsStream("/ucum-essence.xml"))
  }

  /**
   * Returns the canonical form of a UCUM Value.
   *
   * The canonical form is generated by normalizing [value] to UCUM base units, used to generate
   * canonical matches on Quantity Search
   *
   * For example a value of 1000 mm will return 1 m.
   */
  @Throws(ConverterException::class)
  fun getCanonicalForm(value: UcumValue): UcumValue {
    try {
      val pair =
        ucumService.getCanonicalForm(Pair(Decimal(value.value.toPlainString()), value.code))
      return UcumValue(
        pair.code,
        pair.value.asDecimal().toBigDecimal(MathContext(value.value.precision()))
      )
    } catch (e: UcumException) {
      throw ConverterException("UCUM conversion failed", e)
    } catch (e: NullPointerException) {
      // See https://github.com/google/android-fhir/issues/869 for why NPE needs to be caught
      throw ConverterException("Missing numerical value in the canonical UCUM value", e)
    }
  }
}

class ConverterException(message: String, cause: Throwable) : Exception(message, cause)

data class UcumValue(val code: String, val value: BigDecimal)
