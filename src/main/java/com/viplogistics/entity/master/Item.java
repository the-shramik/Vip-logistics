package com.viplogistics.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Item - Represents an item in the logistics system.
 *
 * This entity stores information about an item, including its associated route,
 * party, and various item attributes such as quantity, weight, and rate.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item_master")
public class Item {

    @Id
    private String itemNo;


    @ManyToOne
    @JoinColumn(name = "route_no", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "party_no", nullable = false)
    private Party party;

    private String itemName;

    private LocalDate itemDate;

    private String partNo;

    private Integer qtyInBox;

    private Double weightPerBox;

    private Double rateOnBox;

    private Double rateOnWeight;

    private String pu;

    private String vendorCode;

}
