package com.gdu.bulmeong.camp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CampDTO {
	private int campNo;  // 캠핑장 번호
	private String facltNm;  // 야영장명                         (주)디노담양힐링파크 지점                     
	private String intro; // 소개                                담양군 봉산면 기곡리에 위치한 힐링파크 담양점은 도심과 떨어져 조용히 숲과 자연에서 자유로이 쉴수 있는 힐링파크다. 이곳은 기존 봉산관광농원이라는 이름으로 1998년 농림부 1등급 판정을 받은 시설로 어느 휴양지에서도 느끼지 못했던 색다른 휴식과 즐거움을 동시에 느낄 수 있다. 담양 힐링파크는 대한민국에서 둘째가라면 서러울 정도로 많은 편의시설을 자랑하고 있는데 책을 좋아하는 사람이라면 누구든 이용 가능한 실내 독서실과 저수지낚시터가 있다. 또한 족구, 배드민턴 등 함께 뛰며 게임할 수 있는 족구장이 있으며 배드민턴은 무료 대여중이다. 이밖에도 주말 행복한 하루를 밤하늘을 보며 마감할 수 있는 주말 폭죽서비스가 있다. 이곳은 ‘동물의 왕국’이라고 해도 과언이 아닐 정도로 다양한 동물들과 시간을 보낼 수 있는데 미니동물원에서는 토끼에게 먹이도 주고 만져보며 교감할 수 있다. 그리고 담양곤충체험관이 있어 자연, 곤충과 친해질 수 있으며 이용료는 무료다. 반려동물이 출입이 가능하므로 자신의 반려동물과도 추억을 쌓을 수 있다.
	private String lineIntro;  // 한줄소개                       담양 힐링파크에서 일상 속 쌓인 스트레스를 풀어보자
	private String featureNm;  // 특징                           물놀이시설 잘 갖추어짐 실내 놀이방 실내 독서방 카라반 2동 글램핑 시설이 좋은편
	private String sbrsCl;  // 부대시설                          전기,무선인터넷,장작판매,온수,트렘폴린,물놀이장,놀이터,산책로,운동시설,마트.편의점
	private String firstImageUrl;  // 대표이미지                 https://gocamping.or.kr/upload/camp/4/thumb/thumb_720_4548WQ5JCsRSkbHrBAaZylrQ.jpg
	private String themaEnvrnCl;  // 테마환경                    낚시,여름물놀이
	private String eqpmnLendCl;  // 캠핑장비대여                 null
	private String tourEraCl;  // 여행시기                       null
	private String induty;  // 업종                              일반야영장,카라반,글램핑
	private String allar;  // 전체면적                           2500
	private String operPdCl;  // 운영기간                        봄,여름,가을,겨울
	private String hvofBgnde;  // 휴장기간.휴무기간 시작일       null
	private String hvofEnddle;  // 휴장기간.휴무기간 종료일      null
	private String prmisnDe;  // 인허가일자                      2016-10-27
	private String operDeCl;  // 운영일                          평일+주말
	private String toiletCo;  // 화장실 개수                     4
	private String extshrCo;  // 소화기 개수                     20
	private String brazierCl;  // 화로대                         개별
	private String glampInnerFclty;  // 글램핑 - 내부시설        침대,에어컨,냉장고,유무선인터넷,난방기구,취사도구,내부화장실
	private String caravInnerFclty;  // 카라반 - 내부시설        침대,TV,에어컨,냉장고,유무선인터넷,난방기구,취사도구,내부화장실
	private String lctCl;  // 입지구분                           호수
	private String addr1;  // 주소                               전남 담양군 봉산면 탄금길 9-26
	private String doNm;  // 도                                  전라남도
	private String sigunguNm;  // 시군구                         담양군
	private String addr2;  // 주소상세                           null
	private String zipcode;  // 우편번호                         57371
	private String mapX;  // 경도(X)                             126.9609528
	private String mapY;  // 위도(Y)                             35.2714369
	private String homepage;  // 홈페이지                        https://healingpark.modoo.at/
	private String tel;  // 전화                                 061-383-5155
	private String createdtime;  // 등록일                       2022-08-01 23:17:22
	private String modifiedtime;  // 수정일                      2022-08-01 23:17:22
	private int jjim;
	private int sum;

}
