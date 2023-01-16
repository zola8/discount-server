package com.mz.discount.rest;

import com.mz.discount.data.to.Discount;
import com.mz.discount.service.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    @Autowired
    private ProductFacade productFacade;

    @GetMapping("/list")
    public List<Discount> findAll() {
        return productFacade.findAllDiscounts();
    }

    @PostMapping("/save")
    public Discount saveDiscount(@RequestBody Discount discount) {
        return productFacade.saveDiscount(discount);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") String force) {
        productFacade.deleteDiscount(id, Boolean.parseBoolean(force));
    }

}
