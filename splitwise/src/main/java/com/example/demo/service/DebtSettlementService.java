package com.example.demo.service;


import com.example.demo.model.Balance;
import com.example.demo.model.Group;
import com.example.demo.model.Transaction;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

@Service
public class DebtSettlementService {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<Transaction> simplifyDebt(Long groupId) {
        if( groupId == null || !groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Group id cannot be null or group does not exist");
        }
        List<Balance> balances = balanceRepository.findByGroupId(groupId);
        Map<Long, Double> balanceMap = new HashMap<>();

        for (Balance balance : balances) {
            balanceMap.put(balance.getUserId(), balanceMap.getOrDefault(balance.getUserId(), 0.0) + balance.getAmount());
        }

        return minimizeTransactionsFromMap(balanceMap);
    }

    private List<Transaction> minimizeTransactionsFromMap(Map<Long, Double> balances) {
        List<Transaction> transactions = new ArrayList<>();

        PriorityQueue<Map.Entry<Long, Double>> debtors = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        PriorityQueue<Map.Entry<Long, Double>> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (Map.Entry<Long, Double> entry : balances.entrySet()) {
            if (entry.getValue() < 0) debtors.add(entry);
            else if (entry.getValue() > 0) creditors.add(entry);
        }

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<Long, Double> debtor = debtors.poll();
            Map.Entry<Long, Double> creditor = creditors.poll();

            double debtAmount = Math.min(-debtor.getValue(), creditor.getValue());
            transactions.add(Transaction.builder().fromUser(debtor.getKey()).toUser(creditor.getKey()).amount(debtAmount).build());

            debtor.setValue(debtor.getValue() + debtAmount);
            creditor.setValue(creditor.getValue() - debtAmount);

            if (debtor.getValue() < 0) debtors.add(debtor);
            if (creditor.getValue() > 0) creditors.add(creditor);
        }

        return transactions;
    }
}
