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
@Entity(name = "freight_bill_transaction")
public class FreightBillReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freightBillReportId;

    private String billNo;

    private String partyName;

    private String address;

    private String district;

    private String stateCode;

    private String gstNo;

    private String routeTo;

    private String codeNo;

    private String telephoneNo;

    private String ml;

    private String sac;


}
