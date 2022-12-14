package com.gdu.bulmeong.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersDTO {
	private int usersNo;
	private String id;
	private String nickname;
	private String pw;
	private String name;
	private String mobile;
	private String gender;
	private String birthYear;
	private String birthDay;
	private String postCode;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String extraAddress;
	private String joinDate;
	private int agreeCode;
	private String snsType;
	private String sessionId;
	private String sessionLimitDate;
	private String email;
	private int age;
	private String profileImage;
	private String pwModifyDate; 
	private String infoModifyDate;
}
