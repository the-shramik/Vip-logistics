package com.viplogistics.entity.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "nagpur_bill_transaction")
public class NagpurBillReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freightBillReportId;

    private String billNo;

    private String partyName;

    private String address;

    private String district;

    private String stateCode;

    private String gstNo;

    private String routeName;

    private String codeNo;

    private String telephoneNo;

    private String ml;

    private String sac;

    private Boolean isVerified;
}
