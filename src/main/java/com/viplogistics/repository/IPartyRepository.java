package com.viplogistics.repository;

import com.viplogistics.entity.master.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPartyRepository extends JpaRepository<Party,String> {

    @Query("SELECT p.partyNo FROM party_master p ORDER BY p.partyNo DESC LIMIT 1")
    String findTopByOrderByPartyNoDesc();

    @Query("SELECT p FROM party_master p WHERE " +
            "(p.partyNo LIKE %:searchData% OR " +
            "p.partyName LIKE %:searchData% OR " +
            "p.district LIKE %:searchData%)")
    List<Party> findBySearchData(String searchData);
}
