package com.viplogistics.repository;

import com.viplogistics.entity.master.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends JpaRepository<Item,String> {

    @Query("SELECT p.itemNo FROM item_master p ORDER BY p.itemNo DESC LIMIT 1")
    String findTopByOrderByItemNoDesc();
}
