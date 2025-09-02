package com.example.leavemanagementsystem.service;

import com.example.leavemanagementsystem.dto.LeaveRequestResponseDto;
import com.example.leavemanagementsystem.dto.UserResponseDto;
import com.example.leavemanagementsystem.model.LeaveRequest;
import com.example.leavemanagementsystem.model.User;

public class Mapper {

    public static UserResponseDto toUserResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .leaveBalance(user.getLeaveBalance())
                .build();
    }

    public static LeaveRequestResponseDto toLeaveRequestResponseDto(LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            return null;
        }
        return LeaveRequestResponseDto.builder()
                .id(leaveRequest.getId())
                .employee(toUserResponseDto(leaveRequest.getEmployee()))
                .manager(toUserResponseDto(leaveRequest.getManager()))
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .reason(leaveRequest.getReason())
                .status(leaveRequest.getStatus())
                .requestedDate(leaveRequest.getRequestedDate())
                .build();
    }
}