package com.api.v2.doctors.services;

import com.api.v2.common.MLN;
import com.api.v2.doctors.controllers.DoctorController;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;

    public DoctorRetrievalServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Mono<HalResourceWrapper<DoctorResponseDto, Void>> findByMedicalLicenseNumber(@MLN String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(DoctorResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            DoctorController.class,
                                            controller -> controller.findByMedicalLicenseNumber(medicalLicenseNumber)
                                    ).withSelfRel(),
                                    linkTo(
                                            DoctorController.class,
                                            controller -> controller.findByMedicalLicenseNumber(medicalLicenseNumber)
                                    ).withRel("find_doctor_by_medical_license_number"),
                                    linkTo(
                                            DoctorController.class,
                                            controller -> controller.terminate(medicalLicenseNumber)
                                    ).withRel("terminated_doctor_by_medical_license_number")
                            );
                });
    }

    @Override
    public Flux<DoctorResponseDto> findAll() {
        return doctorRepository
                .findAll()
                .flatMap(DoctorResponseMapper::mapToMono);
    }
}
