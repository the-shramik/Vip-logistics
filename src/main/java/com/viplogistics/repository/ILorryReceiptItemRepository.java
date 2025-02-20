package com.viplogistics.repository;

import com.viplogistics.entity.transaction.helper.LorryReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILorryReceiptItemRepository extends JpaRepository<LorryReceiptItem,Long> {

    LorryReceiptItem findByChalanNo(String chalanNo);
}
