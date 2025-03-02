package com.example.demo.controller;

import com.example.demo.dto.request.AddExpenseRequest;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public boolean addExpense(@RequestBody AddExpenseRequest addExpenseRequest) {
        return expenseService.addExpense(addExpenseRequest);
    }
}
