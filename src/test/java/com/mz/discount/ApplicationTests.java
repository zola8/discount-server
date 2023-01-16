package com.mz.discount;

import com.mz.discount.rest.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ProductController productController;

    @Test
    public void contextLoads() {
        // sanity check
        assertThat(productController).isNotNull();
    }

}
