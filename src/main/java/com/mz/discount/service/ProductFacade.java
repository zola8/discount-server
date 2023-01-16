package com.mz.discount.service;

import com.mz.discount.data.repository.DiscountRepository;
import com.mz.discount.data.repository.ProductRepository;
import com.mz.discount.data.to.Discount;
import com.mz.discount.data.to.Product;
import com.mz.discount.exception.EntityNotFoundException;
import com.mz.discount.exception.OperationNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFacade.class);

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        LOGGER.info("findAllProducts: {}", productRepository.count());

        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findByIds(List<Long> idList) {
        List<Product> products = productRepository.findByIdIn(idList);
        List<Product> result = new ArrayList<>();
        idList.forEach(item -> {
            result.add(products.stream().filter(product -> product.getId().equals(item)).findFirst().get());
        });
        LOGGER.info("findByIds items: {}", result);

        return result;
    }

    @Transactional
    public Product saveProduct(final Product product) {
        Product productByCode = productRepository.findByCode(product.getCode());

        if (productByCode == null || productByCode.getId().equals(product.getId())) {
            Product result = productRepository.save(product);
            LOGGER.info("Product saved: {}", result);

            return result;
        }

        LOGGER.warn("Product is not saved as CODE already exists: {}", product.getCode());
        throw new OperationNotAllowedException("Product not saved. ID: " + product.getId());
    }

    @Transactional
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Product.class));
        productRepository.delete(product);
        LOGGER.info("Product deleted by ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<Discount> findAllDiscounts() {
        LOGGER.info("findAllDiscounts: {}", discountRepository.count());

        return discountRepository.findAll();
    }

    @Transactional
    public Discount saveDiscount(final Discount discount) {
        Discount result = discountRepository.save(discount);
        LOGGER.info("Discount saved: {}", result);

        return result;
    }

    @Transactional
    public void deleteDiscount(final Long id, final boolean force) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Discount.class));
        List<Product> products = productRepository.findAllByDiscount(discount);

        if (products.isEmpty()) {
            discountRepository.deleteById(id);
            LOGGER.info("Discount deleted by ID: {}", id);
        } else {
            if (force) {
                removeDiscountsFromAllProducts(products);
                discountRepository.deleteById(id);
                LOGGER.info("Discount (id: {}) removed from all products and deleted.", id);
            } else {
                LOGGER.warn("Discount (id: {}) cannot be deleted as it is referenced by product(s): {}", id, products);
                throw new OperationNotAllowedException("Discount cannot be deleted as it is referenced. Discount ID: " + id);
            }
        }
    }

    public Product removeDiscount(final Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, Product.class));

        return removeDiscount(product);
    }

    @Transactional
    public Product removeDiscount(final Product product) {
        product.setDiscount(null);
        productRepository.save(product);
        LOGGER.info("Discount removed from product: {}", product);

        return product;
    }

    public void removeDiscountsFromAllProducts(final List<Product> products) {
        products.forEach(item -> removeDiscount(item));
    }

    public Product assignDiscount(final Long productId, final Long discountId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, Product.class));
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException(discountId, Discount.class));

        return assignDiscount(product, discount);
    }

    @Transactional
    public Product assignDiscount(final Product product, final Discount discount) {
        product.setDiscount(discount);
        productRepository.save(product);
        LOGGER.info("Discount assigned to product: {}", product);

        return product;
    }

}
