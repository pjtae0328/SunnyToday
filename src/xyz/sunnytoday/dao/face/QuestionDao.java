package xyz.sunnytoday.dao.face;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import xyz.sunnytoday.common.Paging;
import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.dto.Question;

public interface QuestionDao {

	/**
	 * 게시판 글 전체 조회
	 * @param conn - DB접속
	 * @param paging - 페이징 정보 객체
	 * @return 게시판 전체를 List로 반환
	 */
	public List<Map<String, Object>> selectListAll(Connection conn, Paging paging);

	/**
	 * 총 게시글 수 조회
	 * 
	 * @param connection - DB연결 객체
	 * @return int - Board테이블 전체 행 수 조회 결과
	 */
	public int selectCntAll(Connection conn);

	/**
	 * userno을 통해서 nick확인
	 * @param conn
	 * @param userno
	 * @return
	 */
	public String selectNickByUserno(Connection conn, int userno);
	
	/**
	 * userno을 통해서 id확인
	 * @param conn
	 * @param userno
	 * @return
	 */
	public String selectIdByUserno(Connection conn, int userno);
	/**
	 * 문의글 업로드
	 * @param conn
	 * @param question - 정보 들어있는 객체
	 * @return
	 */
	public int insert(Connection conn, Question question);

	/**
	 * userno으로 nick 찾기
	 * @param connection
	 * @param userno
	 * @return
	 */
	public String selectNickByUserno(Connection conn, Question userno);

	/**
	 * questionNo으로 상세 문의 조회하기
	 * @param conn
	 * @param questionNo
	 * @return
	 */
	public Question selectQuestionByquestionno(Connection conn, Question questionNo);

	/**
	 * 문의 글 수정
	 * @param conn
	 * @param question - 수정 된 정보 들어있는 객체
	 * @return
	 */
	public int update(Connection conn, Question question);

	/**
	 * 문의 글 삭제
	 * @param conn
	 * @param questionNo
	 * @return
	 */
	public int deleteQuestionByQuestionno(Connection conn, Question questionNo);



}
