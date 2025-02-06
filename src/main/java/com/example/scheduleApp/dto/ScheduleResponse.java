package com.example.scheduleApp.dto;

import com.example.scheduleApp.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "일정 응답 DTO")
public class ScheduleResponse {

}
