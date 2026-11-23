package com.growmighty.lectures.firstday.tangledmonolith.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public static Payment ready(BigDecimal amount) {
        Payment payment = new Payment();
        payment.amount = amount;
        payment.status = PaymentStatus.READY;
        return payment;
    }

    public void approve(){
        requireReady();
        this.status = PaymentStatus.PAID;
    }

    public void fail(){
        requireReady();
        this.status = PaymentStatus.FAILED;
    }

    /*
        - approve(), fail()에서 공통으로 필요한 로직이기에 별도 메서드로 분리
        - approve(), fail() 내부에서만 쓰이기에 private으로 선언
     */
    private void requireReady(){
        if(this.status != PaymentStatus.READY){
            throw new IllegalStateException("READY 상태에서만 결제 상태를 변경할 수 있습니다. status= " + this.status);
        }
    }
}
