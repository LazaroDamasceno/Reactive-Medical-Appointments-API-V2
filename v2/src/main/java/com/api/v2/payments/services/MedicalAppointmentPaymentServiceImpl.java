package com.api.v2.payments.services;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.utils.CardFinder;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v2.payments.domain.Payment;
import com.api.v2.payments.domain.PaymentRepository;
import com.api.v2.payments.dtos.PaymentResponseDto;
import com.api.v2.payments.utils.PaymentResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentPaymentServiceImpl implements MedicalAppointmentPaymentService {

    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final CardFinder cardFinder;
    private final PaymentRepository paymentRepository;

    public MedicalAppointmentPaymentServiceImpl(MedicalAppointmentFinder medicalAppointmentFinder,
                                                CardFinder cardFinder,
                                                PaymentRepository paymentRepository
    ) {
        this.medicalAppointmentFinder = medicalAppointmentFinder;
        this.cardFinder = cardFinder;
        this.paymentRepository = paymentRepository;
    }


    @Override
    public Mono<PaymentResponseDto> payPrivateInsurance(String customerId, String medicalAppointmentId, String cardId, double price) {
        return null;
    }

    @Override
    public Mono<PaymentResponseDto> payPublicInsurance(String customerId, String medicalAppointmentId, String cardId) {
        return null;
    }

    @Override
    public Mono<PaymentResponseDto> payPaidByPatient(String customerId, String medicalAppointmentId, String cardId, double price) {
        return null;
    }

    private Mono<PaymentResponseDto> pay(String customerId, String medicalAppointmentId, String cardId, double price) {
        Mono<MedicalAppointment> medicalAppointmentMono = medicalAppointmentFinder.findById(medicalAppointmentId);
        Mono<Card> cardMono = cardFinder.find(cardId);
        return medicalAppointmentMono
                .zipWith(cardMono)
                .flatMap(tuple -> {
            MedicalAppointment medicalAppointment = tuple.getT1();
            Card card = tuple.getT2();
            Payment payment = Payment.of(card, medicalAppointment);
            return paymentRepository
                    .save(payment)
                    .flatMap(PaymentResponseMapper::mapToMono);
        });
    }
}
