package xyz.sunnytoday.dao.face;

import java.sql.Connection;

import java.util.List;

import xyz.sunnytoday.common.util.Paging;
import xyz.sunnytoday.dto.AdminBoard;

public interface AdminBoardDao {

	/**
	 * Board테이블 전체 조회
	 * 
	 * @param conn - DB연결 객체
	 * @return List<Board> - Board테이블 전체 조회 결과 리스트
	 */
	public List<AdminBoard> selectAll(Connection conn);

	/**
	 * Board테이블 전체 조회
	 * 	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @param conn - DB연결 객체
	 * @return List<Board> - Board테이블 전체 조회 결과 리스트
	 */
	public List<AdminBoard> selectAll(Connection conn, Paging paging);
	
	/**
	 * 총 게시글 수 조회
	 * 
	 * @param conn - DB연결 객체
	 * @return int - Board테이블 전체 행 수 조회 결과
	 */
	public int selectCntAll(Connection conn);

	public int boardCntAll(Connection conn);
	
	public int titleCount(Connection conn);
	
	/**
	 * 특정 게시글 조회
	 * 
	 * @param boardno - 조회할 boardno를 가진 객체
	 * @return Board - 조회된 결과 객체
	 */
	public AdminBoard selectBoardByBoardno(Connection conn, AdminBoard board_no);
	
	public int insert(Connection conn, AdminBoard board);

	public int delete(Connection conn, AdminBoard board);
	
	public int update(Connection conn, AdminBoard board);

}

