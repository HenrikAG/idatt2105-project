package ntnu.idatt2105.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.dto.ItemRegisterDTO;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.model.Item;
import ntnu.idatt2105.backend.service.ItemService;

/**
 * Handles HTTP requests related to items.
 */
@RestController
@Tag(name = "Items", description = "Operations related to items listed for sale")
@RequestMapping("/api/items")
public class ItemController {
    private static Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    /**
     * Constructs an ItemController.
     * 
     * @param itemService service class for Items
     */
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
    @Operation(summary = "Get all of the items in the category")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String categoryName) {
        try {
            List<ItemDTO> categoryItems = itemService.getItemsByCategoryName(categoryName);
            return new ResponseEntity<>(categoryItems, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns all registered items mapped to itemDTOs.
     * 
     * @return ReponseEntity with List containing all items as itemDTOs
     */
    @GetMapping
    @Operation(summary = "Get all of the registered items")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Registers a new item in the database if the item is valid.
     * 
     * @param itemRequest the item to be registered as an ItemDTO object
     * @return A ResponseEntity with the registered item an ok status code, or the exception message and the HttpStatus code.
     */
    @PostMapping("/post")
    @Operation(summary = "Register a new item")
    public ResponseEntity<?> postItem(@RequestBody ItemRegisterDTO itemRequest) {
        logger.info("A user is trying to post the following item: " + itemRequest);
        try {
            Item newItem = itemService.registerItem(itemRequest);
            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } catch (NotFoundException exception) {
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the item with the specified id.
     * 
     * @param id the id of the item to be updated
     * @param itemUpdateRequest DTO with the new item info
     * @return ResponseEntity with the result of the request
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing item. Null atributes will be left unchanged.")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemUpdateRequest) {
        logger.info("A user is trying to update the item with id: " + id + ", with the follwing info:" + itemUpdateRequest);
        try {
            itemService.updateItem(id, itemUpdateRequest);
            return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            logger.warn("There was an error trying to update item: " + id + ", with the info: " + itemUpdateRequest);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Deletes the item with the specified id.
     * 
     * @param id the id of the item to be deleted.
     * @return ResponseEntity with the result of the request
     */
    @DeleteMapping("/item/{id}")
    @Operation(summary = "Delete item by id")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        logger.info("User is trying to delete item with id: " + id);

        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException exception) {
            logger.warn("Failed to delete. No item with id: " + id);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
