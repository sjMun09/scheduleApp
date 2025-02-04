# scheduleApp

### 프로젝트 세팅
- Spring web
- JPA
- MySQL Driver
- Lombok
- Spring Boot DevTools
- validation
- properties 말고 yml 사용
- Swagger
- Spring Boot 3.4.2
- Java 17
- gradle

<hr>


이 프로젝트를 기반으로 Spring Boot와 MySQL을 활용하여 일정 관리 시스템을 구축할 수 있도록, 다음과 같은 구조로 진행하면 될 것 같습니다.

1️⃣ 프로젝트 세팅
Spring Boot 프로젝트 생성

Spring Web
Spring Data JPA
MySQL Driver
Lombok
Spring Boot DevTools
Validation
application.yml 설정

MySQL 연결 설정
JPA 설정 (spring.jpa.hibernate.ddl-auto=update 초기 개발 단계에서 설정)
포트 및 기타 환경 설정

2️⃣ API 명세서 작성
프로젝트 root(README.md)에 Postman 또는 Swagger 기반으로 API 명세 작성
Request 및 응답 Response JSON 예시 포함

📌 일정 생성 API (POST /schedules)
```
{
  "todo": "회의 준비하기",
  "writer": "홍길동",
  "password": "1234"
}
```
📌 전체 일정 조회 API (GET /schedules?writer=홍길동&date=2024-01-31)
```
[
  {
    "id": 1,
    "todo": "회의 준비하기",
    "writer": "홍길동",
    "created_at": "2024-01-31 10:00:00",
    "updated_at": "2024-01-31 12:00:00"
  }
]
```
📌 단일 일정 조회 API (GET /schedules/{id})
```
{
  "id": 1,
  "todo": "회의 준비하기",
  "writer": "홍길동",
  "created_at": "2024-01-31 10:00:00",
  "updated_at": "2024-01-31 12:00:00"
}
```
📌 일정 수정 API (PUT /schedules/{id})
```
{
  "todo": "회의 자료 정리",
  "writer": "홍길동",
  "password": "1234"
}
```
📌 일정 삭제 API (DELETE /schedules/{id})
```
{
  "password": "1234"
}
```
3️⃣ ERD 설계
ERDCloud 또는 Draw.io를 활용하여 ERD 설계



4️⃣ SQL 작성
schedule.sql 파일에 테이블 생성 쿼리 작성
sql

```
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo VARCHAR(255) NOT NULL,
    writer VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

<hr>

현재 방식에선, 
- 요청 및 응답에서 password 필드를 포함하여 인증을 간단히 구현.
- writer(작성자) 기반으로 조회 가능
- 기본적인 JSON 형식으로 요청/응답 구조 제공

즉, 초기 개발에서는 CRUD 기능을 빠르게 구현하고 간단히 동작할 수 있도록

1. 사용자 인증 없이 일정(todo) 데이터를 생성, 조회, 수정, 삭제 가능
2. 비밀번호(password)를 활용하여 일정 수정 및 삭제 가능
3. 요청 파라미터 및 응답은 JSON 형식으로 통신
4. 간단한 API 구조 유지

이와 같이 설계 및 개발했습니다.

그러나 리팩토링 과정에서 jwt 기반 인증을 추가하여 보안을 강화할 예정입니다.
이를 통해 비밀번호 기반 인증을 제거하고, 인가 과정을 추가할 예정입니디.

#### 리팩토링시 주요 변경 사항
1. JWT 기반 사용자 인증 적용
- 일정 생성, 수정, 삭제 시 사용자가 JWT를 포함하여 요청해야 함.
- 일정 조회는 공개된 일정만 가능하며, 비공개 일정 조회 시 JWT 필요.

2. 비밀번호(password) 제거 및 사용자 인증 기반 접근 제어
- 기존에는 password 필드를 통해 일정 수정 및 삭제 가능
- 리팩토링 후, JWT의 사용자 정보와 일정 작성자(writer)를 비교하여 수정 및 삭제 가능하도록 변경

3. Access Token & Refresh Token 적용
- Access Token을 활용하여 사용자 인증
- Refresh Token을 도입하여 토큰 만료 시 재발급 처리
