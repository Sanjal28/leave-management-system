package com.example.leavemanagementsystem.dto;

import com.example.leavemanagementsystem.model.LeaveStatus;
import lombok.Data;

@Data
public class UpdateLeaveStatusRequest {
    private LeaveStatus status;
}