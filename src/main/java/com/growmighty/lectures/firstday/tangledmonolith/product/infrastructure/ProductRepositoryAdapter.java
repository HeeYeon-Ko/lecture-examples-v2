package com.growmighty.lectures.firstday.tangledmonolith.product.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.payment.infrastructure.PaymentJpaRepository;
import com.growmighty.lectures.firstday.tangledmonolith.product.domain.Product;
import com.growmighty.lectures.firstday.tangledmonolith.product.domain.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId);
    }
}
