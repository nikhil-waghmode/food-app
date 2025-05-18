package com.capgemini.food_app.rest;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.food_app.dto.DashboardDTO;
import com.capgemini.food_app.service.DashboardDTOService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")

public class DashboardDTOController {

    private final DashboardDTOService dashboardService;

    public DashboardDTOController(DashboardDTOService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardData() {
        DashboardDTO dashData = new DashboardDTO();

        dashData.setRecentOrders(dashboardService.getTop3OrdersByDateDesc());
        dashData.setBestSelling(dashboardService.getMostOrderedFoodItem().getName());
        dashData.setLeastSelling(dashboardService.getLeastOrderedFoodItem().getName());
        dashData.setTotalRestaurants(dashboardService.totalResaurants());
        dashData.setTotalFoodItems(dashboardService.totalFoodItems());
        dashData.setTotalOrders(dashboardService.totalOrders());
        dashData.setTotalUsers(dashboardService.totalUsers());
        dashData.setTotalCustomers(dashboardService.totalCustomers());
        dashData.setTotalOwners(dashboardService.totalOwners());

        return ResponseEntity.ok(dashData);
    }
}
