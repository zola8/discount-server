package com.mz.discount.data.repository;

import com.mz.discount.data.to.Discount;
import com.mz.discount.data.to.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByDiscount(Discount discount);

    List<Product> findByIdIn(List<Long> idList);

    Product findByCode(String code);

}
