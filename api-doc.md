# API Docs
### 목차
- [1. OAuth](#11-oauth)
  - [테스트](#111-테스트)
- [2.1 게시글](#21-게시글)
    - [게시글 전체 조회](#211-게시글-전체-조회)
    - [게시글 필터링 적용 목록 조회](#212-게시글-필터링-적용-목록-조회)
    - [게시글 상세 조회](#213-게시글-상세-조회)
    - [게시글 저장](#214-게시글-저장)
- [2.2 댓글](#22-댓글)
    - [댓글 저장](#221-댓글-저장)
- [3. 파일 관리](#31-파일-관리)
  - [파일 저장](#311-파일-저장)
  - [파일 조회](#312-파일-조회)
- [4. 좋아요](#41-좋아요)
  - [좋아요 등록](#411-좋아요-등록)
- [5.1 회원](#51-회원-관리)
  - [회원 가입](#511-회원-가입)
  - [아이디 찾기](#512-아이디-찾기)
  - [비밀번호 찾기](#513-비밀번호-찾기)
  - [아이디 중복 확인](#514-회원가입-아이디-중복-확인)
- [5.2 마이페이지](#52-마이페이지)
  - [내가 작성한 댓글 목록 조회](#521-내가-작성한-댓글-목록-조회)
  - [내가 작성한 글 목록 조회](#522-내가-작성한-글-목록-조회)
- [6. 물품](#61-물품-관리)
  - [물품 저장](#611-물품-저장)
  - [물품 수정](#612-물품-수정)
  - [물품 조회](#613-물품-목록-조회)
- [7. 프로필 관리](#71-프로필-관리)
  - [프로필 생성](#711-프로필-생성)
  - [프로필 수정](#712-프로필-수정)
  - [프로필 조회](#713-프로필-조회)
- [8. 산책 기록](#81-산책-기록)
  - [산책 기록 조회](#811-산책-기록-조회)
  - [산책 기록 저장](#812-산책-기록-저장)
  - [산책 기록 통계 조회](#813-산책-통계-조회)
- [9. 산책 메이트](#91-산책-메이트)
    - [산책 메이트 등록](#911-산책-메이트-등록)
    - [산책 메이트 검색(조회)](#912-산책-메이트-검색조회)

## 1. Auth

### 1.1 OAuth
#### 1.1.1 테스트
```text
[Request]
- url : GET $SERVER_PATH/test
- type : applicaation/json

========================
[Response]
- type : application/json
- body
서버 배포 완료
```

## 2. Board
### 2.1 게시글
#### 2.1.1 게시글 전체 조회
```text
[Request]
- url : GET $SERVER_PATH/board
- type : application/json

========================
[Response]
`- List<BoardResponse>`
```

#### 2.1.2 게시글 필터링 적용 목록 조회
```text
[Request]
- url : GET $SERVER_PATH/board/filter
- type : application/json
- body
{
    "boardTags": [CAT, DOG],
    "title": "title"
}
========================
[Response]
- List<BoardResponse>
```

#### 2.1.3 게시글 상세 조회
```text
[Request]
- url : GET $SERVER_PATH/board/{boardId}
- type : application/json
- param
  - boardId : 게시글 아이디
========================
[Response]
- BoardResponse
```

#### 2.1.4 게시글 저장
```text
[Request]
- url : POST $SERVER_PATH/board
- type : multipart/form-data
- param
  - file : 이미지 파일
  - userId : 1
  - title : "title"
  - body : "body"
  - tagTypes : [CAT, DOG]
========================
[Response]
- BoardResponse
```

### 2.2 댓글
#### 2.2.1 댓글 저장
```text
[Request]
- url : POST $SERVER_PATH/comments
- type : application/json
- body
{
    "boardId" : 1, 
    "userId" : 1,
    "body" : "댓글내용"
}
========================
[Response]
- Comment
```

## 3. File
### 3.1 파일 관리
#### 3.1.1 파일 저장
```text
[Request]
- url : POST $SERVER_PATH/file
- type : multipart/form-data
- param
  - files : 이미지 파일 목록
  - imageType : BOARD (USER_PROFILE, BOARD, PRODUCT)
========================
[Response]
- List<FileInfo>
```

#### 3.1.2 파일 조회
```text
[Request]
- url : GET $SERVER_PATH/file
- type : application/json
- param
  - fileId : 1
========================
[Response]
- 바이트 목록
```

## 4. Like
### 4.1 좋아요
#### 4.1.1 좋아요 등록
```text
[Request]
- url : POST $SERVER_PATH/likes
- type : application/json
- body
{
    "memberId": 1,
    "boardId": 1
}
========================
[Response]
- Like
```

## 5. Member
### 5.1 회원 관리
### 5.1.1 회원 가입
```text
[Request]
- url : POST $SERVER_PATH/member/join
- type : application/json
- body
{
    "loginId": "loginId1",
    "password": "password12",
    "email": "test@test.com"
}
========================
[Response]
- Long (회원 ID)
```

### 5.1.2 아이디 찾기
```text
[Request]
- url : GET $SERVER_PATH/member/find-id
- type : application/json
- body
{
    "Email": "test@test.com"
}
========================
[Response]
- String (로그인 ID)
```

### 5.1.3 비밀번호 찾기
```text
[Request]
- url : GET $SERVER_PATH/member/find-pwd
- type : application/json
- body
{
    "loginId": "loginId123"
}
========================
[Response]
- String (비밀번호)
```

### 5.1.4 회원가입 아이디 중복 확인
```text
[Request]
- url : GET $SERVER_PATH/member/find-pwd
- type : application/json
- body
{
    "loginId": "loginId123"
}
========================
[Response]
- "중복이 아닙니다."
```

### 5.2 마이페이지
#### 5.2.1 내가 작성한 댓글 목록 조회
```text
[Request]
- url : GET $SERVER_PATH/mypage/comments
- type : application/json
- param
  - memberId : 1
========================
[Response]
- List<Comment>
```

#### 5.2.2 내가 작성한 글 목록 조회
```text
[Request]
- url : GET $SERVER_PATH/mypage/boards
- type : application/json
- param
  - memberId : 1
========================
[Response]
- List<BoardResponse>
```

## 6. Product
### 6.1 물품 관리
#### 6.1.1 물품 저장
```text
[Request]
- url : POST $SERVER_PATH/products
- type : multipart/form-data
- param
  - title : "제목"
  - body : "내용"
  - price : 13000
  - reservationStatus : false
  - saleStatus : false
  - tagType : CAT
  - file : 이미지_파일 
========================
[Response]
- Product
```

#### 6.1.2 물품 수정
```text
[Request]
- url : PATCH $SERVER_PATH/products/{productId}
- type : multipart/form-data
- path
  - productId : 물품 ID 
- param
  - title : "제목"
  - body : "내용"
  - price : 13000
  - reservationStatus : false
  - saleStatus : false
  - tagType : CAT
  - file : 이미지_파일 
========================
[Response]
- Product
```

#### 6.1.3 물품 목록 조회
```text
[Request]
- url : GET $SERVER_PATH/products
- type : application/json
- param
  - sort : LOW_PRICE
  - isReserved : true 
========================
[Response]
- List<Product>
```

## 7. Profile
### 7.1 프로필 관리
### 7.1.1 프로필 생성
```text
[Request]
- url : POST $SERVER_PATH/profiles/{memberId}
- type : application/json
- path
  - memberId : 멤버 ID 
- body
{
    "nickname" : "123123",
    "sex" : "FEMALE",
    "age" : 30,
    "petStatus" : "PETNO"
}
========================
[Response]
- Long
```

### 7.1.2 프로필 수정
```text
[Request]
- url : PATCH $SERVER_PATH/profiles/{memberId}
- type : application/json
- path
  - memberId : 멤버 ID 
- body
{
    "nickname" : "123123",
    "petStatus" : "PETNO"
}
========================
[Response]
- Void
```

### 7.1.3 프로필 조회
```text
[Request]
- url : PATCH $SERVER_PATH/profiles/{profileId}
- type : application/json
- path
  - memberId : 프로필 ID 
[Response]
- FindMyProfileDto
```

# 8. Walk
## 8.1 산책 기록
### 8.1.1 산책 기록 조회
```text
[Request]
- url : GET $SERVER_PATH/walk
- param
  - checkDate : 20240801 
[Response]
- Walk
```

### 8.1.2 산책 기록 저장
```text
[Request]
- url : POST $SERVER_PATH/walk
- body
{
    "profileId": 1,
    "checkDate": "20240801",
    "walkTime": "2024-08-01T00:01:20",
    "speed": 1.0,
    "distance": "2.3"
}
[Response]
- Walk
```

### 8.1.3 산책 통계 조회
```text
[Request]
- url : GET $SERVER_PATH/walk/statistics
- body
{
    "startDate": "20240701",
    "endDate": "20240801"
}
[Response]
- StatisticsWalkResponse
```

## 9. WalkMate
### 9.1 산책 메이트
#### 9.1.1 산책 메이트 등록
```text
[Request]
- url : POST $SERVER_PATH/walkmate
- body
{
    "memberId": 1,
    "sexType": "FEMALE",
    "age": 29,
    "hasPet": true,
    "location": "현재위치",
    "startDateTime": "2024-08-01T00:01:20",
    "endDateTime": "2024-08-02T00:01:20",
    "memo": "내 자랑 메모"
}
[Response]
- WalkMate
```

#### 9.1.2 산책 메이트 검색(조회)
```text
[Request]
- url : GET $SERVER_PATH/walkmate
- param
  - sexType : MALE
  - minAge : 20
  - maxAge : 30
  - hasPet : false
[Response]
- List<WalkMate>
```

## 10. Chat
### 10.1 채팅
#### 채팅 메시지 전송
```text
[Request]
- url : ws(웹소켓) $SERVER_PATH/send/{roomId}
- path
  - roomId : 1
- payload
{
  "message" : "hello",
  "senderId" : 1,
  "recipientId" : 1
}
[Response]
- Void
```

#### 채팅 메시지 삭제
```text
[Request]
- url : DELETE $SERVER_PATH/chat/delete/{messageId}
- path
  - messageId : 1
[Response]
- Void
```

#### 채팅방 조회
```text
[Request]
- url : GET $SERVER_PATH/chat/rooms/{userId}
- path
  - userId : 1
[Response]
- List<ChatRoomDto>
```

#### 채팅방 메시지 조회
```text
[Request]
- url : GET $SERVER_PATH/chat/rooms/{roomId}/messages
- path
  - roomId : 1
[Response]
- List<ChatMessageResponseDto>
```

#### 패팅방 삭제
```text
[Request]
- url : GET $SERVER_PATH/chat/rooms/{chatRoomId}
- path
  - chatRoomId : 1
[Response]
- Void
```