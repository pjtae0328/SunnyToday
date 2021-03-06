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
import xyz.sunnytoday.common.config.AppConfig;
import xyz.sunnytoday.dto.Board;
import xyz.sunnytoday.dto.File;
import xyz.sunnytoday.dto.Post;
import xyz.sunnytoday.service.face.BoardService;
import xyz.sunnytoday.service.impl.BoardServiceImpl;

import javax.servlet.http.HttpSession;

@WebServlet("/board/main")
public class BoardMainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		Paging paging = boardService.getPaging(req);
		List<Map<String, Object>> list = boardService.getList(paging);
		
		boardService.setThumFile(list);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("notice", boardService.getNotices().get(Board.TYPE_NOTICE));

//		System.out.println("mainThumFile : " + mainThumFile );

		req.getRequestDispatcher("/WEB-INF/views/user/board/boardMain.jsp").forward(req, resp);
	}
	

}
