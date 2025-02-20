package com.viplogistics.entity.master.helper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.viplogistics.entity.master.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Owner - Represents the vehicle owner and maintenance-related details.
 *
 * This entity contains information about various maintenance and legal compliance
 * aspects such as oil service, insurance, RTO tax, fitness, and permits.
 *
 * @see Vehicle
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vehicle_owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    private String startReading;

    private String oilServiceDate;

    private Integer oilServiceKm;

    private Integer oilServiceTargetKm;

    private String greasePackingDate;

    private Integer greasePackingKm;

    private Integer greasePackingTargetKm;

    private String insuranceDate;

    private String insuranceFrom;

    private String insuranceTo;

    private String insuranceNextDate;

    private String rtoTaxDate;

    private String rtoTaxFrom;

    private String rtoTaxTo;

    private String rtoTaxNextDate;

    private String fitnessDate;

    private String fitnessFrom;

    private String fitnessTo;

    private String fitnessNextDate;

    private String permitDate;

    private String permitFrom;

    private String permitTo;

    private String permitNextDate;

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonBackReference
    private Vehicle vehicle;
}
