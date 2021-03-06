package xyz.sunnytoday.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xyz.sunnytoday.common.JDBCTemplate;
import xyz.sunnytoday.dao.face.MemberMenageDao;
import xyz.sunnytoday.dao.face.QuestionMenageDao;
import xyz.sunnytoday.dao.face.ReportHandlingDao;
import xyz.sunnytoday.dao.impl.MemberMenageDaoImpl;
import xyz.sunnytoday.dao.impl.QuestionMenageDaoImpl;
import xyz.sunnytoday.dao.impl.ReportHandingDaoImpl;
import xyz.sunnytoday.dto.Ban;
import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.dto.Question;
import xyz.sunnytoday.dto.Report;
import xyz.sunnytoday.service.face.MemberMenageService;
import xyz.sunnytoday.util.Paging;

public class MemberMenageServiceImpl implements MemberMenageService {
	MemberMenageDao memberDao = new MemberMenageDaoImpl();
	QuestionMenageDao questionDao = new QuestionMenageDaoImpl();
	ReportHandlingDao reportDao = new ReportHandingDaoImpl();
	@Override
	public int cntList(HttpServletRequest req, Member param, String location) {
		int totalCount = 0;
		Connection conn = JDBCTemplate.getConnection();
		totalCount = memberDao.searchCnt(conn, param, location);
		JDBCTemplate.close(conn);
		return totalCount;
	}
	@Override
	public Paging getPaging(HttpServletRequest req, Member param, String location) {
		System.out.println("getMemberPaging called");
		String page = req.getParameter("curPage");
		System.out.println("curPage : " + page );
		System.out.println("location : " + location);
		int curPage = 0;
		if(page != null && !"".equals(page)) {
			curPage = Integer.parseInt(page);
		}else {
			System.out.println("[WARNING] curPage값이 null이거나 비어있음");
		}
		int totalCount = cntList(req, param, location); 
		
		Connection conn = JDBCTemplate.getConnection();
				
		//Paging 객체 생성
		Paging paging = new Paging(totalCount, curPage);
		
		JDBCTemplate.close(conn);
		
		return paging;
	}
	
	@Override
	public List<Member> getUserList(Member param, Paging paging) {
		System.out.println("getSearchUserList called");
		Connection conn = JDBCTemplate.getConnection();
		
		List<Member> list = memberDao.getMemberList(conn, paging, param);
		
		JDBCTemplate.close(conn);
		return list;
	}
	
	@Override
	public Member getMemberDetailList(HttpServletRequest req) {
		System.out.println("getMemberDetailList called");
		Connection conn = JDBCTemplate.getConnection();
		Member param = new Member();
		param.setUserno(Integer.parseInt(req.getParameter("userno")));
		param = memberDao.selectUserDatail(param, conn);
		
		JDBCTemplate.close(conn);
		return param;
	}

	@Override
	public List<Map<String, Object>> getQuestionList(Member param, Paging paging) {
		System.out.println("getQuestionList");
		Connection conn = JDBCTemplate.getConnection();
		List<Map<String, Object>> list = null;
		
		list = questionDao.searchQuestion(param, paging, conn);
		
		
		JDBCTemplate.close(conn);
		return list;
	}

	@Override
	public Question getQuestionDetail(HttpServletRequest req, Question param) {
		System.out.println("getQuestionDetail called");
	
		if(req.getParameter("question_no") != null && !"".equals(req.getParameter("question_no"))) {
			param.setQuestion_no(Integer.parseInt(req.getParameter("question_no")));
			param.setAdmin_no(Integer.parseInt(req.getParameter("admin_no")));
			Connection conn = JDBCTemplate.getConnection();
			param = questionDao.getQuestionDatil(conn, param);
			
			JDBCTemplate.close(conn);
			return param;
		}else {
			System.out.println("question_no가 null이거나 값이 존재 하지 않습니다.");
			return null;
		}

	}

