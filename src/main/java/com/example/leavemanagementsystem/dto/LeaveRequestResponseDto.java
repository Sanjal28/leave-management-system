package com.example.leavemanagementsystem.dto;

import com.example.leavemanagementsystem.model.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// This DTO represents a leave request in the API response.
// It uses UserResponseDto to avoid the infinite loop.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestResponseDto {
    private Long id;
    private UserResponseDto employee;
    private UserResponseDto manager;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;
    private LocalDateTime requestedDate;
}