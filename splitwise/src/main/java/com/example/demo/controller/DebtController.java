package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.model.Transaction;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.DebtSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/debt")
public class DebtController {
    @Autowired
    private DebtSettlementService debtSettlementService;


    @GetMapping("/getSimplifiedDebts/{groupId}")
    public List<Transaction> simplifyDebt(@PathVariable Long groupId) {
        return debtSettlementService.simplifyDebt(groupId);
    }
}
