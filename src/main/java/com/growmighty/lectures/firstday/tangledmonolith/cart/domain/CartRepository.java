package com.growmighty.lectures.firstday.tangledmonolith.cart.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(Long userId);

    Cart save(Cart cart);
}
