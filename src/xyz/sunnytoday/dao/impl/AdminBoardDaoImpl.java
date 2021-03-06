package xyz.sunnytoday.dao.impl;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xyz.sunnytoday.common.JDBCTemplate;
import xyz.sunnytoday.common.util.Paging;
import xyz.sunnytoday.dao.face.AdminBoardDao;
import xyz.sunnytoday.dto.Board;

public class AdminBoardDaoImpl implements AdminBoardDao {

	@Override
	public List<Board> selectAll(Connection conn) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "";
		sql += "SELECT * FROM board";
		sql += " ORDER BY board_no DESC";
		
		List<Board> boardList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board b = new Board(); 
				b.setBoard_no( rs.getInt("board_no") );
				b.setComments_grant( rs.getString("comments_grant"));
				b.setIndex(rs.getInt("index"));
				b.setLike(rs.getString("like"));
				b.setList_grant(rs.getString("list_grant"));
				b.setRead_grant(rs.getString("read_grant"));
				b.setShow(rs.getString("show"));
				b.setTitle( rs.getString("title") );
				b.setTitle_length(rs.getInt("title_length"));
				b.setWrite_grant(rs.getString("write_grant"));
				
				boardList.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return boardList;
	}

	@Override
	public List<Board> selectAll(Connection conn, Paging paging) {
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		String sql = "";
		sql += "SELECT * FROM (";
		sql += "	SELECT rownum rnum, B.* FROM (";
		sql += "		SELECT";
		sql += "		*";
//		sql += "			board_no, comments_grant, \"index\"";
//		sql += "			, \"like\", list_grant";
//      sql += "            , read_grant, \"show\"";
//      sql += "            , \"title\", title_length";
//      sql += "			, write_grant";
		sql += "		FROM board";
		sql += "		ORDER BY board_no DESC";
		sql += "	) B";
		sql += " ) BOARD";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		//?????? ????????? List
		List<Board> boardList = new ArrayList<>(); 
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board b = new Board();
				
				b.setBoard_no( rs.getInt("board_no") );
				b.setComments_grant( rs.getString("comments_grant"));
				b.setIndex(rs.getInt("index"));
				b.setLike(rs.getString("like"));
				b.setList_grant(rs.getString("list_grant"));
				b.setRead_grant(rs.getString("read_grant"));
				b.setShow(rs.getString("show"));
				b.setTitle( rs.getString("title") );
				b.setTitle_length(rs.getInt("title_length"));
				b.setWrite_grant(rs.getString("write_grant"));
				
				//???????????? ????????? ??????
				boardList.add(b);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardList;
	}

	@Override
	public int selectCntAll(Connection conn) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//SQL ??????
		String sql = "";
		sql += "SELECT count(*) FROM board";
		
		//??? ????????? ???
		int count = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return count;	
	}
	
	@Override
	public int boardCntAll(Connection conn){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//SQL ??????
		String sql = "";
		sql += "SELECT count(*) count FROM board";
		
		//??? ????????? ???
		int boardCount = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next())
				boardCount = rs.getInt("count");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardCount;	
	}

	@Override
	public int titleCount(Connection conn) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//SQL ??????
		String sql = "";
		sql += "SELECT count(title) count FROM board";
		
		//??? ????????? ???
		int titleCount = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next())
				titleCount = rs.getInt("count");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return titleCount;	
	}
	
	public int selectCntTitle(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = ""
				+ "SELECT count(*) FROM board"
				+ " WHERE title = ? ";
				
		int Count = 0;
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return Count;
	}
	@Override
	public Board selectBoardByBoardno(Connection conn, Board board_no) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//SQL ??????
		String sql = "";
		sql += "SELECT * FROM board";
		sql += " WHERE board_no = ?";
		
		//?????? ????????? Board??????
		Board viewBoard = null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL?????? ??????
			
			ps.setInt(1, board_no.getBoard_no()); //????????? ????????? ?????? ??????
			
			rs = ps.executeQuery(); //SQL ?????? ??? ???????????? ??????
			
			//?????? ?????? ??????
			while(rs.next()) {
				viewBoard = new Board(); //????????? ?????? ??????
				
				//????????? ??? ??? ??????
				viewBoard.setBoard_no( rs.getInt("board_no") );
				viewBoard.setComments_grant( rs.getString("comments_grant"));
				viewBoard.setIndex(rs.getInt("index"));
				viewBoard.setLike(rs.getString("like"));
				viewBoard.setList_grant(rs.getString("list_grant"));
				viewBoard.setRead_grant(rs.getString("read_grant"));
				viewBoard.setShow(rs.getString("show"));
				viewBoard.setTitle( rs.getString("title") );
				viewBoard.setTitle_length(rs.getInt("title_length"));
				viewBoard.setWrite_grant(rs.getString("write_grant"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB?????? ??????
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//?????? ?????? ??????
		return viewBoard;	
	}
	
	@Override
	public int insert(Connection conn, Board board) {
		
		PreparedStatement ps = null;
		
		//SQL ??????
		String sql = "";
		sql += "INSERT INTO board(board_no, comments_grant, \"LIKE\"";
		sql += " , list_grant, read_grant, \"SHOW\", \"TITLE\"";
		sql += " , title_length, write_grant, \"INDEX\"";
//		sql += "FROM BOARD)";
		sql += " )";
		sql += " VALUES (board_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
//			ps.setInt(1, board.getBoard_no());
			ps.setString(1, board.getComments_grant());
			ps.setString(2, board.getLike());
			ps.setString(3, board.getList_grant());
			ps.setString(4, board.getRead_grant());
			ps.setString(5, board.getShow());
			ps.setString(6, board.getTitle());
			ps.setInt(7, board.getTitle_length());
			ps.setString(8, board.getWrite_grant());
			ps.setInt(9, board.getIndex());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;	
	
	}

	@Override
	public int delete(Connection conn, Board board) {
		//?????? ????????? ?????? ?????? ??????
		String sql = "";
		sql += "DELETE board";
		sql += " WHERE board_no = ?";
		
		//DB ??????
		PreparedStatement ps = null; 
		
		int res = -1;
		
		try {
			//DB??????
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getBoard_no());

			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public int update(Connection conn, Board board) {
		
		String sql = "";
		sql += "UPDATE board";
		sql += " SET comments_grant = ?,";
		sql += " 	\"LIKE\" = ?,";
		sql += " 	list_grant = ?,";
		sql += " 	read_grant = ?,";
		sql += " 	\"SHOW\" = ?,";
		sql += " 	\"TITLE\" = ?,";
		sql += " 	title_length = ?,";
		sql += " 	write_grant = ?";
		sql += " WHERE board_no = ?";
		
		//DB ??????
		PreparedStatement ps = null; 
		
		int res = -1;
		
		try {
			//DB??????
			ps = conn.prepareStatement(sql);
			ps.setString(1, board.getComments_grant());
			ps.setString(2, board.getLike());
			ps.setString(3, board.getList_grant());
			ps.setString(4, board.getRead_grant());
			ps.setString(5, board.getShow());
			ps.setString(6, board.getTitle());
			ps.setInt(7, board.getTitle_length());
			ps.setString(8, board.getWrite_grant());
			ps.setInt(9, board.getBoard_no());
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public int changeBoardno(Connection conn, String value) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "";
		sql += "SELECT board_no FROM board";
		sql += "	WHERE title = ?";
		
		//?????? ?????? ??????
		int board_no = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, value);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				board_no = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return board_no;
	}

	
}
