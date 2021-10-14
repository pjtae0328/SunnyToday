package xyz.sunnytoday.dao.face;

import java.sql.Connection;
import java.util.List;

import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.dto.Question;
import xyz.sunnytoday.util.Paging;

public interface QuestionMenageDao {
	
	/**
	 * id로 검색된 총 페이지 수 조회
	 * @param conn - DB 연결 객체
	 * @param param - member dto 객체
	 * @return - id로 검색된 총 페이지 수 반환
	 */
	public int selectIdCntAll(Connection conn, Member param);

	/**
	 * nick로 검색된 총 페이지 수 조회
	 * @param conn - DB 연결 객체
	 * @param param - member dto 객체
	 * @return - id로 검색된 총 페이지 수 반환
	 */
	public int selectNickCntAll(Connection conn, Member param);
	
	/**
	 * question의 모든 데이터(행)의 수를 조회
	 * @param conn - DB 연결객체
	 * @return - 조회된 총 페이지의 수
	 */
	public int selectCntAll(Connection conn);

	/**
	 * id로 검색된 문의 리스트 조회
	 * @param param - Member DTO 객체
	 * @param paging - 페이징 객체
	 * @param conn - DB 연결 객체
	 * @return - 조회된 문의 리스트 반환
	 */
	public List<Question> searchUserId(Member param, Paging paging, Connection conn);

	/**
	 * nick으로 검색된 문의 리스트 조회
	 * @param param - Member DTO 객체
	 * @param paging - 페이징 객체
	 * @param conn - DB 연결 객체
	 * @return - 조회된 문의 리스트 반환 	
	 */
	public List<Question> searchUserNick(Member param, Paging paging, Connection conn);

	/**
	 * 모든 문의 리스트 조회
	 * @param param - Member DTO 객체
	 * @param paging - 페이징 객체
	 * @param conn - DB 연결 객체
	 * @return - 조회된 문의 리스트 반환 	
	 */
	public List<Question> getQuestionList(Connection conn, Paging paging);
	
	/**
	 * 조회된 문의 사항의 세부 정보를 조회
	 * @param conn - DB 연결 객체
	 * @param param - Question dto 객체
	 * @return - 조회된 문의사항의 세부 정보 반환
	 */
	public Question getQuestionDatil(Connection conn, Question param);

	/**
	 * 입력된 답변을 DB에 저장
	 * @param conn - DB연결 객체
	 * @param param - Question dto 객체
	 * @return - 답변을 DB에 저장여부를 반환
	 */
	public int setUpdateAnswer(Connection conn, Question param);

	/**
	 * 선택된 번호가 들어간 모든 행을 삭제
	 * @param conn - DB 연결 객체
	 * @param param - Question dto 객체 -> 삭제할 번호가 들어가있음
	 * @return - 삭제 성공 여부를 반환
	 */
	public int deleteQuestion(Connection conn, Question param);

}