package com.gdu.bulmeong.admin.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.admin.domain.AdminTentDTO;
import com.gdu.bulmeong.admin.mapper.AdminMapper;
import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.camp.mapper.CampMapper;
import com.gdu.bulmeong.users.domain.MyPageReservedDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.util.MyFileUtil;
import com.gdu.bulmeong.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
	
	private AdminMapper adminMapper;
	private CampMapper campMapper;
	private PageUtil pageUtil;
	private MyFileUtil myFileUtil;
	
	@Override
	public void adminCheck(HttpServletRequest request, Model model) {
		UsersDTO loginUser = (UsersDTO)request.getSession().getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
	}
	
	public Map<String, Object> getAllUser(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int userCount = adminMapper.selectAllUserCount();
		pageUtil.setPageUtil(page, userCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		List<UsersDTO> users = adminMapper.selectAllUser(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("users", users);
		result.put("pageUtil", pageUtil);
		result.put("userCount", userCount);
		
		return result;
	}
	
	@Override
	public Map<String, Object> removeUser(List<Integer> usersNo) {
		int userNoArr[] = new int[usersNo.size()];
		int result = 0;
		Map<String, Object> map = new HashMap<>();
		for(int i = 0; i < usersNo.size(); i++) {
			userNoArr[i] = usersNo.get(i);
			result += adminMapper.deleteUser(userNoArr[i]);
		}
		
		if(result == usersNo.size()) {
			map.put("isDelete", true);
			map.put("resultCount", result);
		} else {
			map.put("isDelete", false);
		}
		
		return map;
	}
	
	public Map<String, Object> getAllCamp(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int campCount = campMapper.selectCampCount();
		pageUtil.setPageUtil(page, campCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		List<CampDTO> camp = adminMapper.selectAllCampAdmin(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("campList", camp);
		result.put("pageUtil", pageUtil);
		result.put("campCount", campCount);
		
		return result;
	}
	
	@Override
	public Map<String, Object> getAllTent(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int tentCount = adminMapper.selectAllTentCount();
		pageUtil.setPageUtil(page, tentCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		List<AdminTentDTO> tent = adminMapper.selectAllTentAdmin(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", tent);
		result.put("pageUtil", pageUtil);
		result.put("tentCount", tentCount);
		return result;
	}
	
	@Override
	public Map<String, Object> getCampcampNofacltNm() {
		List<Map<String, Object>> camp = adminMapper.selectCampcampNofacltNm();
		Map<String, Object> result = new HashMap<>();
		result.put("camp", camp);
		return result;
	}
	
	@Override
	public Map<String, Object> getAllReserve(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int reserveCount = adminMapper.selectAllReserveCount();
		
		pageUtil.setPageUtil(page, reserveCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		List<MyPageReservedDTO> reserve = adminMapper.selectReserveByMap(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("reserveList", reserve);
		result.put("pageUtil", pageUtil);
		result.put("reserveCount", reserveCount);
		
		return result;
	}
	
	@Override
	public void uploadTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		int CAMP_NO = Integer.parseInt(request.getParameter("campNo"));
		String TENT_NAME = request.getParameter("TENT_NAME");
		int TENT_CATEGORY = Integer.parseInt(request.getParameter("TENT_CATEGORY"));
		int TENT_MAX_COUNT = Integer.parseInt(request.getParameter("TENT_MAX_COUNT"));
		int TENT_SUM = Integer.parseInt(request.getParameter("TENT_SUM"));
		MultipartFile TENT_IMAGE = request.getFile("TENT_IMAGE");
		
		try {
			if(TENT_IMAGE != null && TENT_IMAGE.isEmpty() == false || TENT_IMAGE.getSize() >= 0) {
				
				String origin = TENT_IMAGE.getOriginalFilename();
				origin = origin.substring(origin.lastIndexOf("\\") + 1);
				
				String filesystem = myFileUtil.getFilename(origin);
				
				String path = "";
				
				if(TENT_IMAGE.getSize() == 0) {
					filesystem = "/images/tent/default_tent.png";
					
				} else {
					path = myFileUtil.getTentPath();
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					TENT_IMAGE.transferTo(file);
				}
				
				AdminTentDTO tent = AdminTentDTO.builder()
						.campNo(CAMP_NO).tentName(TENT_NAME).tentCategory(TENT_CATEGORY).tentMaxCount(TENT_MAX_COUNT).tentSum(TENT_SUM).tentImage(filesystem).tentOrigin(origin).build();
				
				int result = adminMapper.insertTent(tent);
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				if(result > 0) {
					out.println("<script>");
					out.println("alert('????????? ???????????????.');");
					out.println("opener.parent.location.reload()");
					out.println("window.close()");
					out.println("</script>");
				}  else {
					out.println("<script>");
					out.println("alert('????????? ??????????????????.');");
					out.println("opener.parent.location.reload()");
					out.println("window.close();");
					out.println("</script>");
				}
				out.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void modifyTent(HttpServletRequest request, Model model) {
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		String facltNm = request.getParameter("facltNm");
		AdminTentDTO tent = adminMapper.selectTentByTentNo(tentNo);
		tent.setFacltNm(facltNm);
		
		model.addAttribute("tent", tent);
	}
	
	@Override
	public void changeTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		int TENT_NO = Integer.parseInt(request.getParameter("tentNo"));
		String TENT_NAME = request.getParameter("TENT_NAME");
		int TENT_CATEGORY = Integer.parseInt(request.getParameter("TENT_CATEGORY"));
		int TENT_MAX_COUNT = Integer.parseInt(request.getParameter("TENT_MAX_COUNT"));
		int TENT_SUM = Integer.parseInt(request.getParameter("TENT_SUM"));
		MultipartFile TENT_IMAGE = request.getFile("TENT_IMAGE");
		String TENT_IMAGE_ORIGIN = request.getParameter("TENT_IMAGE_ORIGIN");
		
		try {
			if(TENT_IMAGE != null && TENT_IMAGE.isEmpty() == false || TENT_IMAGE.getSize() >= 0) {
			
				String origin = TENT_IMAGE.getOriginalFilename();
				origin = origin.substring(origin.lastIndexOf("\\") + 1);
				
				String filesystem = myFileUtil.getFilename(origin);

				String path = "";
				if(TENT_IMAGE.getSize() == 0) {
					
					if(TENT_IMAGE.getName() != null) {
						filesystem = TENT_IMAGE_ORIGIN;
					} else {
						filesystem = "/images/tent/default_tent.png";
					}
					
				} else {
					path = myFileUtil.getTentPath();
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					TENT_IMAGE.transferTo(file);
				}
				
				AdminTentDTO tent = AdminTentDTO.builder()
						.tentNo(TENT_NO).tentName(TENT_NAME).tentCategory(TENT_CATEGORY).tentMaxCount(TENT_MAX_COUNT).tentSum(TENT_SUM).tentImage(filesystem).tentOrigin(origin).build();
				
				
				int result = adminMapper.updateTentByTentNo(tent);
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				if(result > 0) {
					out.println("<script>");
					out.println("alert('?????? ???????????????.');");
					out.println("opener.parent.fn_getList()");
					out.println("window.close()");
					out.println("</script>");
				}  else {
					out.println("<script>");
					out.println("alert('?????? ??????????????????.');");
					out.println("opener.parent.fn_getList()");
					out.println("window.close();");
					out.println("</script>");
				}
				out.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, Object> removeTent(HttpServletRequest request) {
		
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		int result = adminMapper.deleteTentByTentNo(tentNo);
		boolean isDelete;
		String alertMsg = "";
		
		Map<String, Object> map = new HashMap<>();
		if(result > 0) {
			isDelete = true;
			alertMsg = "?????? ???????????????.";
		} else {
			isDelete = false;
			alertMsg = "?????? ??????????????????.";
		}
		
		map.put("isDelete", isDelete);
		map.put("alertMsg", alertMsg);
		return map;
	}
	
	@Override
	public Map<String, Object> changeImage(MultipartHttpServletRequest request) {
		
		MultipartFile tentImage = request.getFile("TENT_IMAGE");
		Map<String, Object> map = new HashMap<>();
		map.put("tentImage", tentImage);
	
		return map;
	}
	
}
