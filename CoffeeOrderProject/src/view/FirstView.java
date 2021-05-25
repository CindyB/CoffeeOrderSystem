package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dto.MemberDto;
import singleton.Singleton;

public class FirstView extends Frame implements WindowListener, ActionListener {
	JButton user, admin; // 사용자, 관리자

	public FirstView() { // 초기화면

		super("초기화면");
		setLayout(null);

		admin = new JButton("Admin");
		admin.setBounds(50, 75, 150, 65);
		admin.addActionListener(this);
		add(admin);

		user = new JButton("User");
		user.setBounds(50, 160, 150, 65);
		user.addActionListener(this);
		add(user);

		setBounds(400, 200, 250, 300);
		setVisible(true);
		addWindowListener(this);
		setBackground(Color.WHITE);

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 화면에 처음 나타날 때
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 닫히려고 할 때 = X버튼 누를 때
		System.exit(0); // X를 누르면 종료
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 닫힌 다음에 호출
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 아이콘화 되었을 때
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 정상화 되었을 때
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 활성화 될 때
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		// 윈도우가 비활성화 되었을 때
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 버튼 클릭 시 발동되는 메소드
		JButton nowBtn = (JButton) e.getSource();
		Singleton s = Singleton.getInstance();

		if (nowBtn == admin) {
			dispose();
			s.memCtrl.loginView();
		} else if (nowBtn == user) {
			dispose();
			s.ordCtrl.orderView();
		}
	}
}
