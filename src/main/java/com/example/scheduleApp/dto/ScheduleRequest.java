package com.example.scheduleApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
    @NotBlank(message = "Task cannot be empty")
    private String task;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}