package com.mz.discount.data.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long id;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "after_piece", nullable = false)
    private Integer afterPiece;

    @Column(name = "amount", scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    public Discount() {
    }

    public Discount(Long id, DiscountType discountType, Integer afterPiece, BigDecimal amount, LocalDate validFrom, LocalDate validUntil) {
        this.id = id;
        this.discountType = discountType;
        this.afterPiece = afterPiece;
        this.amount = amount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Integer getAfterPiece() {
        return afterPiece;
    }

    public void setAfterPiece(Integer afterPiece) {
        this.afterPiece = afterPiece;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", discountType=" + discountType +
                ", afterPiece=" + afterPiece +
                ", amount=" + amount +
                ", validFrom=" + validFrom +
                ", validUntil=" + validUntil +
                '}';
    }
}
