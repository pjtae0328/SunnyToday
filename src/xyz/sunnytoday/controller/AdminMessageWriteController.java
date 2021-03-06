package xyz.sunnytoday.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.sunnytoday.dto.Event;
import xyz.sunnytoday.dto.Member;
import xyz.sunnytoday.dto.Message;
import xyz.sunnytoday.service.face.AdminMessageEventService;
import xyz.sunnytoday.service.face.AdminMessageService;
import xyz.sunnytoday.service.impl.AdminMessageEventServiceImpl;
import xyz.sunnytoday.service.impl.AdminMessageServiceImpl;


@WebServlet("/admin/message/write")
public class AdminMessageWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminMessageService messageService = new AdminMessageServiceImpl();
	private AdminMessageEventService eventService = new AdminMessageEventServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/admin/message/write [GET]");
		
		//쪽지를 받을 회원 수
		int totalcount = 0;
		
		//전달받는 회원번호 배열이 null값이 아닐 때
		if(req.getParameter("no[]") != null) {
		
			//전달받은 회원번호
			int[] userno = messageService.getParam(req);
		
			//전달받은 회원번호의 회원리스트
			List<Member> list = messageService.getlist(userno);
		
			//쪽지를 받을 회원 수
			String[] arr = req.getParameterValues("no[]");
			totalcount = arr.length;
			
			//회원 리스트 전달
			req.setAttribute("list", list);
		
		} else { //전달받는 회원이 없을 때
			//전체회원
			totalcount = messageService.totalUser();
		}
		
		//쪽지이벤트
		List<Event> event = eventService.getEvent();
		
		//쪽지를 받을 회원 수 전달
		req.setAttribute("totalcount", totalcount);
		
		//쪽지이벤트 리스트 전달
		req.setAttribute("event", event);
		
		req.getRequestDispatcher("/WEB-INF/views/admin/management/message/message_sendWrite.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/admin/message/write [POST]");
		
		
		//전달받는 회원번호 배열이 null값이 아닐 때
		if( req.getParameterValues("no[]") != null ) {

			//전달받은 회원번호
			int[] userno = messageService.getParam(req);
			
			//메세지 보내기 쪽지 제목,내용
			messageService.getContent(req, userno);
			
			
		} else { //전달받은 회원 번호 배열이 없을 때
			
			messageService.sendMessage(req);
			
		}
		
		resp.sendRedirect("/admin/message");
	}
}
