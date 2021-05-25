package service;

import dto.MemberDto;

public interface MemberService {
	
	// 아이디 중복검사 
	public boolean getId(String id);
	// 회원가입하기
	public boolean addMember(MemberDto dto);	
	// 로그인하기 
	public MemberDto login(String id, String pwd); 
}
