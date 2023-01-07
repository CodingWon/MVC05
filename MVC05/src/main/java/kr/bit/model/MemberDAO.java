package kr.bit.model;

import java.io.IOException;
import java.io.InputStream;
// JDBC->myBatis, JPA
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mysql.jdbc.log.LogFactory;

public class MemberDAO {

	private static SqlSessionFactory sessionFactory;
	private static Logger logger = Logger.getGlobal();

	static {

		try {
			String resource = "kr/bit/mybatis/config.xml".trim();
			logger.info("resource : " + resource);

			InputStream inputStream = Resources.getResourceAsStream(resource);
			logger.info("inputStream : " + inputStream);

			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 회원전체 리스트보기
	public List<MemberVO> memberList() {
		// SqlSession 생성
		SqlSession session = sessionFactory.openSession();
		// mapper와 연결
		List<MemberVO> list = session.selectList("memberList");
		session.close();

		return list;
	}

	// 회원 가입
	public int memberInsert(MemberVO memberVO) {
		SqlSession session = sessionFactory.openSession();
		int cnt = session.insert("memberInsert", memberVO);
		session.commit();
		session.close();

		return cnt;
	}

	// Delete
	public int memberDelete(int num) {
		SqlSession session = sessionFactory.openSession();
		int result = session.delete("memberDelete",num);
		session.commit();
		session.close();
		
		return result;
	}

	//Content
	public MemberVO memberContent(int num) {
		SqlSession session = sessionFactory.openSession();
		MemberVO memberVO = session.selectOne("memberContent",num);
		session.close();
		
		return memberVO;
	}
	
	//Update
	public int memberUpdate (MemberVO vo) {
		SqlSession session = sessionFactory.openSession();
		int cnt = session.update("memberUpdate",vo);
		session.commit();
		session.close();
		
		return cnt;
	}
	
}
