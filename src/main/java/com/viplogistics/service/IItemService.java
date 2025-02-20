package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Item;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IItemService {

    Item addItem(Item item);

    Map<String,String> getLatestItemNo();

    List<Item> getAllItems();

    Item updateItem(Item item) throws ResourceNotFoundException;

    ApiResponse<?> deleteItem(String itemNo);

    Map<String,Long> getItemCount();
}
