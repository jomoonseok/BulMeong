package com.gdu.bulmeong.camp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BulMeongDTO {
	private int CampNo;  // 캠핑장 번호
	private int ReviewNo;  // 게시판 번호
	private String facltNm;  // 야영장명
	private String lineIntro;  // 한줄소개
	private String featureNm;  // 특징
	private String sbrsCl;  // 부대시설
	private String firstImageUrl;  // 대표이미지
	private String themaEnvrnCl;  // 테마환경
	private String eqpmnLendCl;  // 캠핑장비대여
	private String tourEraCl;  // 여행시기
	private String induty;  // 업종
	private int allar;  // 전체면적
	private String operPdCl;  // 운영기간
	private String hvofBgnde;  // 휴장기간.휴무기간 시작일
	private String hvofEnddle;  // 휴장기간.휴무기간 종료일
	private String prmisnDe;  // 인허가일자
	private String operDeCl;  // 운영일
	private int toiletCo;  // 화장실 개수
	private int extshrCo;  // 소화기 개수
	private int brazierCl;  // 화로대
	private String glampInnerFclty;  // 글램핑 - 내부시설
	private String caravInnerFclty;  // 카라반 - 내부시설
	private String lctCl;  // 입지구분
	private String addr1;  // 주소
	private String doNm;  // 도
	private String sigunguNm;  // 시군구
	private String addr2;  // 주소상세
	private String zipcode;  // 우편번호
	private String mapX;  // 경도(X)
	private String mapY;  // 위도(Y)
	private String homepage;  // 홈페이지
	private String tel;  // 전화
	private String createdtime;  // 등록일
	private String modifiedtime;  // 수정일

}
