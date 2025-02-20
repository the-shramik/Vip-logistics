package com.viplogistics.entity.transaction.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viplogistics.entity.transaction.LorryReceipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bill_lorry_receipt")
public class Bill{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private String billNo;

    private String billDate;

    private String unloadDate;

    private String billRNo;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LorryReceipt> lorryReceipts = new ArrayList<>();
}
