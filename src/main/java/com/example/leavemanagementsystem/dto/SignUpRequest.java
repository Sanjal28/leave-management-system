package com.example.leavemanagementsystem.dto;

import com.example.leavemanagementsystem.model.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String managerEmail; // Optional: for employees to link to their manager
}