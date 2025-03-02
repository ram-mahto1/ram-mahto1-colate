package com.example.demo.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactSplit extends SplitType {
    @Override
    public Map<Long, Double> splitExpense(double totalAmount, List<Long> users, List<Double> amounts) {

        Map<Long, Double> result = new HashMap<>();
        for (int i = 0; i<users.size(); i++) {
            result.put(users.get(i), amounts.get(i));
        }
        return result;
    }
}
