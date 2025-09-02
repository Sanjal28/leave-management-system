package com.example.leavemanagementsystem.repository;

import com.example.leavemanagementsystem.model.LeaveRequest;
import com.example.leavemanagementsystem.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByManagerId(Long managerId);
    List<LeaveRequest> findByManagerIdAndStatus(Long managerId, LeaveStatus status);
}