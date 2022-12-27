USE bulmeong;

DROP TABLE IF EXISTS PAY;
DROP TABLE IF EXISTS RESERVE;
DROP TABLE IF EXISTS CAMP_TENT;
DROP TABLE IF EXISTS CAMP_CART;
DROP TABLE IF EXISTS REVIEW_CMT;
DROP TABLE IF EXISTS REVIEW_BRD;
DROP TABLE IF EXISTS USED_CART;
DROP TABLE IF EXISTS USED_CMT;
DROP TABLE IF EXISTS USED_BRD;
DROP TABLE IF EXISTS FREE_CMT;
DROP TABLE IF EXISTS FREE_LIKE;
DROP TABLE IF EXISTS FREE_BRD;
DROP TABLE IF EXISTS FAQ;
DROP TABLE IF EXISTS SUMMERNOTE_IMAGE;
DROP TABLE IF EXISTS NOTICE;
DROP TABLE IF EXISTS QNA;
DROP TABLE IF EXISTS CAMP;
DROP TABLE IF EXISTS USERS_ACCESS;
DROP TABLE IF EXISTS USERS_RETIRE;
DROP TABLE IF EXISTS USERS_SLEEP;
DROP TABLE IF EXISTS USERS;





CREATE TABLE USERS (
   USERS_NO   INT      NOT NULL AUTO_INCREMENT,
   ID   VARCHAR(50)      NOT NULL UNIQUE,
   NICKNAME   VARCHAR(50)      NOT NULL UNIQUE,
   PW   VARCHAR(64)      NOT NULL,
   NAME   VARCHAR(50)      NOT NULL,
   MOBILE   VARCHAR(11)      NOT NULL,
   GENDER   VARCHAR(2)      NOT NULL,
   BIRTH_YEAR   VARCHAR(4)     NOT NULL,
   BIRTH_DAY   VARCHAR(4)     NOT NULL,
   POSTCODE   VARCHAR(5)      NULL,
   ROAD_ADDRESS   VARCHAR(100)      NULL,
   JIBUN_ADDRESS   VARCHAR(100)      NULL,
   DETAIL_ADDRESS   VARCHAR(100)      NULL,
   EXTRA_ADDRESS   VARCHAR(100)      NULL,
   JOIN_DATE   VARCHAR(20)      NOT NULL,
   AGREE_CODE   SMALLINT      NOT NULL,
   SNS_TYPE   VARCHAR(10)      NULL,
   SESSION_ID   VARCHAR(46)      NULL,
   SESSION_LIMIT_DATE   VARCHAR(20)      NULL,
   EMAIL   VARCHAR(100)      NOT NULL UNIQUE,
   AGE   SMALLINT      NULL,
   PROFILE_IMAGE   VARCHAR(255)      NULL DEFAULT '/images/userimage/basic_profileImage.png',
   PW_MODIFY_DATE VARCHAR(20), 
   INFO_MODIFY_DATE VARCHAR(20),
   CONSTRAINT PK_USERS PRIMARY KEY(USERS_NO)
);

CREATE TABLE USERS_SLEEP (
   USERS_NO   INT      NOT NULL AUTO_INCREMENT,
   ID   VARCHAR(50)      NOT NULL UNIQUE,
   NICKNAME   VARCHAR(50)      NOT NULL UNIQUE,
   PW   VARCHAR(64)      NOT NULL,
   NAME   VARCHAR(50)      NOT NULL,
   MOBILE   VARCHAR(11)      NOT NULL,
   GENDER   VARCHAR(2)      NOT NULL,
   BIRTH_YEAR   VARCHAR(4)      NULL,
   BIRTH_DAY   VARCHAR(4)      NULL,
   POSTCODE   VARCHAR(5)      NULL,
   ROAD_ADDRESS   VARCHAR(100)      NULL,
   JIBUN_ADDRESS   VARCHAR(100)      NULL,
   DETAIL_ADDRESS   VARCHAR(100)      NULL,
   EXTRA_ADDRESS   VARCHAR(100)      NULL,
   JOIN_DATE   VARCHAR(20)      NOT NULL,
   AGREE_CODE   SMALLINT      NOT NULL,
   SNS_TYPE   VARCHAR(10)      NULL,
   SESSION_ID   VARCHAR(46)      NULL,
   SESSION_LIMIT_DATE   VARCHAR(20)      NULL,
   EMAIL   VARCHAR(50)      NOT NULL UNIQUE,
   SLEEP_DATE   VARCHAR(20)      NULL,
   AGE   SMALLINT      NULL,
   PROFILE_IMAGE   VARCHAR(255)      NULL DEFAULT '/images/userimage/basic_profileImage.png',
   LAST_LOGIN_DATE   VARCHAR(20)      NULL,
   PW_MODIFY_DATE VARCHAR(20), 
   INFO_MODIFY_DATE VARCHAR(20),
   CONSTRAINT PK_USERS_SLEEP PRIMARY KEY(USERS_NO)
);

