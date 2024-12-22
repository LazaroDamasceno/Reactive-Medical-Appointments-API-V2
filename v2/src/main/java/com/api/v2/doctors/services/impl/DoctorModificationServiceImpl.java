package com.api.v2.doctors.services.impl;

import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.services.DoctorModificationService;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.people.dtos.PersonModificationDto;
import com.api.v2.people.services.PersonModificationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DoctorModificationServiceImpl implements DoctorModificationService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;
    private final PersonModificationService personModificationService;

    public DoctorModificationServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository,
            PersonModificationService personModificationService
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
        this.personModificationService = personModificationService;
    }

    @Override
    public Mono<Void> modify(String medicalLicenseNumber, @Valid PersonModificationDto modificationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    return personModificationService
                            .modify(doctor.getPerson(), modificationDto)
                            .flatMap(modifiedPerson -> {
                                doctor.setPerson(modifiedPerson);
                                return doctorRepository.save(doctor);
                            });
                })
                .then();
    }
}
