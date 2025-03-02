package com.example.demo.dto.request;

import com.example.demo.enums.SplitTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class AddExpenseRequest {
    private Long groupId;
    private Long paidBy;
    private Double amount;
    private SplitTypeEnum splitType;
    private List<Long> splitAmong;
    private List<Double> splitAmounts;

}