	@Override
	public void updateAnswer(Question param) {
		System.out.println("updateAnswer called");
		Connection conn = JDBCTemplate.getConnection();
		int res = 0;
		res = questionDao.setUpdateAnswer(conn, param);
		if(res != 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
	}

	@Override
	public void deleteQuestion(Question param) {
		System.out.println("deleteQuestion called");
		Connection conn = JDBCTemplate.getConnection();
		int res = 0;
		res = questionDao.deleteQuestion(conn, param);
		
		if(res != 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
	}


	@Override
	public void deleteReport(Report param) {
		System.out.println("deleteReportSerive called");
		Connection conn = JDBCTemplate.getConnection();
		int res = 0;
		res = reportDao.deleteReport(conn, param);
		
		if(res != 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
	}

	@Override
	public List<Map<String, Object>> getReportDatil(HttpServletRequest req) {
		System.out.println("getReportDatil called");
		Connection conn = JDBCTemplate.getConnection();
		Report param = new Report();
		System.out.println("report_no : " + Integer.parseInt(req.getParameter("report_no")));
		param.setReport_no(Integer.parseInt(req.getParameter("report_no")));
		param.setReport_type(req.getParameter("report_type"));
		List<Map<String, Object>> mapList = reportDao.ReportDatilList(param, conn);
		
		JDBCTemplate.close(conn);
		return mapList;
	}

	@Override
	public Paging getReportPaging(HttpServletRequest req, Member param1, Report param2) {
		System.out.println("getMemberPaging called");
		String page = req.getParameter("curPage");
		System.out.println("curPage : " + page );
		int curPage = 0;
		if(page != null && !"".equals(page)) {
			curPage = Integer.parseInt(page);
		}else {
			System.out.println("[WARNING] curPage값이 null이거나 비어있음");
		}
		
		Connection conn = JDBCTemplate.getConnection();
		
		int totalCount = 0; 
		totalCount = memberDao.searchReportCnt(conn, param1, param2);

		//Paging 객체 생성
		Paging paging = new Paging(totalCount, curPage);
		
		JDBCTemplate.close(conn);
		
		return paging;
	}

	@Override
	public List<Map<String, Object>> getReportList(Member param1, Report param2, Paging paging) {
		System.out.println("getReportList called");
		Connection conn = JDBCTemplate.getConnection();

		List<Map<String, Object>> mapList = reportDao.searchReportList(param1, param2, paging, conn);
		
		JDBCTemplate.close(conn);
		return mapList;

	}


	@Override
	public void updateExecuteResult(HttpServletRequest req) {
		Connection conn =JDBCTemplate.getConnection();
		int res = 0;
		if((req.getParameter("memo") != null && !"".equals(req.getParameter("memo")))  
				|| (req.getParameter("execute_result") != null && !"".equals(req.getParameter("execute_result")))) {
			res = reportDao.updateResult(conn, req);
			if(res == 0) {
				JDBCTemplate.rollback(conn);
			}else {
				JDBCTemplate.commit(conn);
			}
			JDBCTemplate.close(conn);
		}else {
			System.out.println("요청정보가 없습니다.");
		}
		
	}

	@Override
	public List<Map<String, Object>> getPurnishList(Member param, Paging paging) {
		System.out.println("getReportList called");
		Connection conn = JDBCTemplate.getConnection();

		List<Map<String, Object>> mapList = memberDao.searchPurnishList(param, paging, conn);
		
		JDBCTemplate.close(conn);
		return mapList;
	}

	@Override
	public void deletePurnish(Ban param) {
		System.out.println("deletePurnishSerive called");
		Connection conn = JDBCTemplate.getConnection();
		int res = 0;
		res = memberDao.deletePurnish(conn, param);
		
		if(res != 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		
	}
	@Override
	public List<Map<String, Object>> getPurnishDatailList(HttpServletRequest req) {
		System.out.println("getReportList called");
		Connection conn = JDBCTemplate.getConnection();

		List<Map<String, Object>> mapList = memberDao.getPurnishDetailList(req, conn);
		
		JDBCTemplate.close(conn);
		return mapList;
	}
	
	
	@Override
	public void insertBan(Member member, Ban ban, int date) {
		Connection conn =JDBCTemplate.getConnection();
		
		int res = 0;
		res = reportDao.insertBan(conn, ban, member, date);
		if(res == 0) {
			JDBCTemplate.rollback(conn);
		}else {
			JDBCTemplate.commit(conn);
		}
		JDBCTemplate.close(conn);
		
	}
	

}
