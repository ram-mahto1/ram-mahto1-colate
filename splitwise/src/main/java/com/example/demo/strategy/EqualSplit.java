package com.example.demo.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplit extends SplitType {
    @Override
    public Map<Long, Double> splitExpense(double totalAmount, List<Long> users, List<Double> amounts) {
        int numUsers = users.size();
        double share = totalAmount / numUsers;

        Map<Long, Double> result = new HashMap<>();
        for (Long user : users) {
            result.put(user, share);
        }
        return result;
    }
}
