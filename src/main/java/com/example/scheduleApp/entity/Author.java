package com.example.scheduleApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
Schedule에 변수 추가하지않고 이렇게 따로 엔티티 클래스를 만든 이유
장점: 여러 개의 Schedule이 같은 Author를 가질 수 있음 → 데이터 정규화 가능.
즉, 작성자를 Author 엔티티로 관리하고, Schedule이 작성자를 참조하는 방식이다.
(주의) Schedule을 수정할 때 Author 정보를 직접 수정하지 않도록 변경 필요.
-> 이는, 스케줄서비스의 업데이트스케줄 메서드를 수정 하면되는거.
이를,
schedule.getAuthor().setName(updatedSchedule.getAuthor().getName());
작성자의 이름은 수정하지 않음. Schedule의 task만 수정.
schedule.setTask(updatedSchedule.getTask());
schedule.setUpdatedAt(LocalDateTime.now());
return scheduleRepository.save(schedule);
이런 식으로.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
