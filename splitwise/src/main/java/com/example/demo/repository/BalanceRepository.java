package com.example.demo.repository;

import com.example.demo.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findByUserId(Long userId);
   List<Balance> findByGroupId(Long groupId);
}
