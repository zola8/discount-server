package com.mz.discount.rest;

import com.mz.discount.data.to.Product;
import com.mz.discount.service.ProductFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.mz.discount.utils.TestUtils.CODE;
import static com.mz.discount.utils.TestUtils.DESCRIPTION;
import static com.mz.discount.utils.TestUtils.getSimpleProduct;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductFacade productFacade;

    @Test
    public void testGetRequest() throws Exception {
        when(productFacade.findAllProducts()).thenReturn(Arrays.asList(getSimpleProduct()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"code\":\"code\",\"description\":\"description\",\"price\":1,\"discount\":null}]"));
    }

    @Test
    public void testPostRequest() throws Exception {
        when(productFacade.saveProduct(new Product(null, CODE, DESCRIPTION, BigDecimal.ONE))).thenReturn(getSimpleProduct());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("{\"id\":null,\"code\":\"code\",\"description\":\"description\",\"price\":1,\"discount\":null}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"code\":\"code\",\"description\":\"description\",\"price\":1,\"discount\":null}"));
    }

}
