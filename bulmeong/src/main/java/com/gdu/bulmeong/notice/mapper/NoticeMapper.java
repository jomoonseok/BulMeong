package com.gdu.bulmeong.notice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.notice.domain.NoticeDTO;
import com.gdu.bulmeong.notice.domain.SummernoteImageDTO;

@Mapper
public interface NoticeMapper {
	public int selectNoticeListCount();
	public List<NoticeDTO> selectNoticeListByMap(Map<String, Object> map);
	public int insertSummernoteImage(SummernoteImageDTO summernote);
	public int insertNotice(NoticeDTO notice);
	public int updateNoticeHit(int noticeNo);
	public NoticeDTO selectNoticeByNo(int noticeNo);
	public int updateNotice(NoticeDTO notice);
	public int deleteNotice(int noticeNo);
	public List<SummernoteImageDTO> selectSummernoteImageListInNotice(int noticeNo);
	public List<SummernoteImageDTO> selectAllSummernoteImageList();
	public int deleteSummernoteImage(String filesystem);
}
