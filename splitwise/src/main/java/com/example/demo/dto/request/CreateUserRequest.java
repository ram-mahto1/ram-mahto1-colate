package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String name;
    private String mobile;
}
