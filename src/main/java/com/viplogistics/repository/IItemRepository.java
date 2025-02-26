package com.viplogistics.repository;

import com.viplogistics.entity.master.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item,String> {

    @Query("SELECT p.itemNo FROM item_master p ORDER BY p.itemNo DESC LIMIT 1")
    String findTopByOrderByItemNoDesc();

    @Query("SELECT i FROM item_master i JOIN FETCH i.party p WHERE p.partyNo = :partyNo")
    List<Item> findAllByPartyNo(@Param("partyNo") String partyNo);

}
