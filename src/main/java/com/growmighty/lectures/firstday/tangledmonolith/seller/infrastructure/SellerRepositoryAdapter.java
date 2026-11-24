package com.growmighty.lectures.firstday.tangledmonolith.seller.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.seller.domain.Seller;
import com.growmighty.lectures.firstday.tangledmonolith.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryAdapter implements SellerRepository {

    private final SellerJpaRepository sellerJpaRepository;

    @Override
    public Seller save(Seller seller) {
        return sellerJpaRepository.save(seller);
    }
}