CREATE TABLE USERS_RETIRE (
   RETIRE_NO   INT      NOT NULL AUTO_INCREMENT,
   ID   VARCHAR(46)      NOT NULL,
   NICKNAME   VARCHAR(50)      NOT NULL,
   RETIRE_DATE   VARCHAR(20)      NULL,
   JOIN_DATE   VARCHAR(20)      NULL,
   CONSTRAINT PK_USERS_RETIRE PRIMARY KEY(RETIRE_NO)
);

CREATE TABLE USERS_ACCESS (
   ID   VARCHAR(50)      NOT NULL,
   LAST_LOGIN_DATE   VARCHAR(20)      NOT NULL,
   CONSTRAINT FK_ACCESS_USERS FOREIGN KEY(ID) REFERENCES USERS (ID) ON DELETE CASCADE
);

CREATE TABLE CAMP (
   CAMP_NO  INT      NOT NULL AUTO_INCREMENT,
   facltNm   BLOB      NOT NULL,
   intro   BLOB      NULL,
   lineIntro   BLOB      NULL,
   featureNm   BLOB      NULL,
   sbrsCl   BLOB      NULL,
   firstImageUrl   BLOB      NULL,
   themaEnvrnCl   BLOB      NULL,
   eqpmnLendCl   BLOB      NULL,
   tourEraCl   BLOB      NULL,
   induty   BLOB      NULL,
   allar   BLOB      NULL,
   operPdCl   BLOB      NULL,
   hvofBgnde   BLOB      NULL,
   hvofEnddle   BLOB      NULL,
   prmisnDe   BLOB      NULL,
   operDeCl   BLOB      NULL,
   toiletCo   BLOB      NULL,
   extshrCo   BLOB      NULL,
   brazierCl   BLOB      NULL,
   glampInnerFclty   BLOB      NULL,
   caravInnerFclty   BLOB      NULL,
   lctCl   BLOB      NULL,
   addr1   BLOB      NULL,
   doNm   BLOB      NULL,
   sigunguNm   BLOB      NULL,
   addr2   BLOB      NULL,
   zipcode   BLOB      NULL,
   mapX   BLOB      NULL,
   mapY   BLOB      NULL,
   homepage   BLOB      NULL,
   tel   BLOB      NULL,
   createdtime   BLOB      NULL,
   modifiedtime   BLOB      NULL,
   jjim      INT         NULL   DEFAULT 0,
   SUM      INT         NULL   DEFAULT 0,
   CONSTRAINT PK_CAMP PRIMARY KEY(CAMP_NO)
);

CREATE TABLE FREE_BRD (
   FREE_NO         INT          NOT NULL AUTO_INCREMENT,
   NICKNAME         VARCHAR(50)  NOT NULL,
   FREE_TITLE      VARCHAR(40)  NOT NULL,
   FREE_CONTENT      VARCHAR(500) NULL,
   FREE_CREATE_DATE VARCHAR(20)  NOT NULL,
   FREE_MODIFY_DATE VARCHAR(20)  NOT NULL,
   FREE_IP          VARCHAR(30)  NOT NULL,
   FREE_HIT         INT          NOT NULL,
   CONSTRAINT PK_FREE_BRD PRIMARY KEY(FREE_NO),
   CONSTRAINT FK_FREEBRD_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);
