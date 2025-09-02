package com.example.leavemanagementsystem.dto;

import com.example.leavemanagementsystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// This DTO is for sending user info back to the client.
// Notice there's no password, and no complex objects like manager or teamMembers.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private int leaveBalance;
}