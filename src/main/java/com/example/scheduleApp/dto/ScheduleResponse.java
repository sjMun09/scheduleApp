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

    @Schema(description = "일정 ID", example = "1")
    private Long id;

    @Schema(description = "할 일", example = "회의 준비하기")
    private String todo;

    @Schema(description = "작성자명", example = "홍길동")
    private String writer;

    @Schema(description = "작성일", example = "2024-01-31T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정일", example = "2024-01-31T12:00:00")
    private LocalDateTime updatedAt;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.writer = schedule.getWriter();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
