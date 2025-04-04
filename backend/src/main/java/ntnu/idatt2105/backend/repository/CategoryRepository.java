package ntnu.idatt2105.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntnu.idatt2105.backend.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}