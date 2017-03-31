package io.catwizard.productservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.catwizard.productservice.domain.ProductImage;

import io.catwizard.productservice.repository.ProductImageRepository;
import io.catwizard.productservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductImage.
 */
@RestController
@RequestMapping("/api")
public class ProductImageResource {

    private final Logger log = LoggerFactory.getLogger(ProductImageResource.class);

    private static final String ENTITY_NAME = "productImage";
        
    private final ProductImageRepository productImageRepository;

    public ProductImageResource(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    /**
     * POST  /product-images : Create a new productImage.
     *
     * @param productImage the productImage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productImage, or with status 400 (Bad Request) if the productImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-images")
    @Timed
    public ResponseEntity<ProductImage> createProductImage(@Valid @RequestBody ProductImage productImage) throws URISyntaxException {
        log.debug("REST request to save ProductImage : {}", productImage);
        if (productImage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productImage cannot already have an ID")).body(null);
        }
        ProductImage result = productImageRepository.save(productImage);
        return ResponseEntity.created(new URI("/api/product-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-images : Updates an existing productImage.
     *
     * @param productImage the productImage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productImage,
     * or with status 400 (Bad Request) if the productImage is not valid,
     * or with status 500 (Internal Server Error) if the productImage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-images")
    @Timed
    public ResponseEntity<ProductImage> updateProductImage(@Valid @RequestBody ProductImage productImage) throws URISyntaxException {
        log.debug("REST request to update ProductImage : {}", productImage);
        if (productImage.getId() == null) {
            return createProductImage(productImage);
        }
        ProductImage result = productImageRepository.save(productImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productImage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-images : get all the productImages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productImages in body
     */
    @GetMapping("/product-images")
    @Timed
    public List<ProductImage> getAllProductImages() {
        log.debug("REST request to get all ProductImages");
        List<ProductImage> productImages = productImageRepository.findAll();
        return productImages;
    }

    /**
     * GET  /product-images/:id : get the "id" productImage.
     *
     * @param id the id of the productImage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productImage, or with status 404 (Not Found)
     */
    @GetMapping("/product-images/{id}")
    @Timed
    public ResponseEntity<ProductImage> getProductImage(@PathVariable Long id) {
        log.debug("REST request to get ProductImage : {}", id);
        ProductImage productImage = productImageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productImage));
    }

    /**
     * DELETE  /product-images/:id : delete the "id" productImage.
     *
     * @param id the id of the productImage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-images/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long id) {
        log.debug("REST request to delete ProductImage : {}", id);
        productImageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
