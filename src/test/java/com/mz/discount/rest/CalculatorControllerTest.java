package com.mz.discount.rest;

import com.mz.discount.data.to.DiscountType;
import com.mz.discount.data.to.Product;
import com.mz.discount.data.to.ShoppingCart;
import com.mz.discount.service.ProductFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mz.discount.data.to.DiscountType.FIXED_PRICE;
import static com.mz.discount.utils.TestUtils.buildProduct;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductFacade productFacade;

    @Test
    void testCalculatorPostRequest() throws Exception {
        // GIVEN
        List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDiscountType(DiscountType.FIXED_PRICE);
        shoppingCart.setOverallDiscount(BigDecimal.valueOf(10.00));
        shoppingCart.setProductIds(ids);

        List<Product> list = new ArrayList();
        list.add(buildProduct(1L, 4.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));
        list.add(buildProduct(2L, 5.00, FIXED_PRICE, 1, BigDecimal.valueOf(1.0)));

        when(productFacade.findByIds(ids)).thenReturn(list);

        // WHEN-THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/calculator/calculate")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("{" +
                                "  \"discountType\": \"FIXED_PRICE\"," +
                                "  \"overallDiscount\": 1," +
                                "  \"productIds\": [" +
                                "    1,2" +
                                "  ]" +
                                "}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("6.00"));
    }

}
