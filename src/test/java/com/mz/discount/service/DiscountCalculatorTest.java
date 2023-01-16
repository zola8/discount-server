package com.mz.discount.service;

import com.mz.discount.data.to.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mz.discount.data.to.DiscountType.FIXED_PRICE;
import static com.mz.discount.data.to.DiscountType.PERCENT;
import static com.mz.discount.utils.TestUtils.buildProduct;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountCalculatorTest {

    @Test
    public void testInvalidDiscount() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 2.00, FIXED_PRICE, 1, BigDecimal.valueOf(3.0)));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(0).compareTo(result) == 0);
    }

    @Test
    public void testFixedProducts() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 4.30, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 5.50, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(3L, 8.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.99)));
        list.add(buildProduct(4L, 2.00));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(15.81).compareTo(result) == 0);
    }

    @Test
    public void testMixedProducts() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 70.00, PERCENT, 1, BigDecimal.valueOf(10.0)));
        list.add(buildProduct(2L, 5.50, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(3L, 80.00, PERCENT, 1, BigDecimal.valueOf(4)));
        list.add(buildProduct(4L, 2.00));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(146.3).compareTo(result) == 0);
    }

    @Test
    public void testSimilarProducts() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(4.0).compareTo(result) == 0);
    }

    @Test
    public void testOverallDiscountFixedPrice() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 70.00, PERCENT, 1, BigDecimal.valueOf(1)));
        list.add(buildProduct(2L, 2.00));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, FIXED_PRICE, BigDecimal.valueOf(5.00));

        // THEN
        assertTrue(BigDecimal.valueOf(66.3).compareTo(result) == 0);
    }

    @Test
    public void testOverallDiscountPercentWithRounding() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 70.00, PERCENT, 1, BigDecimal.valueOf(1)));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, PERCENT, BigDecimal.valueOf(2.00));

        // THEN
        assertTrue(BigDecimal.valueOf(67.91).compareTo(result) == 0);
    }

    @Test
    public void testAfterPiece() {
        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 3.00, FIXED_PRICE, 2, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(1L, 3.00, FIXED_PRICE, 2, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 3, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 3, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 3, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(3L, 3.00, FIXED_PRICE, 2, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(4L, 3.00, FIXED_PRICE, 4, BigDecimal.valueOf(1.0)));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(16.0).compareTo(result) == 0);
    }

    @Test
    public void testValidDates() {
        LocalDate tm1 = LocalDate.now().minusDays(1);
        LocalDate tm2 = LocalDate.now().minusDays(2);
        LocalDate tp1 = LocalDate.now().plusDays(1);
        LocalDate tp2 = LocalDate.now().plusDays(2);

        // GIVEN
        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0), tm2, tm1));
        list.add(buildProduct(2L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0), tm1, tp1));
        list.add(buildProduct(3L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0), tp1, tp2));
        list.add(buildProduct(3L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0), tm1, null));
        list.add(buildProduct(3L, 3.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0), tp1, null));

        // WHEN
        BigDecimal result = DiscountCalculator.calculate(list, null, null);

        // THEN
        assertTrue(BigDecimal.valueOf(13.0).compareTo(result) == 0);
    }

}
