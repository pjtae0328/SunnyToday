package xyz.sunnytoday.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/leaveid")
public class MypageLeaveIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/mypage/leaveid [GET]");
		
//		if( req.getSession().getAttribute("user") == null
//				|| !(boolean)req.getSession().getAttribute("user") ) {
//			
//			resp.sendRedirect("/");
//			
//			return;
//		}
		
		
		req.getRequestDispatcher("/WEB-INF/views/user/mypage/leave_Id.jsp").forward(req, resp);
	}
}
