package kr.bit.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;
import kr.bit.model.MemberVO;

public class MemberLoginController implements Controller {

	private static Logger log = Logger.getGlobal();
	
	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String ctx = request.getContextPath();
		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");
		log.info("userId : " + userId + " password" + password);

		MemberVO memberVO = new MemberVO();
		memberVO.setId(userId);
		memberVO.setPass(password);
		
		MemberDAO memberDAO = new MemberDAO();
		String name = memberDAO.memberLogin(memberVO);
		
		if(name != null && !"".equals(name)) {
			//로그인 성공
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("name",name);
		}else {
			request.getSession().setAttribute("userId", "");
			request.getSession().setAttribute("name", "");
			request.getSession().setAttribute("msg", "사용자 정보가 올바르지 않습니다.");
		}
		
		
		return "redirect:"+ctx+"/memberList.do";
	}

}
