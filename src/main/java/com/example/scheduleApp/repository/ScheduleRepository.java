package com.example.scheduleApp.repository;

import com.example.scheduleApp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 수정일, 작성자명에 따라 일정 조회
    List<Schedule> findByWriterContainingAndUpdatedAtBetweenOrderByUpdatedAtDesc(
            String writer, LocalDateTime startDate, LocalDateTime endDate);
}
