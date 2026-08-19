package sisventaGroup.sisventaArtifact.modules.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategory_NameContainingIgnoreCase(String categoryName,Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCase(
            String name,
            String categoryName,
            Pageable pageable
    );
}
