package com.gdu.bulmeong.notice.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.notice.domain.NoticeDTO;
import com.gdu.bulmeong.notice.domain.SummernoteImageDTO;
import com.gdu.bulmeong.notice.mapper.NoticeMapper;
import com.gdu.bulmeong.util.MyFileUtil;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired 
	private NoticeMapper noticeMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	// 목록
	@Override
	public void getNoticeList(HttpServletRequest request, Model model) {
		
		// 페이지 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int recordPerPage = 10;
				
		// 전체 공지 개수
		int totalRecord = noticeMapper.selectNoticeListCount();
		
		// 페이징 처리
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);

		// 조회조건(Map)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		// 뷰로 전달할 데이터
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("noticeList", noticeMapper.selectNoticeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/notice/list"));
		
	}
	
	// 이미지 저장
	@Override
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest) {
		
		// 파라미터 files
		MultipartFile multipartFile = multipartRequest.getFile("file");
			
		// 저장 경로
		String path = "C:" + File.separator + "noticeImage";
				
		// 저장할 파일명
		String filesystem = myFileUtil.getFilename(multipartFile.getOriginalFilename());
		
		// 저장 경로가 없으면 만들기
		File dir = new File(path);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		// 저장할 File 객체
		File file = new File(path, filesystem);  // new File(dir, filesystem)도 가능
		
		// HDD에 File 객체 저장하기
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 저장된 파일을 확인할 수 있는 매핑을 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", multipartRequest.getContextPath() + "/load/image/" + filesystem);  // 이미지 mapping값을 반환
		map.put("filesystem", filesystem);  // HDD에 저장된 파일명 반환
		return map;
		
	}
	
	// 저장
	@Transactional
	@Override
	public void saveNotice(HttpServletRequest request, HttpServletResponse response) {
		
		// 세션
		//HttpSession session = request.getSession();
		//UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		// 파라미터
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		// String id = request.getParameter(loginUser.getId());
		String id = "ADMIN";

		
		NoticeDTO notice = NoticeDTO.builder()
				.noticeTitle(noticeTitle)
				.noticeContent(noticeContent)
				.id(id)
				.build();
		
		int result = noticeMapper.insertNotice(notice);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
//				String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
//
//				if(summernoteImageNames !=  null) {
//					for(String filesystem : summernoteImageNames) {
//						SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
//								.noticeNo(notice.getNoticeNo())
//								.filesystem(filesystem)
//								.build();
//						noticeMapper.insertSummernoteImage(summernoteImage);
//					}
//					System.out.println(notice.getNoticeNo());
//				}
				out.println("<script>");
				out.println("alert('공지사항이 등록되었습니다.')");
				out.println("location.href='/notice/list';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('공지사항 등록에 실패했습니다.')");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	// 조회수증가
	@Override
	public int increaseNoticeHit(int noticeNo) {
		return noticeMapper.updateNoticeHit(noticeNo);
	}
	
	// 상세보기
	@Override
	public NoticeDTO getNoticeByNo(int noticeNo) {
		
		NoticeDTO notice = noticeMapper.selectNoticeByNo(noticeNo);
		
		List<SummernoteImageDTO> summernoteImageList = noticeMapper.selectSummernoteImageListInNotice(noticeNo);
		
		if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
			for(SummernoteImageDTO summernoteImage : summernoteImageList) {
				if(notice.getNoticeContent().contains(summernoteImage.getFilesystem()) == false) {
					File file = new File("C:" + File.separator + "noticeImage", summernoteImage.getFilesystem());
					if(file.exists()) {
						file.delete();
					}
					noticeMapper.deleteSummernoteImage(summernoteImage.getFilesystem());
				}
			}
		}
		return notice;
	}
	
	// 수정
	@Transactional
	@Override
	public void modifyNotice(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		// UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		//String id = request.getParameter(loginUser.getId());
		String id = "ADMIN";
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		
		NoticeDTO notice = NoticeDTO.builder()
				.noticeTitle(noticeTitle)
				.noticeContent(noticeContent)
				.id(id)
				.noticeNo(noticeNo)
				.build();
		
		int result = noticeMapper.updateNotice(notice);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				// 파라미터 summernoteImageNames
				String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
				
				// DB에 SummernoteImage 저장
				if(summernoteImageNames != null) {
					for(String filesystem : summernoteImageNames) {
						SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
								.noticeNo(notice.getNoticeNo())
								.filesystem(filesystem)
								.build();
						noticeMapper.insertSummernoteImage(summernoteImage);
					}
				}
				
				out.println("<script>");
				out.println("alert('" + noticeNo + "번 공지사항이 수정되었습니다.');");
				out.println("location.href='/notice/detail?noticeNo=" + noticeNo + "';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('공지사항 수정이 불가능합니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 삭제
	@Override
	public void removeNotice(HttpServletRequest request, HttpServletResponse response) {
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		List<SummernoteImageDTO> summernoteImageList = noticeMapper.selectSummernoteImageListInNotice(noticeNo);
		int result = noticeMapper.deleteNotice(noticeNo);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				
				if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
					for(SummernoteImageDTO summernoteImage : summernoteImageList) {
						File file = new File("C:" + File.separator + "noticeImage", summernoteImage.getFilesystem());
						if(file.exists()) {
							file.delete();
						}
					}
				}
				
				out.println("alert('" + noticeNo + "번 공지사항이 삭제되었습니다.');");
				out.println("location.href='/notice/list';");
			} else {
				out.println("alert('공지사항 삭제가 불가능합니다.');");
				out.println("history.back();");
			}
			out.println("</script>");			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
