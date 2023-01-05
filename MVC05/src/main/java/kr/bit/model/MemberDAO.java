package kr.bit.model;
import java.io.IOException;
import java.io.InputStream;
// JDBC->myBatis, JPA
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class MemberDAO {
	
	private static SqlSessionFactory sessionFactory;
	
	static {
		try {
			String resource = "org/mybatis/example/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//회원전체 리스트보기
	public List<MemberVO> memberList() {
		//SqlSession 생성
		SqlSession session = sessionFactory.openSession();
		//mapper와 연결
		List<MemberVO> list = session.selectList("memberList");
		session.close();
		
		return list;
	}

}


