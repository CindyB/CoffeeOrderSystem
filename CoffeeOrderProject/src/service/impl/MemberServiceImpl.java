package service.impl;

import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import dto.MemberDto;
import service.MemberService;

public class MemberServiceImpl implements MemberService {
	MemberDao dao = new MemberDaoImpl();
	
	// 아이디 중복검사 
	@Override
	public boolean getId(String id) {
		return dao.getId(id);
	}
	
	// 회원가입하기
	@Override
	public boolean addMember(MemberDto dto) {
		return dao.addMember(dto);
	}

	// 로그인 하기 
	@Override
	public MemberDto login(String id, String pwd) {
		return dao.login(id, pwd);
	}
	
	
	
}
