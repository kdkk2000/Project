---------- AdminDao 관련(관리자 등록,전체보기,특정 관리작 보기, 정보변경, 삭제) --------------
DROP TABLE ADMINS;

CREATE TABLE ADMINS (
    adminId VARCHAR2(50) PRIMARY KEY,
    adminPw VARCHAR2(50) NOT NULL,
    adminName VARCHAR2(50) NOT NULL,
    adminEmail VARCHAR2(100),
    adminPhone VARCHAR2(50)
);
-- 1. 관리자 id 중복체크
SELECT * FROM ADMINS WHERE adminId = 'aaa';

-- 2. 이메일 중복 체크
SELECT COUNT(*) CNT FROM ADMINS WHERE adminEmail='aaa@a.a';

-- 3. 관리자 등록
INSERT INTO ADMINS (adminId, adminPw, adminName, adminEmail, adminPhone) VALUES ('관리자', 'a', '관리자', 'admin@add.co.kr', '010-1234-1234');

-- 4. 관리자 로그인
SELECT * FROM ADMINS WHERE adminId = 'aaa';

-- 5. 특정 아이디를 가진 관리자 보기
SELECT * FROM ADMINS WHERE adminId = 'aaa';

-- 6. 관리자 정보 변경
UPDATE ADMINS SET adminPw = 'b', adminName = '변경된 관리자 이름', adminEmail = 'admin1@add.co.kr', adminPhone = '010-1234-1234' WHERE adminId = 'aaa';

commit;

--==================================================================================--
---------- USERDao 관련(회원id 중복체크, 이메일 중복 체크, 회원가입, 로그인 체크, userId로 dto 가져오기, 회원정보 수정, 전체 회원 리스트, 전체 가입수, 회원 탈퇴  )--------------
DROP TABLE USERS;

CREATE TABLE USERS (
    userId VARCHAR2(50) PRIMARY KEY,
    userPw VARCHAR2(50) NOT NULL,
    userName VARCHAR2(50) NOT NULL,
    userEmail VARCHAR2(100) UNIQUE NOT NULL,
    userPhone VARCHAR2(50),
    userAddress VARCHAR2(200),
    userDate DATE DEFAULT SYSDATE NOT NULL
);
-- 1. 회원id 중복체크
SELECT * FROM USERS WHERE userId='aaa';

-- 2. 이메일 중복 체크
SELECT COUNT(*) CNT FROM USERS WHERE userEmail='aaa@a.a';

-- 3. 회원가입
INSERT INTO USERS (userId, userPw, userName, userEmail, userPhone, userAddress) 
VALUES ('aaa', 'a', '홍', 'aaa@a.a', '010-1234-1234', '서울시 서대문구');

-- 4. 로그인 체크
SELECT * FROM USERS WHERE userId='aaa';

-- 5. userId로 dto 가져오기(로그인 성공시 session에 넣기 위함)
SELECT * FROM USERS WHERE userId='aa';

-- 6. 회원정보 수정
UPDATE USERS SET userPw='a', userName='a', userEmail='a1a@ma.co.kr', userPhone='010-8520-0258', userAddress='서울시 마포구' 
WHERE userId='aa';


-- 7. 전체 회원 리스트
SELECT * 
    FROM (SELECT ROWNUM RN, A.* FROM (SELECT * FROM USERS ORDER BY userDate DESC) A) 
    WHERE RN BETWEEN 1 AND 3;

-- 8. 전체 회원가입한 회원수
SELECT COUNT(*) CNT FROM USERS;

-- 9. 회원탈퇴
DELETE FROM USERS WHERE userId = 'aaa';

commit;

--==================================================================================--
---------- BoardDao 관련(글 작성, 글 상세보기, 글 수정, 글 답변, 글 삭제 등)--------------
DROP TABLE FILEBOARD;
DROP SEQUENCE FILEBOARD_SEQ;

CREATE SEQUENCE FILEBOARD_SEQ START WITH 1 INCREMENT BY 1 nocache nocycle;

CREATE TABLE FILEBOARD (
    boardId NUMBER(6) PRIMARY KEY,
    userId VARCHAR2(50) REFERENCES USERS(userId),
    boardTitle VARCHAR2(100) NOT NULL ,
    boardContent VARCHAR2(4000) NOT NULL,
    boardFileName VARCHAR2(100),
    boardDate DATE DEFAULT SYSDATE,
    boardHit NUMBER(6) DEFAULT 0,
    boardGroup NUMBER(6) NOT NULL,
    boardStep NUMBER(6) NOT NULL,
    boardIndent NUMBER(6) NOT NULL,
    boardIp VARCHAR2(30) NOT NULL
);

-- (1) 글목록(startRow~endRow)
SELECT F.*, userName FROM FILEBOARD F, USERS U
  WHERE F.userId=U.userId 
  ORDER BY boardGroup DESC, boardStep; -- 출력 기준
SELECT * FROM
  (SELECT ROWNUM RN, A.* FROM (SELECT F.*, userName FROM FILEBOARD F, USERS U
                              WHERE F.userId=U.userId 
                              ORDER BY boardGroup DESC, boardStep) A)
  WHERE RN BETWEEN 1 AND 4; -- dao에 쓸 query
  
-- (2) 글갯수
SELECT COUNT(*) FROM FILEBOARD;