CREATE TABLE FREE_LIKE (
   FREE_NO  INT         NOT NULL,
   NICKNAME VARCHAR(50) NOT NULL,
   CONSTRAINT FK_FREELIKE_FREEBRD FOREIGN KEY(FREE_NO) REFERENCES FREE_BRD (FREE_NO) ON DELETE CASCADE,
   CONSTRAINT FK_FREELIKE_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);
CREATE TABLE FREE_CMT (
   FREE_CMT_NO         INT          NOT NULL AUTO_INCREMENT,
   FREE_NO            INT          NOT NULL,
   NICKNAME            VARCHAR(50)  NOT NULL,
   FREE_CMT_CONTENT     VARCHAR(500) NOT NULL,
   FREE_CMT_CREATE_DATE VARCHAR(20)  NOT NULL,
   FREE_CMT_MODIFY_DATE VARCHAR(20)  NULL,
   FREE_CMT_IP         VARCHAR(30)  NOT NULL,
   FREE_CMT_STATE      SMALLINT     NOT NULL,
   FREE_CMT_DEPTH      SMALLINT     NOT NULL,
   FREE_GROUP_NO INT UNSIGNED NOT NULL,
   FREE_GROUP_ORDER      INT          NOT NULL,
   CONSTRAINT PK_FREE_CMT PRIMARY KEY(FREE_CMT_NO),
   CONSTRAINT FK_FREECMT_FREEBRD FOREIGN KEY(FREE_NO) REFERENCES FREE_BRD (FREE_NO) ON DELETE CASCADE,
   CONSTRAINT FK_FREECMT_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);
CREATE TABLE USED_BRD (
   USED_NO          INT           NOT NULL AUTO_INCREMENT,
   NICKNAME          VARCHAR(50)  NOT NULL,
   USED_TITLE       VARCHAR(40)  NOT NULL,
   USED_CONTENT       VARCHAR(500) NULL,
   USED_CREATE_DATE  VARCHAR(20)  NOT NULL,
   USED_MODIFIY_DATE VARCHAR(20)  NOT NULL,
   USED_IP          VARCHAR(30)  NOT NULL,
   USED_HIT          INT          NOT NULL,
   CONSTRAINT PK_USED_BRD PRIMARY KEY(USED_NO),
   CONSTRAINT FK_USEDBRD_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);
CREATE TABLE USED_CMT (
   USED_CMT_NO         INT          NOT NULL AUTO_INCREMENT,
   USED_NO            INT          NOT NULL,
   NICKNAME            VARCHAR(50)  NOT NULL,
   USED_CMT_CONTENT     VARCHAR(500) NOT NULL,
   USED_CMT_CREATE_DATE VARCHAR(20)  NOT NULL,
   USED_CMT_MODIFY_DATE VARCHAR(20)  NULL,
   USED_CMT_IP          VARCHAR(30)  NOT NULL,
   USED_CMT_STATE      SMALLINT     NOT NULL,
   USED_CMT_DEPTH      SMALLINT     NOT NULL,
   USED_GROUP_NO         INT          NOT NULL,
   USED_GROUP_ORDER      INT          NOT NULL,
   CONSTRAINT PK_USED_CMT PRIMARY KEY(USED_CMT_NO),
   CONSTRAINT FK_USEDCMT_USEDBRD FOREIGN KEY(USED_NO) REFERENCES USED_BRD (USED_NO) ON DELETE CASCADE,
   CONSTRAINT FK_USEDCMT_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);
CREATE TABLE USED_CART (
   USED_NO  INT         NOT NULL,
   NICKNAME VARCHAR(50) NOT NULL,
   CONSTRAINT FK_USEDCART_USEDBRD FOREIGN KEY(USED_NO) REFERENCES USED_BRD (USED_NO) ON DELETE CASCADE,
   CONSTRAINT FK_USEDCART_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE
);

