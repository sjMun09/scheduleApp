package com.example.scheduleApp.controller;

import com.example.scheduleApp.dto.ScheduleRequest;
import com.example.scheduleApp.dto.ScheduleResponse;
import com.example.scheduleApp.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Tag(name = "일정 관리 API", description = "일정 생성, 조회, 수정, 삭제 기능을 제공합니다.")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "일정 생성", description = "새로운 일정을 생성합니다.")
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createSchedule(request));
    }

    @GetMapping
    @Operation(summary = "전체 일정 조회", description = "등록된 모든 일정을 조회합니다.")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules(
            @Parameter(description = "작성자명") @RequestParam(required = false) String writer,
            @Parameter(description = "수정일 (YYYY-MM-DD)") @RequestParam(required = false) String date) {
        return ResponseEntity.ok(scheduleService.getAllSchedules(writer, date));
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 일정 조회", description = "선택한 일정의 정보를 조회합니다.")
    public ResponseEntity<ScheduleResponse> getScheduleById(
            @Parameter(description = "일정 ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

}
