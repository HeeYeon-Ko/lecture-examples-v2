package com.growmighty.lectures.firstday.tangledmonolith.cart.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.cart.domain.Cart;
import com.growmighty.lectures.firstday.tangledmonolith.cart.domain.CartRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final CartJpaRepository cartJpaRepository;


    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartJpaRepository.findById(userId);
    }

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(cart);
    }
}
