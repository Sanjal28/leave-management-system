package com.example.leavemanagementsystem.service;

import com.example.leavemanagementsystem.dto.*;
import com.example.leavemanagementsystem.model.LeaveRequest;
import com.example.leavemanagementsystem.model.LeaveStatus;
import com.example.leavemanagementsystem.model.User;
import com.example.leavemanagementsystem.repository.LeaveRequestRepository;
import com.example.leavemanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // --- Employee Methods ---

    public int getLeaveBalance() {
        return getCurrentUser().getLeaveBalance();
    }

    public LeaveRequestResponseDto applyForLeave(LeaveRequestDto requestDto) {
        User employee = getCurrentUser();
        User manager = userRepository.findByEmail(requestDto.getManagerEmail())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        long requestedDays = ChronoUnit.DAYS.between(requestDto.getStartDate(), requestDto.getEndDate()) + 1;
        if (employee.getLeaveBalance() < requestedDays) {
            throw new RuntimeException("Insufficient leave balance");
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .manager(manager)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .reason(requestDto.getReason())
                .status(LeaveStatus.PENDING)
                .build();
        LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);
        return Mapper.toLeaveRequestResponseDto(savedRequest);
    }

    public List<LeaveRequestResponseDto> getEmployeeLeaveHistory() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(getCurrentUser().getId());
        return leaveRequests.stream()
                .map(Mapper::toLeaveRequestResponseDto)
                .collect(Collectors.toList());
    }

    // --- Manager Methods ---

    public List<UserResponseDto> getTeamEmployees() {
        List<User> teamMembers = userRepository.findByManagerId(getCurrentUser().getId());
        return teamMembers.stream()
                .map(Mapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    public List<LeaveRequestResponseDto> getLeaveRequestsForManager() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByManagerId(getCurrentUser().getId());
        return leaveRequests.stream()
                .map(Mapper::toLeaveRequestResponseDto)
                .collect(Collectors.toList());
    }

    public LeaveRequestResponseDto updateLeaveStatus(Long leaveId, UpdateLeaveStatusRequest statusRequest) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        User manager = getCurrentUser();
        if(!leaveRequest.getManager().getId().equals(manager.getId())){
            throw new RuntimeException("You are not authorized to update this leave request");
        }

        if(statusRequest.getStatus() == LeaveStatus.APPROVED && leaveRequest.getStatus() == LeaveStatus.PENDING) {
            User employee = leaveRequest.getEmployee();
            long requestedDays = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;
            int newBalance = employee.getLeaveBalance() - (int) requestedDays;
            if (newBalance < 0) {
                throw new RuntimeException("Employee has insufficient leave balance");
            }
            employee.setLeaveBalance(newBalance);
            userRepository.save(employee);
        }

        leaveRequest.setStatus(statusRequest.getStatus());
        LeaveRequest updatedRequest = leaveRequestRepository.save(leaveRequest);
        return Mapper.toLeaveRequestResponseDto(updatedRequest);
    }

    public UserResponseDto updateEmployeeLeaveBalance(Long employeeId, UpdateLeaveBalanceRequest balanceRequest) {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User manager = getCurrentUser();
        if(employee.getManager() == null || !employee.getManager().getId().equals(manager.getId())){
            throw new RuntimeException("You are not authorized to update this employee's balance");
        }

        employee.setLeaveBalance(balanceRequest.getNewBalance());
        User updatedEmployee = userRepository.save(employee);
        return Mapper.toUserResponseDto(updatedEmployee);
    }

    public AnalyticsResponse getLeaveAnalytics() {
        Long managerId = getCurrentUser().getId();
        long pending = leaveRequestRepository.findByManagerIdAndStatus(managerId, LeaveStatus.PENDING).size();
        long approved = leaveRequestRepository.findByManagerIdAndStatus(managerId, LeaveStatus.APPROVED).size();
        long rejected = leaveRequestRepository.findByManagerIdAndStatus(managerId, LeaveStatus.REJECTED).size();
        return new AnalyticsResponse(pending, approved, rejected, pending + approved + rejected);
    }
}