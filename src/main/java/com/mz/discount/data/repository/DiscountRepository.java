package com.mz.discount.data.repository;

import com.mz.discount.data.to.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
