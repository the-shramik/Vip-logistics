package com.viplogistics.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viplogistics.entity.transaction.LorryReceipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Route - Represents a route in the logistics system.
 *
 * This entity stores details about a specific route, including its origin, destination,
 * and associated items and receipts.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "route_master")
public class Route {

    @Id
    private String routeNo;

    private String routeName;

    private String routeFrom;

    private String routeTo;

    private Boolean isRoundUp;

    private LocalDate routeDate;

    private String gstType;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Item> items;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<LorryReceipt> lorryReceipts;

}
