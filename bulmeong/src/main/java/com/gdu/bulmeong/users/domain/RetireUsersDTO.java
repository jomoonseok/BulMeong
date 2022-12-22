package com.gdu.bulmeong.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RetireUsersDTO {
	private int retireNo;
	private String id;
	private String nickname;
	private String retireDate;
	private String joinDate;
}
