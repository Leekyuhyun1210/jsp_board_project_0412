package com.leekyuhyun.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leekyuhyun.board.dao.BoardDao;
import com.leekyuhyun.board.dto.BoardDto;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")		// @ : Annotation (애너테이션, 명시하다)
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardDao dao = new BoardDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(request.getContextPath());
		//System.out.println(request.getRequestURI());
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String comm = uri.substring(conPath.length()); //.do 요청만 빼서 저장
		System.out.println(comm);
		
		if(comm.equals("/write_form.do")) {
			//System.out.println("boardList.jsp 실행");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("writeForm.jsp");
			dispatcher.forward(request, response);
		} else if(comm.equals("/write.do")) {
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			dao.write(writer, subject, content);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("list.do");
			dispatcher.forward(request, response);
			
		} else if(comm.equals("/list.do")) {
			ArrayList<BoardDto> dtos = dao.list();
			request.setAttribute("list", dtos);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
