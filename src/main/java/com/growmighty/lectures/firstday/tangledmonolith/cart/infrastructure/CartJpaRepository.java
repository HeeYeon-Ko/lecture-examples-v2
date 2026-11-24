package com.growmighty.lectures.firstday.tangledmonolith.cart.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
}
