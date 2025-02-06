package com.example.scheduleApp.repository;

import com.example.scheduleApp.entity.Author;
import com.example.scheduleApp.entity.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByAuthor(Author author);
    List<Schedule> findAllByUpdatedAtAfter(LocalDateTime updatedAt);


}
