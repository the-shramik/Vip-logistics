package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Item;
import com.viplogistics.entity.master.Party;
import com.viplogistics.entity.master.Route;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IItemRepository;
import com.viplogistics.repository.IPartyRepository;
import com.viplogistics.repository.IRouteRepository;
import com.viplogistics.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {

    private final IItemRepository itemRepository;
    private final IRouteRepository routeRepository;
    private final IPartyRepository partyRepository;

    @Override
    public Item addItem(Item item) {
        if (item.getItemNo() == null || item.getItemNo().isEmpty()) {
            String lastItemNo = itemRepository.findTopByOrderByItemNoDesc();
            String newItemNo = generateNewItemNo(lastItemNo).get("newItemNo");
            item.setItemNo(newItemNo);
        }
        item.setItemDate(LocalDate.now());

        return itemRepository.save(item);
    }

    private Map<String, String> generateNewItemNo(String lastItemNo) {
        Map<String, String> result = new HashMap<>();

        if (lastItemNo == null || lastItemNo.isEmpty()) {
            result.put("newItemNo", "I0001");
            return result;
        }

        int numericPart = Integer.parseInt(lastItemNo.substring(1));
        String newItemNo = String.format("I%04d", numericPart + 1);

        result.put("newItemNo", newItemNo);

        return result;
    }

    @Override
    public Map<String, String> getLatestItemNo() {
        String lastItemNo = itemRepository.findTopByOrderByItemNoDesc();

        return generateNewItemNo(lastItemNo);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems=new ArrayList<>();
        itemRepository.findAll().forEach(item -> {
            Route route=routeRepository.findById(item.getRoute().getRouteNo()).get();
            Party party=partyRepository.findById(item.getParty().getPartyNo()).get();

            item.setRoute(route);
            item.setParty(party);

            allItems.add(item);
        });

        return allItems;
    }

    @Override
    public Item updateItem(Item item) throws ResourceNotFoundException {
        Item existedItem = itemRepository.findById(item.getItemNo()).orElseThrow(
                () -> new ResourceNotFoundException("Item not found with item no: " + item.getItemNo()));

        existedItem.setItemName(item.getItemName());
        existedItem.setItemDate(existedItem.getItemDate());
        existedItem.setPu(item.getPu());
        existedItem.setParty(item.getParty());
        existedItem.setPartNo(item.getPartNo());
        existedItem.setRoute(item.getRoute());
        existedItem.setQtyInBox(item.getQtyInBox());
        existedItem.setRateOnBox(item.getRateOnBox());
        existedItem.setRateOnWeight(item.getRateOnWeight());
        existedItem.setVendorCode(item.getVendorCode());
        existedItem.setWeightPerBox(item.getWeightPerBox());

        return itemRepository.save(existedItem);
    }

    @Override
    public ApiResponse<?> deleteItem(String itemNo) {
        Optional<Item> optionalItem = itemRepository.findById(itemNo);

        if(optionalItem.isPresent()){
            Item item=optionalItem.get();
            System.out.println("Item no: "+item.getItemNo());

            itemRepository.deleteById(item.getItemNo());
            return new ApiResponse<>(true,"Item deleted successfully",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Item not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<String, Long> getItemCount() {
        HashMap<String,Long> result=new HashMap<>();

        result.put("totalItems",itemRepository.count());

        return result;
    }

    @Override
    public List<Item> getItemsByPartyCode(String partyNo) {
        return itemRepository.findAllByPartyNo(partyNo);
    }
}
