package com.example.scheduleApp.controller;

import com.example.scheduleApp.dto.ScheduleRequest;
import com.example.scheduleApp.dto.ScheduleResponse;
import com.example.scheduleApp.entity.Author;
import com.example.scheduleApp.entity.Schedule;
import com.example.scheduleApp.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
// @Tag(name = "일정 관리 API", description = "일정 생성, 조회, 수정, 삭제 기능을 제공합니다.")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/{authorId}")
    public Schedule createSchedule(@RequestBody @Valid ScheduleRequest request,
                                   @PathVariable Long authorId) {
        System.out.println("Received authorId: " + authorId);  // 디버깅용 로그

        if (authorId == null) {
            throw new IllegalArgumentException("authorId is null!");
        }

        Schedule schedule = new Schedule();
        schedule.setTask(request.getTask());
        schedule.setPassword(request.getPassword());
        return scheduleService.createSchedule(schedule, authorId);
    }



    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/author")
    public List<Schedule> getSchedulesByAuthorName(@RequestParam String authorName) {
        return scheduleService.getSchedulesByAuthorName(authorName);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {
        return scheduleService.updateSchedule(id, updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
        return "Schedule deleted successfully";
    }
}
