package com.gdu.bulmeong.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileImageDTO {
	private int imageNo;
	private String path;
	private String origin;
	private String filesystem;
	private String id;
}
