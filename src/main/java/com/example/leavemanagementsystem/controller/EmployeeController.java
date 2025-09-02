package com.example.leavemanagementsystem.controller;

import com.example.leavemanagementsystem.dto.LeaveRequestDto;
import com.example.leavemanagementsystem.dto.LeaveRequestResponseDto; // Change this import
import com.example.leavemanagementsystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class EmployeeController {

    private final LeaveService leaveService;

    @GetMapping("/leave-balance")
    public ResponseEntity<Integer> getLeaveBalance() {
        return ResponseEntity.ok(leaveService.getLeaveBalance());
    }

    @PostMapping("/apply-leave")
    public ResponseEntity<LeaveRequestResponseDto> applyForLeave(@RequestBody LeaveRequestDto requestDto) { // Change return type
        return ResponseEntity.ok(leaveService.applyForLeave(requestDto));
    }

    @GetMapping("/leave-history")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeaveHistory() { // Change return type
        return ResponseEntity.ok(leaveService.getEmployeeLeaveHistory());
    }
}