-- (3) 글쓰기(원글쓰기)
INSERT INTO FILEBOARD (boardId, userId, boardTitle, boardContent, boardFileName, boardGroup, boardStep, boardIndent, boardIp)
  VALUES (FILEBOARD_SEQ.NEXTVAL, 'aaa', '토트넘', '난 공격수', 'a.docx', 
    FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.31');
    
-- (4) hit 1회 올리기
UPDATE FILEBOARD SET boardHit = boardHit + 1 WHERE boardId=2;

-- (5) 글번호(boardId)로 글전체 내용(BoardDto) 가져오기
SELECT F.*, USERNAME
  FROM FILEBOARD F, USERS U WHERE F.USERID=U.USERID AND BOARDID=2;
  
-- (6) 글 수정하기(boardId, boardTitle, boardContent, boardFileName, boardDate(SYSDATE), boardIp 수정)
UPDATE FILEBOARD SET boardTitle = '바뀐제목',
                    boardContent = '바뀐본문',
                    boardFileName = NULL,
                    boardIp = '192.168.151.10',
                    boardDate = SYSDATE
            WHERE boardId = 5;

COMMIT;
-- (7) 글 삭제하기
-- 글 삭제시 해당 글 하나 삭제하기 (삭제하려는 글의 boardId필요)
DELETE FROM FILEBOARD WHERE boardId=1;

-- (8) 답변글 쓰기 전 단계(원글의 boardGroup과 같고, 원글의 boardStep보다 크면 boardStep을 하나 증가하기)
UPDATE FILEBOARD SET boardStep = boardStep + 1 WHERE boardGroup=3 AND boardStep>0;

-- (9) 답변글 쓰기
INSERT INTO FILEBOARD (boardId, userId, boardTitle, boardContent, boardFileName, boardGroup, boardStep, boardIndent, boardIp)
    VALUES (FILEBOARD_SEQ.NEXTVAL, 'aaa', 'aaa', '난 공격수', 'a.docx', FILEBOARD_SEQ.CURRVAL, 0, 0, '192.168.0.31');

-- (10) 회원탈퇴시 탈퇴하는 회원(userId)이 쓴 글 모두 삭제하기
DELETE FROM FILEBOARD WHERE userId='song';

COMMIT;

--==================================================================================--
---------- NoticeDao 관련(공지사항 전체조회, 상세조회, 등록, 수정, 삭제)--------------
DROP TABLE NOTICE;
DROP SEQUENCE NOTICE_SEQ;

CREATE SEQUENCE NOTICE_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE 
    NOCYCLE;
    
CREATE TABLE NOTICE (
    noticeId NUMBER(6) PRIMARY KEY,
    adminId VARCHAR2(50) REFERENCES ADMINS(adminId),
    noticeTitle VARCHAR2(100) NOT NULL ,
    noticeContent VARCHAR2(4000) NOT NULL,
    noticeFileName VARCHAR2(100),
    noticeDate DATE DEFAULT SYSDATE,
    noticeHit NUMBER(6) DEFAULT 0,
    noticeGroup NUMBER(6) NOT NULL,
    noticeStep NUMBER(6) NOT NULL,
    noticeIndent NUMBER(6) NOT NULL,
    noticeIp VARCHAR2(30) NOT NULL
);

-- (1) 공지사항 글목록(startRow~endRow)
SELECT N.*, adminName FROM NOTICE N, ADMINS A
  WHERE N.adminID=A.adminID 
  ORDER BY noticeGroup DESC, noticeStep; -- 출력 기준
  
SELECT * FROM
  (SELECT ROWNUM NUM, NUM.* FROM (SELECT N.*, adminName FROM NOTICE N, ADMINS A
                              WHERE N.adminID=A.adminID 
                              ORDER BY noticeGroup DESC, noticeStep) NUM)
  WHERE NUM BETWEEN 1 AND 4; -- dao에 쓸 query
  
-- (2) 공지사항 글갯수
SELECT COUNT(*) FROM NOTICE;

-- (3) 공지사항 글쓰기
INSERT INTO NOTICE (noticeId, adminId, noticeTitle, noticeContent, noticeFileName, noticeGroup, noticeStep, noticeIndent, noticeIp)
  VALUES (NOTICE_SEQ.NEXTVAL, '관리자', '공지사항', '공지내용', 'a.docx', 
    NOTICE_SEQ.CURRVAL, 0, 0, '192.168.0.31');
    
-- (4) 공지사항 hit 1회 올리기
UPDATE NOTICE SET noticeHit = noticeHit + 1 WHERE noticeId=1;

-- (5) 공지사항 글 번호(noticeId)로 글전체 내용(NoticeDto) 가져오기
SELECT N.*, adminName
  FROM NOTICE N, ADMINS A WHERE N.adminId=A.adminId AND noticeId=1;
  
-- (6) 공지사항 글 수정하기(noticeId, noticeTitle, noticeContent, noticeFileName, noticeDate(SYSDATE), noticeIp 수정)
UPDATE NOTICE SET noticeTitle = '바뀐제목',
                    noticeContent = '바뀐본문',
                    noticeFileName = NULL,
                    noticeIp = '192.168.151.10',
                    noticeDate = SYSDATE
            WHERE noticeId = 5;

COMMIT;
-- (7) 공지사항 글 삭제하기
-- 글 삭제시 해당 글 하나 삭제하기 (삭제하려는 글의 noticeId필요)
DELETE FROM NOTICE WHERE noticeId=1;

COMMIT;
