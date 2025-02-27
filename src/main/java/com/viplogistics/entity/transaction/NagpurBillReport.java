package com.viplogistics.entity.transaction;

import jakarta.persistence.*;
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

    @Column(unique = true)
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

    private String requestedBy;

    private String billDate;
}
