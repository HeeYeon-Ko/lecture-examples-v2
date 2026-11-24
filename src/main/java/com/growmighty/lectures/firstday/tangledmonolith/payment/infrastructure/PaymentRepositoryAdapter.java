package com.growmighty.lectures.firstday.tangledmonolith.payment.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.payment.domain.Payment;
import com.growmighty.lectures.firstday.tangledmonolith.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