CREATE TABLE QNA (
   QNA_NO   INT NOT NULL AUTO_INCREMENT,
   ID   VARCHAR(50)      NOT NULL,
   QNA_TITLE   VARCHAR(40)      NOT NULL,
   QNA_CONTENT   VARCHAR(3000)      NULL,
   QNA_CREATE_DATE   VARCHAR(20)      NULL,
   QNA_MODIFY_DATE   VARCHAR(20)      NULL,
   QNA_STATE   SMALLINT      NOT NULL,
   QNA_IP   VARCHAR(30)      NOT NULL,
   QNA_GROUP_NO   INT      NOT NULL,
   DEPTH   SMALLINT      NOT NULL,
   CONSTRAINT PK_QNA PRIMARY KEY(QNA_NO),
   CONSTRAINT FK_QNA_USERS FOREIGN KEY(ID) REFERENCES USERS (ID)
);

CREATE TABLE NOTICE (
   NOTICE_NO   INT NOT NULL AUTO_INCREMENT,
   NOTICE_TITLE   VARCHAR(40)      NOT NULL,
   NOTICE_CONTENT   VARCHAR(3000)      NOT NULL,
   NOTICE_HIT   INT      NOT NULL,
   NOTICE_CREATE_DATE   VARCHAR(20)      NULL,
   NOTICE_MODIFY_DATE   VARCHAR(20)      NULL,
   ID   VARCHAR(50)      NOT NULL,
   CONSTRAINT PK_NOTICE PRIMARY KEY(NOTICE_NO),
   CONSTRAINT FK_NOTICE_USERS FOREIGN KEY(ID) REFERENCES USERS (ID)
);

CREATE TABLE SUMMERNOTE_IMAGE (
    NOTICE_NO INT NOT NULL,
    FILESYSTEM VARCHAR(40),
    CONSTRAINT FK_SUMMERNOTE_NOTICE FOREIGN KEY(NOTICE_NO) REFERENCES NOTICE(NOTICE_NO) ON DELETE CASCADE
);

CREATE TABLE FAQ (
   FAQ_NO   INT NOT NULL AUTO_INCREMENT,
   FAQ_TITLE   VARCHAR(50)      NOT NULL,
   FAQ_CONTENT   VARCHAR(3000)      NOT NULL,
   FAQ_CREATE_DATE   VARCHAR(20)      NULL,
   CONSTRAINT PK_FAQ PRIMARY KEY(FAQ_NO)
);

CREATE TABLE REVIEW_BRD (
   REVIEW_NO            INT         NOT NULL AUTO_INCREMENT,
   CAMP_NO            INT         NOT NULL,
   NICKNAME            VARCHAR(50)  NOT NULL,
   REVIEW_TITLE         VARCHAR(40)  NOT NULL,
   REVIEW_CONTENT      VARCHAR(500) NULL,
   REVIEW_CREATE_DATE  VARCHAR(20)  NOT NULL,
   REVIEW_MODIFIY_DATE VARCHAR(20)  NOT NULL,
   REVIEW_IP            VARCHAR(30)  NOT NULL,
   REVIEW_HIT         INT         NOT NULL,
   REVIEW_STAR         INT         NOT NULL,
   CONSTRAINT PK_REVIEW_BRD PRIMARY KEY(REVIEW_NO),
   CONSTRAINT FK_REVIEWBRD_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME) ON DELETE CASCADE,
   CONSTRAINT FK_REVIEWBRD_CAMP FOREIGN KEY(CAMP_NO) REFERENCES CAMP (CAMP_NO) ON DELETE CASCADE
);

CREATE TABLE REVIEW_CMT (
   REVIEW_CMT_NO           INT          NOT NULL AUTO_INCREMENT,
   REVIEW_NO              INT            NOT NULL,
   NICKNAME              VARCHAR(50)  NOT NULL,
   REVIEW_CMT_CONTENT     VARCHAR(500) NOT NULL,
   REVIEW_CMT_CREATE_DATE VARCHAR(20)  NOT NULL,
   REVIEW_CMT_MODIFY_DATE VARCHAR(20)  NULL,
   REVIEW_CMT_IP           VARCHAR(30)  NOT NULL,
   REVIEW_CMT_STATE        SMALLINT     NOT NULL,
   REVIEW_CMT_DEPTH       SMALLINT     NOT NULL,
   REVIEW_GROUP_NO        INT            NOT NULL,
   REVIEW_GROUP_ORDER     INT          NOT NULL,
   CONSTRAINT PK_REVIEW_CMT PRIMARY KEY(REVIEW_CMT_NO),
   CONSTRAINT FK_REVIEWCMT_REVIEWBRD FOREIGN KEY(REVIEW_NO) REFERENCES REVIEW_BRD (REVIEW_NO) ON DELETE CASCADE,
   CONSTRAINT FK_REVIEWCMT_USERS FOREIGN KEY(NICKNAME) REFERENCES USERS (NICKNAME)
);

