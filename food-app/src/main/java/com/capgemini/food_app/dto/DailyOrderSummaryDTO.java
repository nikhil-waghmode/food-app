package com.capgemini.food_app.dto;

import java.time.LocalDate;

public interface DailyOrderSummaryDTO {
    String getRestaurantName();
    LocalDate getDate();
    Long getTotalOrders();
    Double getTotalRevenue();
}

