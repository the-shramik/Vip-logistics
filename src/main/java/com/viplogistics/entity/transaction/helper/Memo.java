package com.viplogistics.entity.transaction.helper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viplogistics.entity.transaction.LorryHireMemo;
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
@Entity(name = "memo_lorry_receipt")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @Column(unique = true)
    private String memoNo;

    private String memoDate;

    private Boolean memoStatus;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LorryReceipt> lorryReceipts = new ArrayList<>();

    @OneToOne(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private LorryHireMemo lorryHireMemo;
}