CREATE TABLE CAMP_CART (
   CAMP_NO  INT      NOT NULL,
   ID   VARCHAR(50)      NOT NULL,
   CONSTRAINT FK_CAMPCART_USERS FOREIGN KEY(ID) REFERENCES USERS (ID) ON DELETE CASCADE,
   CONSTRAINT FK_CAMPCART_CAMP FOREIGN KEY(CAMP_NO) REFERENCES CAMP (CAMP_NO) ON DELETE CASCADE
);

CREATE TABLE CAMP_TENT (
   TENT_NO  INT      NOT NULL AUTO_INCREMENT,
   CAMP_NO  INT      NOT NULL,
   TENT_CATEGORY INT,
   TENT_NAME   VARCHAR(64)      NOT NULL,
   TENT_MAX_COUNT  INT      NOT NULL,
   TENT_RESERVE_DAY   VARCHAR(64),
   TENT_SUM  INT      NOT NULL,
   TENT_STATE  INT,
   TENT_IMAGE   VARCHAR(100) NULL DEFAULT '/images/tent/default_tent.png',
   TENT_ORIGIN   VARCHAR(500),
   CONSTRAINT PK_CAMP_TENT PRIMARY KEY(TENT_NO),
   CONSTRAINT FK_CAMPTENT_CAMP FOREIGN KEY(CAMP_NO) REFERENCES CAMP (CAMP_NO) ON DELETE CASCADE
);

CREATE TABLE RESERVE (
   RESERVE_NO  INT      NOT NULL AUTO_INCREMENT,
   CAMP_NO  INT      NOT NULL,
   TENT_NO  INT      NOT NULL,
   ID   VARCHAR(50)      NOT NULL,
   RESERVE_NAME   VARCHAR(50)      NOT NULL,
   RESERVE_COUNT  INT      NOT NULL,
   RESERVE_BEGIN_DATE   VARCHAR(64)      NOT NULL,
   RESERVE_END_DATE   VARCHAR(64)      NOT NULL,
   RESERVE_SUM  INT      NOT NULL,
   RESERVE_OPTION  INT      NOT NULL,
   RESERVE_STATE  INT      NOT NULL,
   RESERVE_CANCEL   VARCHAR(64)      NULL,
   CONSTRAINT PK_RESERVE PRIMARY KEY(RESERVE_NO),
   CONSTRAINT FK_RESERVE_USERS FOREIGN KEY(ID) REFERENCES USERS (ID) ON DELETE CASCADE,
   CONSTRAINT FK_RESERVE_CAMP FOREIGN KEY(CAMP_NO) REFERENCES CAMP (CAMP_NO) ON DELETE CASCADE,
   CONSTRAINT FK_RESERVE_CAMPTENT FOREIGN KEY(TENT_NO) REFERENCES CAMP_TENT (TENT_NO) ON DELETE CASCADE
);

CREATE TABLE PAY (
   PAY_NO  INT      NOT NULL AUTO_INCREMENT,
   RESERVE_NO  INT      NOT NULL,
   PAY_NAME   VARCHAR(50)      NOT NULL,
   PAY_DATE   VARCHAR(64)      NOT NULL,
   CONSTRAINT PK_PAY PRIMARY KEY(PAY_NO,RESERVE_NO),
   CONSTRAINT FK_PAY_RESERVE FOREIGN KEY(RESERVE_NO) REFERENCES RESERVE (RESERVE_NO) ON DELETE CASCADE
);


ALTER TABLE USERS AUTO_INCREMENT=0;
ALTER TABLE CAMP_TENT AUTO_INCREMENT=0;


