package com.viplogistics.repository;

import com.viplogistics.entity.master.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRouteRepository extends JpaRepository<Route,String> {

    @Query("SELECT r.routeNo FROM route_master r ORDER BY r.routeNo DESC LIMIT 1")
    String findTopByOrderByRouteNoDesc();

    @Query("SELECT p FROM route_master p WHERE " +
            "(p.routeNo LIKE %:searchData% OR " +
            "p.routeName LIKE %:searchData% )")
    List<Route> findBySearchData(String searchData);
}
