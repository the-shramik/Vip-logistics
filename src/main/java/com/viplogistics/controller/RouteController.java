package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Route;
import com.viplogistics.service.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * {RouteController} - Handles API requests for managing routes.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/route")
@CrossOrigin("*")
public class RouteController {

  private final IRouteService routeService;

  /**
   * Adds a new route.
   *
   * @param route The route details to be added.
   * @return {@link ResponseEntity} containing the result of the operation.
   */
  @PostMapping("/add-route")
  public ResponseEntity<?> addRoute(@RequestBody Route route){
    System.out.println(route.getGstType());
    return ResponseEntity.status(HttpStatus.OK).body(routeService.addRoute(route));
  }

  /**
   * Retrieves the latest route number.
   *
   * @return {@link ResponseEntity} containing the latest route number.
   */
  @GetMapping("/get-latest-route-no")
  public ResponseEntity<?> getLatestRouteNo(){
    return ResponseEntity.status(HttpStatus.OK).body(routeService.getLatestRouteNo());
  }

  /**
   * Retrieves all routes.
   *
   * @return {@link ResponseEntity} containing a list of all routes.
   */
  @GetMapping("/get-routes")
  public ResponseEntity<?> getALlRoutes(){
    return ResponseEntity.status(HttpStatus.OK).body(routeService.getAllRoutes());
  }

  /**
   * Updates an existing route's details.
   *
   * @param route The updated route details.
   * @return {@link ResponseEntity} with the updated route details.
   */
  @PutMapping("/update-route")
  public ResponseEntity<?> updateRoute(@RequestBody Route route){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(routeService.updateRoute(route));
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  /**
   * Deletes a route by its route number.
   *
   * @param routeNo The route number of the route to be deleted.
   * @return {@link ResponseEntity} containing the result of the delete operation.
   */
  @DeleteMapping("/delete-route")
  public ResponseEntity<?> deleteRoute(@RequestParam String routeNo){
    ApiResponse<?> response = routeService.deleteRoute(routeNo);

    if(response.isSuccess()){
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  /**
   * Searches for routes based on search criteria.
   *
   * @param searchData The search criteria for finding routes.
   * @return {@link ResponseEntity} containing a list of matching routes.
   */
  @GetMapping("/get-searched-routes/{searchData}")
  public ResponseEntity<?> getSearchedParties(@PathVariable String searchData){
    return ResponseEntity.status(HttpStatus.OK).body(routeService.getSearchedRoutes(searchData));
  }


  /**
   * Retrieves the total count of routes.
   *
   * @return {@link ResponseEntity} containing the total number of routes.
   */
  @GetMapping("/get-route-count")
  public ResponseEntity<?> getRouteCount(){
    return ResponseEntity.status(HttpStatus.OK).body(routeService.getRouteCount());
  }

  @PostMapping("/test-route")
  public ResponseEntity<?> testRoute(){
    routeService.testRoute();
    return ResponseEntity.ok("All routes added!");
  }

  @PostMapping("/test-delete-route")
  public ResponseEntity<?> deleteRoute(){
    routeService.deleteTestRoute();
    return ResponseEntity.ok("All routes deleted!");
  }
}
