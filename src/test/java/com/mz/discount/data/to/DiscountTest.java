package com.mz.discount.data.to;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DiscountTest {

    @Test
    public void getterSetterTest() {
        // GIVEN
        LocalDate now = LocalDate.now();
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setAfterPiece(2);
        discount.setAmount(BigDecimal.TEN);
        discount.setDiscountType(DiscountType.FIXED_PRICE);
        discount.setValidUntil(now);
        discount.setValidFrom(now);

        // WHEN-THEN
        assertEquals(1, discount.getId());
        assertEquals(2, discount.getAfterPiece());
        assertEquals(0, BigDecimal.TEN.compareTo(discount.getAmount()));
        assertEquals(DiscountType.FIXED_PRICE, discount.getDiscountType());
        assertEquals(now, discount.getValidFrom());
        assertEquals(now, discount.getValidUntil());
        assertNotNull(discount.toString());
    }

}
