package ntnu.idatt2105.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ntnu.idatt2105.backend.dto.CategoryRegisterDTO;
import ntnu.idatt2105.backend.dto.ItemRegisterDTO;
import ntnu.idatt2105.backend.enums.Role;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.UserRepository;
import ntnu.idatt2105.backend.service.CategoryService;
import ntnu.idatt2105.backend.service.ItemService;

@Component
public class DataLoader implements ApplicationRunner {
    private ItemService itemService;
    private CategoryService categoryService;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(ItemService itemService, CategoryService categoryService, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        User testUser = new User("Username", passwordEncoder.encode("password123"), Role.USER);
        User admin = new User("Admin", passwordEncoder.encode("PasswordAdmin123"), Role.ADMIN);
        userRepository.save(admin);
        userRepository.save(testUser);
        long userId = userRepository.findByUsername("Username").get().getId();

        CategoryRegisterDTO electronics = new CategoryRegisterDTO("Electronics");
        CategoryRegisterDTO books = new CategoryRegisterDTO("Books");
        CategoryRegisterDTO clothing = new CategoryRegisterDTO("Clothing");

        categoryService.addCategory(electronics);
        categoryService.addCategory(books);
        categoryService.addCategory(clothing);

        ItemRegisterDTO laptop = new ItemRegisterDTO(userId, "Laptop", 1299.99, "A high-performance laptop", "Electronics", "laptop.jpg");
        ItemRegisterDTO novel = new ItemRegisterDTO(userId, "Fantasy Novel", 19.99, "A thrilling fantasy adventure", "Books", "novel.jpg");
        ItemRegisterDTO tshirt = new ItemRegisterDTO(userId, "T-shirt", 14.99, "100% cotton T-shirt", "Clothing", "tshirt.jpg");

        itemService.registerItem(laptop);
        itemService.registerItem(novel);
        itemService.registerItem(tshirt);
    }
}
