package com.mz.discount.data.to;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.mz.discount.utils.TestUtils.CODE;
import static com.mz.discount.utils.TestUtils.CODE_2;
import static com.mz.discount.utils.TestUtils.DESCRIPTION;
import static com.mz.discount.utils.TestUtils.DESCRIPTION_2;
import static com.mz.discount.utils.TestUtils.getFixedDiscount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    @Test
    void getterSetterTest() {
        // GIVEN
        Product product = new Product();
        product.setId(1L);
        product.setCode(CODE);
        product.setDescription(DESCRIPTION);
        product.setPrice(BigDecimal.TEN);
        product.setDiscount(getFixedDiscount());

        // WHEN-THEN
        assertEquals(1, product.getId());
        assertEquals(CODE, product.getCode());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(0, BigDecimal.TEN.compareTo(product.getPrice()));
        assertNotNull(product.getDiscount());
        assertNotNull(product.toString());
    }

    @Test
    void testEquals() {
        // WHEN-THEN
        assertEquals(new Product(1L, CODE, DESCRIPTION, BigDecimal.TEN), new Product(1L, CODE, DESCRIPTION_2, BigDecimal.TEN));
        assertTrue(new Product(1L, CODE, DESCRIPTION, BigDecimal.TEN).equals(new Product(1L, CODE, DESCRIPTION_2, BigDecimal.TEN)));

        assertNotEquals(new Product(1L, CODE, DESCRIPTION, BigDecimal.TEN), new Product(1L, CODE_2, DESCRIPTION, BigDecimal.TEN));
        assertNotEquals(new Product(1L, CODE, DESCRIPTION, BigDecimal.TEN), new Product(2L, CODE, DESCRIPTION, BigDecimal.TEN));
        assertNotEquals(new Product(1L, CODE, DESCRIPTION, BigDecimal.TEN), new Product(1L, CODE, DESCRIPTION, BigDecimal.ONE));
    }

}
