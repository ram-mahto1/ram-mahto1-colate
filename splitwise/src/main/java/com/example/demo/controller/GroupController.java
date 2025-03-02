package com.example.demo.controller;

import com.example.demo.dto.request.AddGroupMemberRequest;
import com.example.demo.dto.request.CreateGroupRequest;
import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public Group createGroup(@RequestBody CreateGroupRequest group) {
        return groupService.createGroup(group);
    }
    @PostMapping("/addMember")
    public Group addMember(@RequestBody AddGroupMemberRequest addGroupMemberRequest) {
        return groupService.addMember(addGroupMemberRequest);
    }
    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{groupId}")
    public Group getGroupById(@PathVariable Long groupId) {
        return groupService.getGroupById(groupId);
    }
}
