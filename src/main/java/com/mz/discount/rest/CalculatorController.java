package com.mz.discount.rest;

import com.mz.discount.data.to.Product;
import com.mz.discount.data.to.ShoppingCart;
import com.mz.discount.service.DiscountCalculator;
import com.mz.discount.service.ProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private ProductFacade productFacade;

    @PostMapping("/calculate")
    public BigDecimal calculate(@RequestBody ShoppingCart shoppingCart) {
        LOGGER.info("Incoming calc request: {}", shoppingCart);
        List<Product> products = productFacade.findByIds(shoppingCart.getProductIds());
        BigDecimal result = DiscountCalculator.calculate(products, shoppingCart.getDiscountType(), shoppingCart.getOverallDiscount());
        LOGGER.info("Calculation result: {}", result);

        return result;
    }

}
