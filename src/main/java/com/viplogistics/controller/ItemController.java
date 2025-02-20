package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Item;
import com.viplogistics.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ItemController - Handles API requests for managing items.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@CrossOrigin("*")
public class ItemController {

    private final IItemService itemService;

    /**
     * Adds a new item to the system.
     *
     * @param item The {@link Item} object containing item details.
     * @return A {@link ResponseEntity} containing the added item.
     *         Returns HTTP 200 OK if the item is successfully added.
     */
    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(@RequestBody Item item){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.addItem(item));
    }

    /**
     * Retrieves a list of all items.
     *
     * @return A {@link ResponseEntity} containing a list of items.
     *         Returns HTTP 200 OK with the item list if successful.
     */
    @GetMapping("/get-latest-item-no")
    public ResponseEntity<?> getLatestItemNo(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getLatestItemNo());
    }

    /**
     * Get all items.
     *
     * @return List of items
     */
    @GetMapping("/get-items")
    public ResponseEntity<?> getItems(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems());
    }

    /**
     * Updates an existing item.
     *
     * @param item The item object containing updated details.
     * @return A {@link ResponseEntity} containing the updated item if successful.
     *         Returns HTTP 200 OK on successful update, or HTTP 404 Not Found if the item is not found.
     */
    @PutMapping("/update-item")
    public ResponseEntity<?> updateItem(@RequestBody Item item){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(item));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes an item by its item number.
     *
     * @param itemNo The unique identifier of the item to be deleted.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} object.
     *         Returns HTTP 200 OK if the deletion is successful,
     *         otherwise returns HTTP 404 Not Found if the item does not exist.
     */
    @DeleteMapping("/delete-item")
    public ResponseEntity<?> deleteItem(@RequestParam String itemNo){
        ApiResponse<?> response = itemService.deleteItem(itemNo);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves the total count of items.
     *
     * @return A {@link ResponseEntity} containing the total number of items (HTTP 200 OK).
     */
    @GetMapping("/get-item-count")
    public ResponseEntity<?> getItemCount(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemCount());
    }
}
