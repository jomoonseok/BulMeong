package com.gdu.bulmeong.users.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.bulmeong.users.service.UsersService;

@EnableScheduling
@Component
public class SleepUserScheduler {

	@Autowired
	private UsersService usersService;
	
	@Scheduled(cron = "0 0 14 * * *")  
	public void execute() {
		usersService.sleepUserHandle();
	}
	
}