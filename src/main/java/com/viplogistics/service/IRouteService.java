package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Route;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IRouteService {

    Route addRoute(Route route);

    List<Route> getAllRoutes();

    Map<String, String> getLatestRouteNo();

    Route updateRoute(Route route) throws ResourceNotFoundException;

    ApiResponse<?> deleteRoute(String routeNo);

    List<Route> getSearchedRoutes(String searchData);

    Map<String,Long> getRouteCount();

    void testRoute();

    void deleteTestRoute();
}
