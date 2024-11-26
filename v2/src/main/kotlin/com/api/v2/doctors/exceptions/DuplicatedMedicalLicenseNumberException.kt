package com.api.v2.doctors.exceptions

class DuplicatedMedicalLicenseNumberException: RuntimeException("The given medical license number is already in use.")