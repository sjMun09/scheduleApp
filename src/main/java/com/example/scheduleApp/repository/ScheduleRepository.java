package com.example.scheduleApp.repository;

import com.example.scheduleApp.entity.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByAuthorName(String authorName);
    List<Schedule> findAllByUpdatedAtAfter(LocalDateTime updatedAt);


}
