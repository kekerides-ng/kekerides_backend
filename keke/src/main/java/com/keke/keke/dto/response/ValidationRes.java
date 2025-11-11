package com.keke.keke.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationRes {
    private String username;
    private String message;
}
