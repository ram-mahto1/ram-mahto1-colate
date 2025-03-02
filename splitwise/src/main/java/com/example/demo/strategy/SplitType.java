package com.example.demo.strategy;

import java.util.List;
import java.util.Map;

public abstract class SplitType {
    public abstract Map<Long, Double> splitExpense(double totalAmount, List<Long> users, List<Double> amounts);
}
