package com.mz.discount.service;

import com.mz.discount.data.to.Product;
import com.mz.discount.data.repository.DiscountRepository;
import com.mz.discount.data.repository.ProductRepository;
import com.mz.discount.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mz.discount.utils.TestUtils.CODE;
import static com.mz.discount.utils.TestUtils.CODE_2;
import static com.mz.discount.utils.TestUtils.DESCRIPTION;
import static com.mz.discount.utils.TestUtils.DESCRIPTION_2;
import static com.mz.discount.utils.TestUtils.getFixedDiscount;
import static com.mz.discount.utils.TestUtils.getSimpleProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductFacadeTest {

    @InjectMocks
    private ProductFacade productFacade;
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void testfindAllProducts() {
        // GIVEN
        when(productRepository.findAll()).thenReturn(Arrays.asList(getSimpleProduct()));

        // WHEN
        List<Product> result = productFacade.findAllProducts();

        // THEN
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(getSimpleProduct(), result.get(0));
    }

    @Test
    public void testSaveProduct() {
        // GIVEN
        Product newProduct = new Product(1L, CODE_2, DESCRIPTION_2, BigDecimal.TEN);
        when(productRepository.save(eq(newProduct))).thenReturn(newProduct);

        // WHEN
        Product result = productFacade.saveProduct(newProduct);

        // THEN
        assertEquals(newProduct, result);
    }

    @Test
    public void testDeleteProduct() {
        // GIVEN
        when(productRepository.findById(1L)).thenReturn(Optional.of(getSimpleProduct()));

        // WHEN
        productFacade.deleteProduct(1L);

        // THEN
        Mockito.verify(productRepository).delete(getSimpleProduct());
    }

    @Test
    public void testRemoveDiscount() {
        // GIVEN
        Product product = new Product(1L, CODE, DESCRIPTION, BigDecimal.ONE);
        product.setDiscount(getFixedDiscount());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // WHEN
        Product result = productFacade.removeDiscount(1L);

        // THEN
        assertNull(result.getDiscount());
    }

    @Test
    public void testAssignDiscountThrowExceptionIfProductNotFound() {
        // GIVEN
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN-THEN
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> productFacade.assignDiscount(1L, 1L)
        );

        assertEquals("Entity (class com.mz.discount.data.to.Product) not found. ID: 1", thrown.getMessage());
    }

    @Test
    public void testAssignDiscountThrowExceptionIfDiscountNotFound() {
        // GIVEN
        when(productRepository.findById(1L)).thenReturn(Optional.of(getSimpleProduct()));
        when(discountRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN-THEN
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> productFacade.assignDiscount(1L, 1L)
        );

        assertEquals("Entity (class com.mz.discount.data.to.Discount) not found. ID: 1", thrown.getMessage());
    }

    @Test
    public void testAssignDiscount() {
        // GIVEN
        when(productRepository.findById(1L)).thenReturn(Optional.of(getSimpleProduct()));
        when(discountRepository.findById(1L)).thenReturn(Optional.of(getFixedDiscount()));

        // WHEN
        Product result = productFacade.assignDiscount(1L, 1L);

        // THEN
        assertNotNull(result.getDiscount());
    }


}
