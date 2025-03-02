package com.example.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequest {
    private String groupName;
    private List<Long> members;
}
