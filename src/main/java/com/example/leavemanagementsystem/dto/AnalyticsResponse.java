package com.example.leavemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponse {
    private long pending;
    private long approved;
    private long rejected;
    private long total;
}