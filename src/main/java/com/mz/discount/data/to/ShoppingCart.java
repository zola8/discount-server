package com.mz.discount.data.to;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCart {

    List<Long> productIds;
    DiscountType discountType;
    BigDecimal overallDiscount;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getOverallDiscount() {
        return overallDiscount;
    }

    public void setOverallDiscount(BigDecimal overallDiscount) {
        this.overallDiscount = overallDiscount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productIds=" + productIds +
                ", discountType=" + discountType +
                ", overallDiscount=" + overallDiscount +
                '}';
    }
}
