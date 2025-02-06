package com.example.scheduleApp.service;

import com.example.scheduleApp.dto.ScheduleRequest;
import com.example.scheduleApp.dto.ScheduleResponse;
import com.example.scheduleApp.entity.Author;
import com.example.scheduleApp.entity.Schedule;
import com.example.scheduleApp.exception.InvalidPasswordException;
import com.example.scheduleApp.exception.ScheduleNotFoundException;
import com.example.scheduleApp.repository.AuthorRepository;
import com.example.scheduleApp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    private final AuthorRepository authorRepository;

    public Schedule createSchedule(Schedule schedule, Long authorId) {
        if (schedule.getTask() == null || schedule.getTask().trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorId));
        schedule.setAuthor(author);
        return scheduleRepository.save(schedule);
    }


    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    //검색할 때는 Author를 먼저 찾고 검색
    // -> 이렇게 하면 jpa가 자동으로 Author을 검색하고, 그 author_id를 사용해서 Schedule를 찾음.
    public List<Schedule> getSchedulesByAuthorName(String authorName) {
        Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return scheduleRepository.findAllByAuthor(author);
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.getPassword().equals(updatedSchedule.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        schedule.setTask(updatedSchedule.getTask());
        schedule.setUpdatedAt(LocalDateTime.now());

        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        scheduleRepository.deleteById(id);
    }

}
