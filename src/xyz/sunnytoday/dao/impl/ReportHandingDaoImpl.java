package xyz.sunnytoday.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xyz.sunnytoday.common.JDBCTemplate;
import xyz.sunnytoday.dao.face.ReportHandlingDao;
import xyz.sunnytoday.dto.Ban;
import xyz.sunnytoday.dto.Comments;
import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.dto.Post;
import xyz.sunnytoday.dto.Report;
import xyz.sunnytoday.dto.Report_Category;
import xyz.sunnytoday.util.Paging;

public class ReportHandingDaoImpl implements ReportHandlingDao{
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public List<Map<String, Object>> searchReportList(Member param1, Report param2, Paging paging, Connection conn) {
		System.out.println("searchReportList called");
		
		String sql ="";
		sql += "SELECT * FROM (";
		sql +=	" SELECT rownum rnum, R.* FROM(";
		sql +=		" SELECT ur.report_no, rc.title, m.id, ur.report_date, ur.report_type, ";
		sql +=		" m.user_no, ur.execute_result, ur.target_no";
		sql +=		" FROM user_report ur, member m, report_category rc";
		sql +=		" WHERE m.user_no = ur.user_no and ur.report_c_no = rc.report_c_no";
		
		if(param1.getUserid() != null && !"".equals(param1.getUserid())) {
			sql +=		" AND m.id LIKE ?";
		}else if(param1.getNick() != null && !"".equals(param1.getNick())) {
			sql +=		" AND m.nick LIKE ?";
		}
		if(param2.getReport_type() != null && !"".equals(param2.getReport_type())) {
			sql +=		" AND ur.report_type LIKE ?";
		}
		sql +=		" ORDER BY report_no DESC";
		sql += 	" ) R";
		sql += " ) report_board";
		sql += " WHERE rnum BETWEEN ? AND ?";

		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = null;

		try {
			ps = conn.prepareStatement(sql);

			int paramIdx = 1;
			if(param1.getUserid() != null && !"".equals(param1.getUserid())) {
				ps.setString(paramIdx++, "%" + param1.getUserid() +"%");
			}else if(param1.getNick() != null && !"".equals(param1.getNick())) {
				ps.setString(paramIdx++, "%" + param1.getNick() +"%");
			}
			if(param2.getReport_type() != null && !"".equals(param2.getReport_type())) {
				ps.setNString(paramIdx++, "%" + param2.getReport_type() + "%");
			}
			ps.setInt(paramIdx++, paging.getStartNo());
			ps.setInt(paramIdx++, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				map = new HashMap<>();

				Report report = new Report();
				Report_Category report_c = new Report_Category();
				Member member = new Member();
				report.setReport_no(rs.getInt("report_no"));
				report.setReport_date(rs.getDate("report_date"));
				report.setReport_type(rs.getString("report_type"));
				report.setTarget_no(rs.getInt("target_no"));
				report.setExecute_result(rs.getString("execute_result"));
				
				report_c.setTitle(rs.getString("title"));
				
				member.setUserno(rs.getInt("user_no"));
				member.setUserid(rs.getString("id"));
				
				map.put("rp", report);
				map.put("rpc", report_c);
				map.put("m", member);
				
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int deleteReport(Connection conn, Report param) {
		System.out.println("deleteReport called");
		String sql = "";
		sql += "DELETE FROM user_report";
		sql += " WHERE report_no = ?";
		int res = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, param.getReport_no());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return res;
	}

	@Override
	public List<Map<String, Object>> ReportDatilList(Report param, Connection conn) {
		System.out.println("ReportDatilList called");
		System.out.println(param.getReport_type()); // -> ?????? ?????????
		String sql ="";
	
		sql += " select ur.report_no, ur.report_date, detail, memo";
		sql += " , report_type, execute_result, target_no, rc.title, m.id, m.user_no";
		sql += "  , p.post_no";
		if("C".equals(param.getReport_type())) {
			sql +=  	" , cm.content, cm.comments_no ";
		}else {
			sql +=		" , p.content";
		}
	
		sql +=	" FROM user_report ur, member m, report_category rc, post p";
		
		if("C".equals(param.getReport_type())) {
			sql += 		" , comments cm";
		}
		
		sql +=	" WHERE ur.report_c_no = rc.report_c_no ";
		sql +=	" and ur.user_no = m.user_no";
		sql += " and p.post_no = ur.post_no";
		
		if("C".equals(param.getReport_type())) {
			sql +=		" and cm.comments_no = ur.comments_no";
		}	
		sql +=		" and ur.report_no = ?";
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, param.getReport_no());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				map = new HashMap<>();
				Report report = new Report();
				Report_Category report_c = new Report_Category();
				Member member = new Member();
				Comments comments = new Comments();
				Post post = new Post();
				report.setReport_no(rs.getInt("report_no"));
				report.setReport_date(rs.getDate("report_date"));
				report.setDetail(rs.getNString("detail"));
				report.setMemo(rs.getString("memo"));
				report.setReport_type(rs.getString("report_type"));
				report.setExecute_result(rs.getString("execute_result"));
				report.setTarget_no(rs.getInt("target_no"));
				
				report_c.setTitle(rs.getString("title"));
				
				member.setUserid(rs.getString("id"));
				member.setUserno(rs.getInt("user_no"));
				if("C".equals(param.getReport_type())) {
					comments.setContent(rs.getString("content"));
					comments.setComments_no(rs.getInt("comments_no"));
					map.put("cm", comments);
					
				}else {
					post.setContent(rs.getString("content"));
				}
				
				post.setPost_no(rs.getInt("post_no"));
				map.put("p", post);	
				
				map.put("rp", report);
				map.put("rpc", report_c);
				map.put("m", member);
				

				list.add(map);//????????? ??????		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return list;
	}

	@Override
	public int insertBan(Connection conn, Ban ban, Member member, int date) {
		System.out.println("insertBan called");
		String sql = "";
		sql += "INSERT INTO ban (ban_no, user_no, ban_type, expiry_date, reason)";
		sql += " VALUES (ban_seq.nextval, ?, ?, sysdate + ?, ?)";
		int res = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, member.getUserno());
			ps.setString(2, ban.getBan_type());
			ps.setInt(3, date);
			ps.setString(4, "????????? ?????? ??????");
			res = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int updateResult(Connection conn, HttpServletRequest req) {
		System.out.println("updateResult called");

		String sql = "";
		sql += "UPDATE user_report SET execute_result = ?, memo = ?";
		sql += " WHERE report_no = ?";
		
		int res = 0; 
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("execute_result"));
			ps.setString(2, req.getParameter("memo"));
			ps.setString(3, req.getParameter("report_no"));
			res = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	
}
