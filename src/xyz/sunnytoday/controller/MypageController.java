package xyz.sunnytoday.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xyz.sunnytoday.dto.File;
import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.service.face.MypageService;
import xyz.sunnytoday.service.impl.MypageServiceImpl;


@WebServlet("/mypage/profile")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MypageService mypageService = new MypageServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("mypage [GET]");
		
		//로그인 유저 세션의 유저넘버 얻기
		Object param = req.getSession().getAttribute("userno");
		int userno = (int) param;
	
		//유저정보 전달
		Member member = mypageService.selectMember(userno);
		System.out.println(member.getUserno());
		
		//유저 썸네일 전달
		File profile = mypageService.selectProfile(member);
		
		//썸네일 전달
		req.setAttribute("profile", profile);
		
		//유저정보 전달
		req.setAttribute("member", member);
		
		
		req.getRequestDispatcher("/WEB-INF/views/user/mypage/mypage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("mypage [POST]");
		
		//업데이트
		mypageService.update(req);
		
		//로그인 유저 세션의 유저넘버 얻기
		Object param = req.getSession().getAttribute("userno");
		int userno = (int) param;
	
		//유저정보 전달
		Member member = mypageService.selectMember(userno);
		
		//유저 썸네일 전달
		File profile = mypageService.selectProfile(member);
		
		//세션의 프로필 바꾸기
		HttpSession session = req.getSession();
		
		session.setAttribute("pictureThumbnail", profile.getThumbnail_url() ); //새로운 썸네일URL로 갱신하기

		
		resp.sendRedirect("/mypage/profile");
	}
}
