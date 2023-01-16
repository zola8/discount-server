package com.mz.discount.rest;

import com.mz.discount.data.to.Product;
import com.mz.discount.service.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    @Autowired
    private ProductFacade productFacade;

    @GetMapping("/removeDiscount")
    public Product removeDiscount(@RequestParam String productId) {
        return productFacade.removeDiscount(Long.parseLong(productId));
    }

    @GetMapping("/assignDiscount")
    public Product assignDiscount(@RequestParam String productId, @RequestParam String discountId) {
        return productFacade.assignDiscount(Long.parseLong(productId), Long.parseLong(discountId));
    }

}
