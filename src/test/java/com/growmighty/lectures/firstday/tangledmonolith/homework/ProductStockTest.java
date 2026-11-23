package com.growmighty.lectures.firstday.tangledmonolith.homework;

import com.growmighty.lectures.firstday.tangledmonolith.product.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * [과제 DDD-2] 상품 재고 규칙 테스트 — 뼈대입니다. fail(...) 을 지우고 채우세요.
 */
class ProductStockTest {

    @Test
    @DisplayName("재고 안에서 차감하면 재고가 줄어든다")
    void 재고를_차감한다() {

        // given : 재고 10개
        Product product = Product.create(1L, "Dofia 이동식 접이식 식탁 의자 4개 세트", BigDecimal.valueOf(179000), 10, "가정용 소형주택 신축식");

        // when : 재고 1개 차감
        product.decreaseStock(1);

        // then : 재고 9개
        assertThat(product.getStockQuantity()).isEqualTo(9);

    }

    @Test
    @DisplayName("재고보다 많이 차감하면 예외가 나고 재고는 그대로다")
    void 재고_부족이면_예외() {

        // given : 재고 10개
        Product product = Product.create(1L, "Dofia 이동식 접이식 식탁 의자 4개 세트", BigDecimal.valueOf(179000), 10, "가정용 소형주택 신축식");

        // when & then : 재고 11개 차감하면 예외 발생 -> 재고는 10개 그대로
        assertThatThrownBy(() -> product.decreaseStock(11))
                .isInstanceOf(IllegalStateException.class);
        assertThat(product.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("0 이하의 수량은 차감할 수 없다")
    void 수량이_0_이하면_예외() {

        // given : 재고 10개
        Product product = Product.create(1L, "Dofia 이동식 접이식 식탁 의자 4개 세트", BigDecimal.valueOf(179000), 10, "가정용 소형주택 신축식");

        // when & then : 0 이하 수량 차감 시 예외 발생
        assertThatThrownBy(() -> product.decreaseStock(-1))
                .isInstanceOf(IllegalStateException.class);
        assertThat(product.getStockQuantity()).isEqualTo(10);
    }
}
