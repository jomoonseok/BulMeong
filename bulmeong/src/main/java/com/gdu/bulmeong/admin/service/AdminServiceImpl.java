package com.gdu.bulmeong.admin.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.admin.domain.AdminTentDTO;
import com.gdu.bulmeong.admin.mapper.AdminMapper;
import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.camp.mapper.CampMapper;
import com.gdu.bulmeong.util.MyFileUtil;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Value("{custom.path.uploadTentImages}")
	private String fileRealPath;
	
	@Autowired 
	private AdminMapper adminMapper;
	
	@Autowired
	private CampMapper campMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	public Map<String, Object> getAllUser() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("users", adminMapper.selectAllUser());
		
		return map;
	}
	
	public Map<String, Object> getAllCamp(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int campCount = campMapper.selectCampCount();
		pageUtil.setPageUtil(page, campCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("end", pageUtil.getRecordPerPage());
		
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
		map.put("end", pageUtil.getRecordPerPage());
		
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
	public void uploadTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		int CAMP_NO = Integer.parseInt(request.getParameter("campNo"));
		String TENT_NAME = request.getParameter("TENT_NAME");
		int TENT_CATEGORY = Integer.parseInt(request.getParameter("TENT_CATEGORY"));
		int TENT_MAX_COUNT = Integer.parseInt(request.getParameter("TENT_MAX_COUNT"));
		int TENT_SUM = Integer.parseInt(request.getParameter("TENT_SUM"));
		MultipartFile TENT_IMAGE = request.getFile("TENT_IMAGE");
		
		int imageResult = 0;
		if(TENT_IMAGE.getSize() == 0) {
			imageResult = 1;
		} else {
			imageResult = 0;
		}
		
		try {
			if(TENT_IMAGE != null && TENT_IMAGE.isEmpty() == false || TENT_IMAGE.getSize() >= 0) {
				
				String origin = TENT_IMAGE.getOriginalFilename();
				origin = origin.substring(origin.lastIndexOf("\\") + 1);
				
				String filesystem = myFileUtil.getFilename(origin);
				
				String sep = Matcher.quoteReplacement(File.separator);
				String path = "";
				
				if(TENT_IMAGE.getSize() == 0) {
					filesystem = "/images/tent/default_tent.png";
					
				} else {
					path = "C:" + sep + "bulmeongImage" + sep + "tent";
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
					out.println("alert('업로드 되었습니다.');");
					out.println("opener.parent.location.reload()");
					out.println("window.close()");
					out.println("</script>");
				}  else {
					out.println("<script>");
					out.println("alert('업로드 실패했습니다.');");
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
		
		int imageResult = 0;
		if(TENT_IMAGE.getSize() == 0) {
			imageResult = 1;
		} else {
			imageResult = 0;
		}
		
		try {
			if(TENT_IMAGE != null && TENT_IMAGE.isEmpty() == false || TENT_IMAGE.getSize() >= 0) {
			
				String origin = TENT_IMAGE.getOriginalFilename();
				origin = origin.substring(origin.lastIndexOf("\\") + 1);
				
				String filesystem = myFileUtil.getFilename(origin);
				
				String sep = Matcher.quoteReplacement(File.separator);
				String path = "";
				
				if(TENT_IMAGE.getSize() == 0) {
					filesystem = "/images/tent/default_tent.png";
					
				} else {
					path = "C:" + sep + "bulmeongImage" + sep + "tent";
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
					out.println("alert('수정 되었습니다.');");
					out.println("opener.parent.fn_getList()");
					out.println("window.close()");
					out.println("</script>");
				}  else {
					out.println("<script>");
					out.println("alert('수정 실패했습니다.');");
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
			alertMsg = "삭제 되었습니다.";
		} else {
			isDelete = false;
			alertMsg = "삭제 실패했습니다.";
		}
		
		map.put("isDelete", isDelete);
		map.put("alertMsg", alertMsg);
		return map;
	}
	
}
