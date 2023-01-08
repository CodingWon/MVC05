package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;

public class MemberDbcheckController implements Controller {

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String id = request.getParameter("id");
		MemberDAO memberDAO = new MemberDAO();
		String isDouble = memberDAO.dbCheck(id);
		
		//ajax() 함수로 만들어 놓은 callback 함수(dbCheck)로 응답이 된다.
		response.getWriter().print(isDouble);
		return null;
	}

}
