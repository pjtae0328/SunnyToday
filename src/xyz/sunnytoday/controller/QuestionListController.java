package xyz.sunnytoday.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.sunnytoday.common.Paging;
import xyz.sunnytoday.dto.Post;
import xyz.sunnytoday.dto.Question;
import xyz.sunnytoday.service.face.QuestionService;
import xyz.sunnytoday.service.impl.QuestionServiceImpl;

@WebServlet("/question/list")
public class QuestionListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	QuestionService questionService = new QuestionServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/question/list [GET]");
		
		Paging paging = questionService.getPaging(req);
		List<Map<String, Object>> list = questionService.getList(paging);
		
//		for( Map<String, Object> e : list ) {
//		System.out.println( e );
//	}	

		req.setAttribute("paging", paging);
		req.setAttribute("list", list);
		if( req.getSession().getAttribute("userno") != null ) {
			req.setAttribute( "loginMember", questionService.loginMember(req));
		}
		
		req.getRequestDispatcher("/WEB-INF/views/user/question/questionList.jsp").forward(req, resp);
	}
}
