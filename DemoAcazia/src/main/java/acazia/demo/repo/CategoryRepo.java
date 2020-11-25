package acazia.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acazia.demo.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>{

	Category findByTag(String tag);

	@Query(value = "SELECT * FROM category c WHERE (:name IS NULL OR c.name LIKE CONCAT('%',:name,'%')) "
			+ "AND (:tag IS NULL OR c.tag LIKE CONCAT('%',:tag,'%'))  "
			+ "AND (:id IS NULL OR c.id = :id)",
            countQuery = "SELECT count(*)  FROM category c WHERE (:name IS NULL OR c.name LIKE CONCAT('%',:name,'%')) "
            		+ "AND (:tag IS NULL OR c.tag LIKE CONCAT('%',:tag,'%'))" 
            		+ "AND (:id IS NULL OR c.id = :id)"
            , nativeQuery = true)
	Page<Category> pageCategory(@Param("name") String name, @Param("tag") String tag, @Param("id") Long id, Pageable pageable);

}
