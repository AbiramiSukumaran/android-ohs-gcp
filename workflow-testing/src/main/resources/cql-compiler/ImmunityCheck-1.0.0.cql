library ImmunityCheck version '1.0.0'

using FHIR version '4.0.1'

include "FHIRHelpers" version '4.0.1' called FHIRHelpers

context Patient

define "CompletedImmunization":
  exists(GetFinalDose) or exists(GetSingleDose)

define "GetFinalDose":
  [Immunization] I
    where exists(I.protocolApplied)
      and I.protocolApplied.doseNumber.value = I.protocolApplied.seriesDoses.value

define "GetSingleDose":
  [Immunization] I
    where exists(I.protocolApplied)
      and exists(I.protocolApplied.doseNumber.value)
      and not exists(I.protocolApplied.seriesDoses.value)
