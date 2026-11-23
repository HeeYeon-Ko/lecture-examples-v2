package com.growmighty.lectures.firstday.tangledmonolith.homework;

import com.growmighty.lectures.firstday.tangledmonolith.cart.Cart;
import com.growmighty.lectures.firstday.tangledmonolith.cart.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * [과제 DDD-2] 장바구니 애그리거트 불변식 테스트
 *
 * 스프링 없이 도는 순수 단위 테스트입니다 (@SpringBootTest 미사용).
 *
 *   Cart cart = Cart.create(userId);
 *   cart.addItem(productId, quantity);
 *   cart.changeQuantity(productId, newQuantity);
 *   cart.removeItem(productId);
 */
class CartAggregateTest {


    @Test
    @DisplayName("새 상품을 담으면 항목이 하나 추가된다")
    void 새_상품을_담으면_항목이_추가된다() {
        // given: 빈 장바구니
        Cart cart = Cart.create(1L);

        // when : 상품 1번을 2개 담는다
        cart.addItem(1L, 2);

        // then : 항목 1개, 상품 1번, 수량 2
        assertThat(cart.getItems()).hasSize(1);
        CartItem item = cart.getItems().get(0);
        assertThat(item.getProductId()).isEqualTo(1L);
        assertThat(item.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("같은 상품을 다시 담으면 새 항목이 아니라 수량이 합쳐진다")
    void 같은_상품을_담으면_수량이_합쳐진다() {
        // given: 상품 1번이 2개 담긴 장바구니
        Cart cart = Cart.create(1L);
        cart.addItem(10L, 2);

        // when : 같은 상품 1번을 3개 더 담는다
        cart.addItem(10L, 3);

        // then : 항목 1개, 상품 1번, 수량 5
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("수량이 0 이하이면 담을 수 없다")
    void 수량이_0_이하면_예외() {
        // given: 빈 장바구니
        Cart cart = Cart.create(1L);

        // when & then : 수량 0 또는 -1 -> 예외 발생
        assertThatThrownBy(() -> cart.addItem(10L, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> cart.addItem(10L, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("담긴 상품의 수량을 바꿀 수 있다")
    void 수량을_변경한다() {
        // given: 상품 1번이 2개 담긴 장바구니
        Cart cart = Cart.create(1L);
        cart.addItem(10L, 2);

        // when : 상품 1번의 수량을 7로 변경한다
        cart.changeQuantity(10L, 7);

        // then : 7로 덮어써진다
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("장바구니에 없는 상품의 수량을 바꾸면 예외가 난다")
    void 없는_상품의_수량_변경은_예외() {
        // given: 상품 1번만 담긴 장바구니
        Cart cart = Cart.create(1L);
        cart.addItem(10L, 2);

        // when & then : 다른 상품의 수량을 바꾸기 -> 예외 발생
        assertThatThrownBy(() -> cart.changeQuantity(11L, 3))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("담긴 상품을 뺄 수 있다")
    void 상품을_뺀다() {
        // given: 상품 1번만 담긴 장바구니
        Cart cart = Cart.create(1L);
        cart.addItem(10L, 2);

        // when : 상품 1번을 뺀다
        cart.removeItem(10L);

        // then : 장바구니가 빈다
        assertThat(cart.getItems()).hasSize(0);
    }
}
