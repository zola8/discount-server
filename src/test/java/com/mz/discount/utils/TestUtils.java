package com.mz.discount.utils;

import com.mz.discount.data.to.Discount;
import com.mz.discount.data.to.DiscountType;
import com.mz.discount.data.to.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestUtils {

    private TestUtils() {
        // utility class
    }

    public static final String CODE = "code";
    public static final String CODE_2 = "code2";
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_2 = "description2";

    public static final Product getSimpleProduct() {
        return new Product(1L, CODE, DESCRIPTION, BigDecimal.ONE);
    }

    public static final Discount getFixedDiscount() {
        return new Discount(1L, DiscountType.FIXED_PRICE, 1, BigDecimal.ONE, LocalDate.now(), null);
    }

    public static Product buildProduct(Long id, double price) {
        Product item = new Product(id, Long.toString(id), "item-" + id, BigDecimal.valueOf(price));
        return item;
    }

    public static Product buildProduct(Long id, double price, DiscountType discountType, Integer afterPiece, BigDecimal discountAmount) {
        Product item = buildProduct(id, price);
        Discount discount = new Discount(id, discountType, afterPiece, discountAmount, LocalDate.now(), null);
        item.setDiscount(discount);

        return item;
    }

    public static Product buildProduct(Long id, double price, DiscountType discountType, Integer afterPiece, BigDecimal discountAmount, LocalDate validFrom, LocalDate validUntil) {
        Product item = buildProduct(id, price);
        Discount discount = new Discount(id, discountType, afterPiece, discountAmount, validFrom, validUntil);
        item.setDiscount(discount);

        return item;
    }

}
