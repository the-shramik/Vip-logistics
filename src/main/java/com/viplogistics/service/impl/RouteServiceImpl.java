package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Party;
import com.viplogistics.entity.master.Route;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IRouteRepository;
import com.viplogistics.service.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements IRouteService {

    private final IRouteRepository routeRepository;

    @Override
    public Route addRoute(Route route) {
        if (route.getRouteNo() == null || route.getRouteNo().isEmpty()) {
            String lastRouteNo = routeRepository.findTopByOrderByRouteNoDesc();
            String newRouteNo = generateNewRouteNo(lastRouteNo).get("newRouteNo");
            route.setRouteNo(newRouteNo);
        }
        route.setRouteDate(LocalDate.now());
        return routeRepository.save(route);
    }


    private Map<String, String> generateNewRouteNo(String lastRouteNo) {
        Map<String, String> result = new HashMap<>();

        if (lastRouteNo == null || lastRouteNo.isEmpty()) {
            result.put("newRouteNo", "R0001");
            return result;
        }

        int numericPart = Integer.parseInt(lastRouteNo.substring(1));
        String newRouteNo = String.format("R%04d", numericPart + 1);

        result.put("newRouteNo", newRouteNo);

        return result;
    }


    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Map<String, String> getLatestRouteNo() {
        String lastRouteNo=routeRepository.findTopByOrderByRouteNoDesc();

        return generateNewRouteNo(lastRouteNo);
    }

    @Override
    public Route updateRoute(Route route) throws ResourceNotFoundException {
        Route existedRoute = routeRepository.findById(route.getRouteNo())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found by route no: " + route.getRouteNo()));

        existedRoute.setRouteName(route.getRouteName());
        existedRoute.setRouteTo(route.getRouteTo());
        existedRoute.setRouteFrom(route.getRouteFrom());
        existedRoute.setIsRoundUp(route.getIsRoundUp());
        existedRoute.setRouteDate(route.getRouteDate());
        existedRoute.setGstType(route.getGstType());

        return routeRepository.save(existedRoute);
    }

    @Override
    public ApiResponse<?> deleteRoute(String routeNo) {
        Optional<Route> optionalRoute = routeRepository.findById(routeNo);

        if(optionalRoute.isPresent()){
            Route route = optionalRoute.get();
            routeRepository.delete(route);

            return new ApiResponse<>(true,"Route deleted successfully",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Route not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Route> getSearchedRoutes(String searchData) {
        return routeRepository.findBySearchData(searchData);
    }

    @Override
    public Map<String, Long> getRouteCount() {
        HashMap<String,Long> result=new HashMap<>();

        result.put("routesCount",routeRepository.count());

        return result;
    }

    public void testRoute() {
        List<Route> routes = new ArrayList<>();

        for (int i = 1; i <= 2000; i++) {
            Route route = new Route();
            route.setRouteNo("ROOT-" + i);  // Auto-generated unique route number
            route.setRouteName("Route " + i);
            route.setRouteFrom("City " + (i % 50));
            route.setRouteTo("City " + ((i + 10) % 50));
            route.setIsRoundUp(i % 2 == 0);
            route.setRouteDate(LocalDate.now());
            route.setGstType(i % 2 == 0 ? "GST Inclusive" : "GST Exclusive");

            routes.add(route);
        }

        routeRepository.saveAll(routes);
        System.out.println("2000 routes added successfully.");
    }

    public void deleteTestRoute() {
        List<Route> allRoutes = routeRepository.findAll();

        if (allRoutes.size() > 15) {
            List<Route> toDelete = allRoutes.subList(15, allRoutes.size());
            routeRepository.deleteAll(toDelete);
            System.out.println("Deleted " + toDelete.size() + " routes, skipping first 15.");
        } else {
            System.out.println("Less than 15 routes exist, no deletion performed.");
        }
    }
}
