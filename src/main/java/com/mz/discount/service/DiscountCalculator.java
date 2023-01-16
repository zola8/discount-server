package com.mz.discount.service;

import com.mz.discount.data.to.DiscountType;
import com.mz.discount.data.to.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class DiscountCalculator {

    public static BigDecimal calculate(final List<Product> shoppingCart, final DiscountType discountType, final BigDecimal overallDiscount) {
        BigDecimal total = shoppingCart.stream()
                .map(item -> getDiscountedPrice(item, shoppingCart))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (discountType != null && overallDiscount != null) {
            total = getDiscountedPrice(total, discountType, overallDiscount);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getDiscountedPrice(Product product, List<Product> shoppingCart) {
        // logic: keep simple - get discount only if afterPiece is reached.
        // afterPiece=2, items=3 ==> you get the discount for all products because the limit is reached

        BigDecimal productValue = product.getPrice();

        if (product.getDiscount() == null) {
            return productValue;
        }

        Integer afterPiece = product.getDiscount().getAfterPiece();
        long count = shoppingCart.stream()
                .filter(item -> item.equals(product))
                .count();


        if (count >= afterPiece && isWithinRange(product.getDiscount().getValidFrom(), product.getDiscount().getValidUntil())) {
            return getDiscountedPrice(productValue, product.getDiscount().getDiscountType(), product.getDiscount().getAmount());
        }

        return productValue;
    }

    private static boolean isWithinRange(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();

        if (endDate == null) {
            return !(now.isBefore(startDate));
        }

        return !(now.isBefore(startDate) || now.isAfter(endDate));
    }

    private static BigDecimal getDiscountedPrice(BigDecimal value, DiscountType discountType, BigDecimal discountAmount) {
        if (discountType == null || discountAmount == null) {
            return value;
        }

        switch (discountType) {
            case FIXED_PRICE:
                return value.subtract(discountAmount).compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value.subtract(discountAmount);
            case PERCENT:
                BigDecimal percent = (BigDecimal.valueOf(100).subtract(discountAmount)).divide(BigDecimal.valueOf(100));
                return value.multiply(percent);
            default:
                return value;
        }
    }

}