DROP TEMPORARY TABLE IF EXISTS GET_ID;
CREATE TEMPORARY TABLE GET_ID(
   TMP_ID   VARCHAR(50)      NOT NULL UNIQUE
);

-- 관리자
INSERT INTO USERS
   (ID, NICKNAME, PW, NAME, MOBILE, GENDER, BIRTH_YEAR, BIRTH_DAY, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, EXTRA_ADDRESS, JOIN_DATE, AGREE_CODE, SNS_TYPE, SESSION_ID, SESSION_LIMIT_DATE, EMAIL, AGE, PW_MODIFY_DATE, INFO_MODIFY_DATE)
VALUES
   ('admin', '관리자', '6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', '관리자', '01012345678', 'NO', 2000, 0101, NULL, NULL, NULL, NULL, NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), 0, NULL, NULL, NULL, 'admin@web.com', NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), DATE_FORMAT(NOW(), '%y/%m/%d'));

-- 휴면
INSERT INTO USERS
   (ID, NICKNAME, PW, NAME, MOBILE, GENDER, BIRTH_YEAR, BIRTH_DAY, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, EXTRA_ADDRESS, JOIN_DATE, AGREE_CODE, SNS_TYPE, SESSION_ID, SESSION_LIMIT_DATE, EMAIL, AGE, PW_MODIFY_DATE, INFO_MODIFY_DATE)
VALUES
   ('user99', '휴면', '6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', '휴면유저', '01099999999', 'NO', 1999, 0909, NULL, NULL, NULL, NULL, NULL, DATE_FORMAT('2021-12-01', '%y/%m/%d'), 0, NULL, NULL, NULL, 'sleep@web.com', NULL, DATE_FORMAT('2021-12-01', '%y/%m/%d'), DATE_FORMAT('2021-12-01', '%y/%m/%d'));

INSERT INTO USERS_ACCESS
   (ID, LAST_LOGIN_DATE)
VALUES
   ('user99', DATE_FORMAT('2021-12-02', '%y/%m/%d'));
   
-- 일반
INSERT INTO USERS
   (ID, NICKNAME, PW, NAME, MOBILE, GENDER, BIRTH_YEAR, BIRTH_DAY, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, EXTRA_ADDRESS, JOIN_DATE, AGREE_CODE, SNS_TYPE, SESSION_ID, SESSION_LIMIT_DATE, EMAIL, AGE, PW_MODIFY_DATE, INFO_MODIFY_DATE)
VALUES
   ('user01', '이름(5조)', '6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', '닉네임(5조)', '01011111111', 'NO', 1934, 0806, NULL, NULL, NULL, NULL, NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), 0, NULL, NULL, NULL, 'hong@web.com', NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), DATE_FORMAT(NOW(), '%y/%m/%d'));
   -- ('user02', '테스트용', '6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B', '닉네임', '01011111111', 'NO', 1934, 0806, NULL, NULL, NULL, NULL, NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), 0, NULL, NULL, NULL, 'asdf@web.com', NULL, DATE_FORMAT(NOW(), '%y/%m/%d'), DATE_FORMAT(NOW(), '%y/%m/%d'));
-- 탈퇴
INSERT INTO USERS_RETIRE
   (ID, NICKNAME, RETIRE_DATE, JOIN_DATE)
VALUES
   ('user00', '탈퇴유저', DATE_FORMAT('2022-12-02', '%y/%m/%d'), DATE_FORMAT('2021-12-02', '%y/%m/%d'));

