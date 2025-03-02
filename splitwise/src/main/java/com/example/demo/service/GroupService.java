package com.example.demo.service;

import com.example.demo.dto.request.AddGroupMemberRequest;
import com.example.demo.dto.request.CreateGroupRequest;
import com.example.demo.exception.GroupAlreadyExistsException;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;


    public Group createGroup(CreateGroupRequest groupRequest) {
        Group group = groupRepository.getGroupByGroupName(groupRequest.getGroupName());
        if (group != null) {
            throw new GroupAlreadyExistsException("Group with name " + groupRequest.getGroupName() + " already exists");
        }
        List<User> userList = userRepository.findAllById(groupRequest.getMembers());
        if(  userList.size() != groupRequest.getMembers().size()) {
            throw new GroupNotFoundException("Some users not found");
        }
        Group newGroup = Group.builder().groupName(groupRequest.getGroupName()).members(new HashSet<>(userList)).build();
        return groupRepository.save(newGroup);
    }

    public Group addMember(AddGroupMemberRequest addGroupMemberRequest) {
        Optional<Group> groupOpt = groupRepository.findById(addGroupMemberRequest.getGroupId());
        if(groupOpt.isEmpty()) {
            throw new GroupNotFoundException("Group with id " + addGroupMemberRequest.getGroupId() + " not found");
        }
        List<User> userList = userRepository.findAllById(addGroupMemberRequest.getMembers());
        if( userList.isEmpty() || userList.size() != addGroupMemberRequest.getMembers().size()) {
            throw new GroupNotFoundException("Some users not found");
        }


        Group group = groupOpt.get();
        group.getMembers().addAll(userList);
        return groupRepository.save(group);
    }

    public Group getGroupById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if(groupOpt.isEmpty()) {
            throw new GroupNotFoundException("Group with id " + groupId + " not found");
        }
        return groupOpt.get();
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
