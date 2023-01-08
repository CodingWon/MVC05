package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLogoutController implements Controller {

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx = request.getContextPath();
		//현재 세션 갖고와서 세션 종료하기
		//1. 강제로 종료
		request.getSession().invalidate();
		//2. 브라우저를 종료
		//3. 세션이 종료될때까지 기다리는 것(session Time out : 30분)
		
		return "redirect:"+ctx+"/memberList.do";
	}

}