-- 텐트 초기데이터
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (1, 0, '텐트1', 2, NULL, 100, 0, '/images/tent/glamping.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (1, 1, '텐트2', 5, NULL, 100, 0, '/images/tent/caraban.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (1, 2, '텐트3', 5, NULL, 100, 0, '/images/tent/general.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (1, 3, '텐트4', 4, NULL, 100, 0, '/images/tent/carcamping.webp');

-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (2, 0, '텐트5', 2, NULL, 100, 0, '/images/tent/glamping.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (2, 1, '텐트6', 5, NULL, 100, 0, '/images/tent/caraban.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (2, 2, '텐트7', 5, NULL, 100, 0, '/images/tent/general.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (2, 3, '텐트8', 4, NULL, 100, 0, '/images/tent/carcamping.webp');

-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (3, 0, '텐트9', 2, NULL, 100, 0, '/images/tent/glamping.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (3, 1, '텐트10', 5, NULL, 100, 0, '/images/tent/caraban.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (3, 2, '텐트11', 5, NULL, 100, 0, '/images/tent/general.webp');
-- INSERT INTO CAMP_TENT
--     (CAMP_NO, TENT_CATEGORY, TENT_NAME, TENT_MAX_COUNT, TENT_RESERVE_DAY, TENT_SUM, TENT_STATE, TENT_IMAGE)
-- VALUES
--     (3, 3, '텐트12', 4, NULL, 100, 0, '/images/tent/carcamping.webp');


COMMIT;


/*
SELECT U.ID
  FROM USERS U LEFT OUTER JOIN USERS_ACCESS A
    ON U.ID = A.ID
 WHERE DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), A.LAST_LOGIN_DATE) >= 12
    OR (DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), U.JOIN_DATE) >= 12 AND A.LAST_LOGIN_DATE IS NULL);

DELETE
  FROM USERS
 WHERE ID = (SELECT TMP_ID
               FROM (SELECT U.ID TMP_ID
                 FROM USERS U LEFT OUTER JOIN USERS_ACCESS A
                  ON U.ID = A.ID
                WHERE DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), A.LAST_LOGIN_DATE) >= 12
                  OR (DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), U.JOIN_DATE) >= 12 AND A.LAST_LOGIN_DATE IS NULL)) A);


INSERT INTO USERS_SLEEP(USERS_NO, ID, NICKNAME, PW, NAME, MOBILE, GENDER, BIRTH_YEAR, BIRTH_DAY, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, EXTRA_ADDRESS, JOIN_DATE, AGREE_CODE, SNS_TYPE, EMAIL, SLEEP_DATE, AGE, PROFILE_IMAGE, LAST_LOGIN_DATE, PW_MODIFY_DATE, INFO_MODIFY_DATE)
         (SELECT U.USERS_NO, U.ID, U.NICKNAME, U.PW, U.NAME, U.MOBILE, U.GENDER, U.BIRTH_YEAR, U.BIRTH_DAY, U.POSTCODE, U.ROAD_ADDRESS, U.JIBUN_ADDRESS, U.DETAIL_ADDRESS, U.EXTRA_ADDRESS, U.JOIN_DATE, U.AGREE_CODE, U.SNS_TYPE, U.EMAIL, DATE_FORMAT(NOW(), '%y/%m/%d'), U.AGE, U.PROFILE_IMAGE, A.LAST_LOGIN_DATE, U.PW_MODIFY_DATE, U.INFO_MODIFY_DATE
            FROM USERS U LEFT OUTER JOIN USERS_ACCESS A
           ON U.ID = A.ID
           WHERE DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), A.LAST_LOGIN_DATE) >= 12
              OR (DATEDIFF(DATE_FORMAT(NOW(), '%y/%m/%d'), U.JOIN_DATE) >= 12 AND A.LAST_LOGIN_DATE IS NULL));

SELECT ID, JOIN_DATE, LAST_LOGIN_DATE, SLEEP_DATE
        FROM USERS_SLEEP
       WHERE ID = 'user99';

SELECT USERS_NO, ID, NICKNAME, PW, NAME, MOBILE, GENDER, BIRTH_YEAR, BIRTH_DAY, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, EXTRA_ADDRESS, JOIN_DATE, AGREE_CODE, SNS_TYPE, SESSION_ID, SESSION_LIMIT_DATE, EMAIL, AGE, PROFILE_IMAGE, PW_MODIFY_DATE, INFO_MODIFY_DATE
  FROM USERS
 WHERE SESSION_ID = 'B782E0DACEC19D1ACF37C3BDD71A125E'
   AND 1 = (SESSION_LIMIT_DATE > DATE_FORMAT(NOW(), '%Y/%m/%d'));

SELECT SESSION_LIMIT_DATE
  FROM USERS
 WHERE ID = 'user01';
          
SELECT '2023-01-03' > DATE_FORMAT(NOW(), '%Y/%m/%d');
*/