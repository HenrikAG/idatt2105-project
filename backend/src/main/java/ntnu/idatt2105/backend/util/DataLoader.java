package ntnu.idatt2105.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.model.Category;
import ntnu.idatt2105.backend.service.CategoryService;
import ntnu.idatt2105.backend.service.ItemService;

@Component
public class DataLoader implements ApplicationRunner {
    private ItemService itemService;
    private CategoryService categoryService;

    @Autowired
    public DataLoader(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    public void run(ApplicationArguments args) {
        Category electronics = new Category("Electronics");
        Category books = new Category("Books");
        Category clothing = new Category("Clothing");

        categoryService.addCategory(electronics);
        categoryService.addCategory(books);
        categoryService.addCategory(clothing);

        ItemDTO laptop = new ItemDTO("Laptop", 1299.99, "A high-performance laptop", "Electronics", "laptop.jpg");
        ItemDTO novel = new ItemDTO("Fantasy Novel", 19.99, "A thrilling fantasy adventure", "Books", "novel.jpg");
        ItemDTO tshirt = new ItemDTO("T-shirt", 14.99, "100% cotton T-shirt", "Clothing", "tshirt.jpg");

        itemService.registerItem(laptop);
        itemService.registerItem(novel);
        itemService.registerItem(tshirt);
    }
}
