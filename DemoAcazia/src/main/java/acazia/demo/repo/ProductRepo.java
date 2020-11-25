package acazia.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acazia.demo.entity.Product;
import acazia.demo.modl.ProductModel;
@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

	@Query(value = "SELECT new acazia.demo.modl.ProductModel(p.id, p.name , p.price , c.tag , c.name ) "
			+ "FROM Product p JOIN p.category c "
			+ "WHERE (:inputSearch IS NULL OR c.tag LIKE CONCAT('%',:inputSearch,'%') OR c.name LIKE CONCAT('%',:inputSearch,'%'))"
			+ " AND (:name IS NULL OR p.name LIKE CONCAT('%',:name,'%')) AND (:id IS NULL OR p.id = :id) ORDER BY p.price DESC, UPPER(p.name)",
			countQuery = "SELECT COUNT(*) "
					+ "FROM Product p  JOIN p.category c "
					+ "WHERE (:inputSearch IS NULL OR c.tag LIKE CONCAT('%',:inputSearch,'%') OR c.name LIKE CONCAT('%',:inputSearch,'%'))"
					+ " AND (:name IS NULL OR p.name LIKE CONCAT('%',:name,'%'))", nativeQuery = false)
	Page<ProductModel> pageProduct(@Param("name") String name, @Param("inputSearch") String inputSearch, @Param("id") Long id, Pageable pageable);

	@Query(countQuery = "SELECT COUNT(*) FROM product WHERE category_id = :id " , nativeQuery = true)
	public int countByCategoryId(@Param("id") Long categoryId);

}
