package com.viplogistics.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viplogistics.entity.transaction.LorryHireMemo;
import com.viplogistics.entity.transaction.LorryReceipt;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code Branch} - Represents a branch in the logistics system.
 *
 * This entity stores details of a branch, including its name, creation date,
 * and associated transactions like {@link LorryReceipt} and {@link LorryHireMemo}.
 *
 * <p>
 * Relationships:
 * <ul>
 *   <li>{@link LorryReceipt} - Represents the lorry receipts linked to the branch.</li>
 *   <li>{@link LorryHireMemo} - Represents the lorry hire memos linked to the branch.</li>
 * </ul>
 * </p>
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "branch_master")
public class Branch {

    @Id
    private String branchNo;

    private String branchName;

    private LocalDate branchDate;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<LorryReceipt> lorryReceipts;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<LorryHireMemo> lorryHireMemos;
}
