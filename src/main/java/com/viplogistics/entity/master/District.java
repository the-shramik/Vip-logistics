package com.viplogistics.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * District - Represents a district in the logistics system.
 *
 * This entity stores details of a district, including its name and code.
 * Each district is identified by a unique ID.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "district_master")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtId;

    private String districtName;

    private String districtCode;
}
