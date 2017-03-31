package io.catwizard.productservice.repository;

import io.catwizard.productservice.domain.ProductImage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductImage entity.
 */
@SuppressWarnings("unused")
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

}
