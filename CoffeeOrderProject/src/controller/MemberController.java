package controller;

import dto.MemberDto;
import service.MemberService;
import service.impl.MemberServiceImpl;
import view.JoinView;
import view.LoginView;
import view.OrderView;
import view.FirstView;
import view.AdminMenuAdd;
import view.AdminMenuDelete;

public class MemberController {
	MemberService memService = new MemberServiceImpl();
	
	//처음 화면 열기
	public void firstView() {
		new FirstView();
	}
	// 로그인창 열기 
	public void loginView() {
		new LoginView();
	}
	// 회원가입창 열기
	public void joinView() {
		new JoinView();
	}
	//메뉴 추가창 열기
	public void adminMenuAdd() {
		new AdminMenuAdd();
	}
	//메뉴 삭제창 열기
	public void adminMenuDelete() {
		new AdminMenuDelete();
	}
	// 아이디 확인하기
	public boolean checkId(String id) {
		return memService.getId(id);
	}
	// 회원가입 버튼누름 -> 회원데이터 새로 저장하기
	public boolean addMember(MemberDto dto) {
		return memService.addMember(dto);
	}
	// 로그인 하기 
	public MemberDto login(String id, String pwd) {
		return memService.login(id, pwd);
	}
	
	
}
