package com.example.demo.dto.request;

import com.example.demo.enums.SplitTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class AddGroupMemberRequest {
    private Long groupId;
    private List<Long> members;
}
