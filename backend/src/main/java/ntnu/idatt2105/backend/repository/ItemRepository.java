package ntnu.idatt2105.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntnu.idatt2105.backend.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}