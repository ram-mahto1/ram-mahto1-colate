package com.example.demo.service;

import com.example.demo.dto.request.AddExpenseRequest;
import com.example.demo.enums.SplitTypeEnum;
import com.example.demo.exception.GenericException;
import com.example.demo.exception.GroupAlreadyExistsException;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Balance;
import com.example.demo.model.Group;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.strategy.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ExpenseService {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private Factory factory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    public boolean addExpense(AddExpenseRequest addExpenseRequest) {
        validateAddExpenseRequest(addExpenseRequest);
        SplitType splitType = factory.getSplitType(addExpenseRequest.getSplitType());
        Map<Long, Double> splitMap = splitType.splitExpense(addExpenseRequest.getAmount(), addExpenseRequest.getSplitAmong(), addExpenseRequest.getSplitAmounts());
        for (Map.Entry<Long, Double> entry : splitMap.entrySet()) {
            Long userId = entry.getKey();
            double share = entry.getValue();

            if (userId.equals(addExpenseRequest.getPaidBy())) continue;

            balanceRepository.save(Balance.builder().groupId(addExpenseRequest.getGroupId()).userId(addExpenseRequest.getPaidBy()).amount(share).build());
            balanceRepository.save(Balance.builder().groupId(addExpenseRequest.getGroupId()).userId(userId).amount(-share).build());

        }
        return true;
    }

    private void validateAddExpenseRequest(AddExpenseRequest addExpenseRequest) {
        if (addExpenseRequest.getAmount() <= 0) {
            throw new GenericException("Amount should be greater than 0");
        }
        if (addExpenseRequest.getSplitAmong().isEmpty()) {
            throw new GenericException("Split among should not be empty");
        }
        if (addExpenseRequest.getSplitType() != SplitTypeEnum.EQUAL && addExpenseRequest.getSplitAmounts().isEmpty() ) {
            throw new GenericException("Split amounts should not be empty");
        }
        if (addExpenseRequest.getSplitType() != SplitTypeEnum.EQUAL && addExpenseRequest.getSplitAmounts().size() != addExpenseRequest.getSplitAmong().size()) {
            throw new GenericException("Split amounts and split among should be of same size");
        }
        if (addExpenseRequest.getSplitType() != SplitTypeEnum.EQUAL && addExpenseRequest.getSplitAmounts().stream().mapToDouble(Double::doubleValue).sum() != addExpenseRequest.getAmount()) {
            throw new GenericException("Sum of split amounts should be equal to total amount");
        }
        List<User> userList = userRepository.findAllById(addExpenseRequest.getSplitAmong());
        if( userList.isEmpty() || userList.size() != addExpenseRequest.getSplitAmong().size()) {
            throw new UserNotFoundException("Some users not found");
        }
        Group group = groupRepository.findById(addExpenseRequest.getGroupId()).orElse(null);
        if( group == null) {
            throw new GroupNotFoundException("Group not found");
        }
        addExpenseRequest.getSplitAmong().forEach(userId -> {
            if ( !group.getMembers().stream().map(User::getId).collect(Collectors.toSet()).contains(userId)) {
                throw new GenericException("UserId : " +userId + " not part of group");
            }
        });


    }

}

