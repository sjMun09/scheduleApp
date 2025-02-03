package com.example.scheduleApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "일정 생성 요청 DTO")
public class ScheduleRequest {

    @Schema(description = "할 일", example = "회의 준비하기")
    private String todo;

    @Schema(description = "작성자명", example = "홍길동")
    private String writer;

    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
