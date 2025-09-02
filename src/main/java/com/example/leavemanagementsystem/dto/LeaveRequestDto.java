package com.example.leavemanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestDto {
    private String managerEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}