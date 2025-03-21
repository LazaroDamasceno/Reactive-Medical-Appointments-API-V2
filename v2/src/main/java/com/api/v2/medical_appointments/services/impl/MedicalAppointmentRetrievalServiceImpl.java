package com.api.v2.medical_appointments.services.impl;

import com.api.v2.customers.utils.CustomerFinder;
import com.api.v2.medical_appointments.controllers.MedicalAppointmentController;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class MedicalAppointmentRetrievalServiceImpl implements MedicalAppointmentRetrievalService {

    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final CustomerFinder customerFinder;

    public MedicalAppointmentRetrievalServiceImpl(
            MedicalAppointmentFinder medicalAppointmentFinder,
            MedicalAppointmentRepository medicalAppointmentRepository, CustomerFinder customerFinder
    ) {
        this.medicalAppointmentFinder = medicalAppointmentFinder;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.customerFinder = customerFinder;
    }

    @Override
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findById(String id) {
        return medicalAppointmentFinder
                .findById(id)
                .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                .map(dto -> {
                    String customerId = dto.customer().person().id();
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.findById(id)
                                    ).withSelfRel(),
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.findAllPaidByPatientByCustomer(customerId)
                                    ).withRel("find_all_paid_by_patient_medical_appointments_by_customer"),
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.findAllPrivateInsuranceByCustomer(customerId)
                                    ).withRel("find_all_private_insurance_medical_appointments_by_customer"),
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.findAllPublicInsuranceByCustomer(customerId)
                                    ).withRel("find_all_public_insurance_medical_appointments_by_customer")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAll() {
        return medicalAppointmentRepository
                .findAll()
                .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_medical_appointment_by_id"),
                                    linkTo(
                                            MedicalAppointmentController.class,
                                            controller -> controller.cancel(dto.customer().person().id(), dto.id())
                                    ).withRel("cancel_medical_appointment_by_id")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(String customerId) {
        return customerFinder
                .findById(customerId)
                .flatMapMany(customer -> {
                    return medicalAppointmentRepository
                            .findAll()
                            .filter(appointment ->
                                    appointment.getType().equals("public insurance")
                                            && appointment.getCustomer().getId().equals(customer.getId())
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                            .map(dto -> {
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPaidByPatientByCustomer(customerId)
                                                ).withRel("find_all_paid_by_patient_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPrivateInsuranceByCustomer(customerId)
                                                ).withRel("find_all_private_insurance_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPublicInsuranceByCustomer(customerId)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.cancel(dto.customer().person().id(), dto.id())
                                                ).withRel("cancel_found_medical_appointment_by_id"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findById(dto.id())
                                                ).withRel("find_medical_appointment_by_id")
                                        );
                            });
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPrivateInsuranceByCustomer(String customerId) {
        return customerFinder
                .findById(customerId)
                .flatMapMany(customer -> {
                    return medicalAppointmentRepository
                            .findAll()
                            .filter(appointment ->
                                    appointment.getType().equals("private insurance")
                                    && appointment.getCustomer().getId().equals(customer.getId())
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                            .map(dto -> {
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPaidByPatientByCustomer(customerId)
                                                ).withRel("find_all_paid_by_patient_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPrivateInsuranceByCustomer(customerId)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPublicInsuranceByCustomer(customerId)
                                                ).withRel("find_all_public_insurance_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.cancel(dto.customer().person().id(), dto.id())
                                                ).withRel("cancel_found_medical_appointment_by_id"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller
                                                )
                                        );
                            });
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPaidByCustomer(String customerId) {
        return customerFinder
                .findById(customerId)
                .flatMapMany(customer -> {
                    return medicalAppointmentRepository
                            .findAll()
                            .filter(appointment ->
                                    appointment.getType().equals("paid by patient")
                                    && appointment.getCustomer().getId().equals(customer.getId())
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                            .map(dto -> {
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPaidByPatientByCustomer(customerId)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPrivateInsuranceByCustomer(customerId)
                                                ).withRel("find_all_private_insurance_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.findAllPublicInsuranceByCustomer(customerId)
                                                ).withRel("find_all_public_insurance_medical_appointments_by_customer"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller.cancel(dto.customer().person().id(), dto.id())
                                                ).withRel("cancel_found_medical_appointment_by_id"),
                                                linkTo(
                                                        MedicalAppointmentController.class,
                                                        controller -> controller
                                                )
                                        );
                            });
                });
    }
}
