package com.gdu.bulmeong.users.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.bulmeong.users.service.UsersService;

@EnableScheduling
@Component
public class SleepUserMailScheduler {

	@Autowired
	private UsersService usersService;
	
	// 매일 새벽 1시	@Scheduled(cron="0 0 1 * * *")
	@Scheduled(cron = "0 0 1 * * *")  
	public void execute() {
		usersService.sleepUserMail();
	}
	
}