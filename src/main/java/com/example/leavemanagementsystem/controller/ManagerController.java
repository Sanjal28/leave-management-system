package com.example.leavemanagementsystem.controller;

import com.example.leavemanagementsystem.dto.*; // Import all DTOs
import com.example.leavemanagementsystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerController {

    private final LeaveService leaveService;

    @GetMapping("/team")
    public ResponseEntity<List<UserResponseDto>> getTeamEmployees() { // Change return type
        return ResponseEntity.ok(leaveService.getTeamEmployees());
    }

    @GetMapping("/leave-requests")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeaveRequests() { // Change return type
        return ResponseEntity.ok(leaveService.getLeaveRequestsForManager());
    }

    @PutMapping("/leave-requests/{leaveId}/status")
    public ResponseEntity<LeaveRequestResponseDto> updateLeaveStatus(@PathVariable Long leaveId, @RequestBody UpdateLeaveStatusRequest request) { // Change return type
        return ResponseEntity.ok(leaveService.updateLeaveStatus(leaveId, request));
    }

    @PutMapping("/employee/{employeeId}/leave-balance")
    public ResponseEntity<UserResponseDto> updateEmployeeLeaveBalance(@PathVariable Long employeeId, @RequestBody UpdateLeaveBalanceRequest request) { // Change return type
        return ResponseEntity.ok(leaveService.updateEmployeeLeaveBalance(employeeId, request));
    }

    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponse> getLeaveAnalytics() {
        return ResponseEntity.ok(leaveService.getLeaveAnalytics());
    }
}