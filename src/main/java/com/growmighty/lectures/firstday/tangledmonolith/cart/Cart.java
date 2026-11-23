package com.growmighty.lectures.firstday.tangledmonolith.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Long userId;

    public static Cart create(Long userId) {
        Cart cart = new Cart();
        cart.userId = userId;
        return cart;
    }

    /*
        정답 코드 참고
     */
    // 장바구니에 상품 추가
    public void addItem(Long productId, Integer quantity) {
        /*
            장바구니에 존재하지 않는 상품(null)이여도 예외 처리가 아니라
            장바구니에 추가해야하니까 findItem 메서드 사용
         */
        CartItem existingItem = findItem(productId);

        // 이미 장바구니에 존재하는 상품이라면
        if(existingItem != null) {
            existingItem.addQuantity(quantity);
            return;
        }

        // 장바구니에 존재하지 않는 상품이라면
        CartItem cartItem = CartItem.create(productId, quantity);
        cartItem.assignCart(this);
        this.items.add(cartItem);
    }

    // 장바구니에 담긴 상품의 수량 변경
    public void changeQuantity(Long productId, Integer quantity) {
        /*
            장바구니에 해당 상품이 담겨있지 않으면 수량 변경할 수 없으므로
            예외처리가 가능한 requireItem 메서드 사용
         */
        requireItem(productId).changeQuantity(quantity);
    }

    // 장바구니에서 상품 삭제
    public void removeItem(Long productId) {
        // 장바구니에 해당 상품이 없으면 예외, 있으면 삭제
        CartItem cartItem = requireItem(productId);
        this.items.remove(cartItem);
    }

    private CartItem findItem(Long productId) {
        return items.stream()
                .filter(item -> item.hasProduct(productId))
                .findFirst()
                .orElse(null);
    }

    private CartItem requireItem(Long productId) {
        CartItem item = findItem(productId);
        if (item == null) {
            throw new IllegalArgumentException("장바구니에 없는 상품입니다. productId=" + productId);
        }
        return item;
    }

    public void clear() {
        this.items.clear();
    }
}
