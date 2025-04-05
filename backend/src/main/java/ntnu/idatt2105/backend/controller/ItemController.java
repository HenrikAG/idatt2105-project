package ntnu.idatt2105.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.model.Item;
import ntnu.idatt2105.backend.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private static Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    /**
     * Retrieves all items with the category with the specified name.
     * 
     * @param categoryName the name of the category you want to retrieve items of
     * @return A ResponseEntity with the items with the category and the HttpStatus code, or the exception and the HttpStatus code.
     */
    @GetMapping("/{categoryName}")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String categoryName) {
        try {
            List<Item> categoryItems = itemService.getItemsByCategoryName(categoryName);
            return new ResponseEntity<>(categoryItems, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Registers a new item in the database if the item is valid.
     * 
     * @param itemRequest the item to be registered as an ItemDTO object
     * @return A ResponseEntity with the registered item an ok status code, or the exception message and the HttpStatus code.
     */
    @PostMapping("/post")
    public ResponseEntity<?> postItem(@RequestBody ItemDTO itemRequest) {
        logger.info("A user is trying to post the following item: " + itemRequest);
        try {
            Item newItem = itemService.registerItem(itemRequest);
            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemUpdateRequest) {
        logger.info("A user is trying to update the item with id: " + id + ", with the follwing info:" + itemUpdateRequest);
        try {
            itemService.updateItem(id, itemUpdateRequest);
            return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            logger.warn("There was an error trying to update item: " + id + ", with the info: " + itemUpdateRequest);
            return new ResponseEntity<>("Error " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
