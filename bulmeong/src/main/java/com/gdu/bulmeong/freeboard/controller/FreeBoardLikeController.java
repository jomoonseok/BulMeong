package com.gdu.bulmeong.freeboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.bulmeong.freeboard.service.FreeBoardCmtService;
import com.gdu.bulmeong.freeboard.service.FreeBoardService;

@Controller
public class FreeBoardLikeController {

	@Autowired
	private FreeBoardService freeBoardService;
	
	@Autowired
	private FreeBoardCmtService freeBoardCmtService;
	
	

	

	
}
