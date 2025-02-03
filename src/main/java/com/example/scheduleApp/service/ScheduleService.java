package com.example.scheduleApp.service;

import com.example.scheduleApp.dto.ScheduleRequest;
import com.example.scheduleApp.dto.ScheduleResponse;
import com.example.scheduleApp.entity.Schedule;
import com.example.scheduleApp.exception.InvalidPasswordException;
import com.example.scheduleApp.exception.ScheduleNotFoundException;
import com.example.scheduleApp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Schedule schedule = new Schedule(request.getTodo(), request.getWriter(), request.getPassword());
        scheduleRepository.save(schedule);
        return new ScheduleResponse(schedule);
    }

    // 전체 일정 조회 (작성자명 또는 수정일 기준)
    public List<ScheduleResponse> getAllSchedules(String writer, String date) {
        LocalDateTime startDate = date != null ? LocalDate.parse(date, DateTimeFormatter.ISO_DATE).atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime endDate = date != null ? startDate.plusDays(1) : LocalDateTime.MAX;

        return scheduleRepository.findByWriterContainingAndUpdatedAtBetweenOrderByUpdatedAtDesc(
                        writer != null ? writer : "", startDate, endDate)
                .stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }

    // 단일 일정 조회
    public ScheduleResponse getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다."));
        return new ScheduleResponse(schedule);
    }

    // 일정 수정 (비밀번호 인증)
    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 ID의 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        schedule.update(request.getTodo(), request.getWriter());
        return new ScheduleResponse(schedule);
    }

    // 일정 삭제 (비밀번호 인증)
    @Transactional
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
