package com.viplogistics.entity.master;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.viplogistics.entity.master.helper.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Vehicle - Represents a vehicle in the logistics system.
 *
 * This entity stores details about a specific vehicle, including its ID, number, owner, and other related information.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vehicle_master")
public class Vehicle {

    @Id
    private String vehicleId;

    private String vehicleNumber;

    private String ownerName;

    private String driverName;

    private String permitNo;

    private String licenceNo;

    private String vehicleType;

    private String vehicleExpAcNo;

    @OneToOne(mappedBy = "vehicle",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Owner owner;

//    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
//    private LorryHireMemo lorryHireMemo;
}
