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
@Entity(name = "chakan_bill_transaction")
public class ChakanBillReport {

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
}
