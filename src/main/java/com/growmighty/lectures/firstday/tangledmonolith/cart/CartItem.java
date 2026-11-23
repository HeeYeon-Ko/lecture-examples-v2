package com.growmighty.lectures.firstday.tangledmonolith.cart;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    public static CartItem create(Long productId, int quantity) {
        return new CartItem(productId, quantity);
    }

    private CartItem(Long productId, Integer quantity) {
        validateQuantity(quantity);
        this.productId = productId;
        this.quantity = quantity;
    }

    // 수량 더하기
    void addQuantity(int amount) {
        validateQuantity(this.quantity + amount);
        this.quantity += amount;
    }

    // 수량 덮어쓰기
    void changeQuantity(int newQuantity) {
        validateQuantity(newQuantity);
        this.quantity = newQuantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다. 입력값: " + quantity);
        }
    }

    boolean hasProduct(Long productId) {
        return this.productId.equals(productId);
    }

    void assignCart(Cart cart) {
        this.cart = cart;
    }
}
