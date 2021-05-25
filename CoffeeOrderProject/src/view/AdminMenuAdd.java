package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dto.MenuDto;
import singleton.Singleton;

public class AdminMenuAdd extends Frame implements WindowListener, ActionListener {
	JButton addBtn; // 추가버튼
	JPanel p[];
	JLabel title;
	JLabel l[];
	TextField txt[];

	public AdminMenuAdd() {

		super("메뉴 추가");
		setLayout(new GridLayout(6, 0));

		p = new JPanel[6];
		for (int i = 0; i < p.length; i++) {
			p[i] = new JPanel();
			p[i].setSize(450, 90);
			p[i].setLayout(null);
			p[i].setBackground(Color.WHITE);
			add(p[i]);
		}

		title = new JLabel("메 뉴 추 가");
		title.setSize(450, 100);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("NEXONFootballGothicL", Font.BOLD, 30));
		p[0].add(title);

		String labels[] = { "메뉴 번호", "메뉴 이름", "가격 " };
		l = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			l[i] = new JLabel(labels[i]);
			l[i].setBounds(0, 30, 130, 30);
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			l[i].setFont(new Font("돋움", Font.BOLD, 13));
			p[i + 1].add(l[i]);
		}

		txt = new TextField[3];
		for (int i = 0; i < 3; i++) {
			txt[i] = new TextField();
			txt[i].setBounds(150, 30, 200, 30);
			p[i + 1].add(txt[i]);
		}
		txt[0].setEditable(false);

		addBtn = new JButton("추가 완료");
		addBtn.setBounds(190, 10, 100, 50);
		addBtn.addActionListener(this);
		p[5].add(addBtn);

		setBounds(400, 100, 500, 540);
		setVisible(true);
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		int result = 0;
		Singleton s = Singleton.getInstance();
		JButton nowBtn = (JButton) e.getSource();

		if (nowBtn == addBtn) { // 메뉴 추가 버튼을 눌렀을 시
			for (int i = 1; i < txt.length; i++) {
				if (txt[i].getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "메뉴 정보들을 모두 입력해주세요!");
					return;
				}
			}

			int seq = s.ordCtrl.getMenuCount() + 1; // 번호 최대값
			String name = txt[1].getText();
			String id = s.loginId;
			int price = Integer.parseInt(txt[2].getText().toString());

			for (int i = 0; i < txt.length; i++) {
				txt[i].setText("");
			}

			MenuDto dto = new MenuDto(seq, id, name, price);
			boolean b = s.ordCtrl.addMenu(dto);

			if (b) {
				JOptionPane.showMessageDialog(null, "메뉴가 추가되었습니다! ");
				dispose();
			} else
				JOptionPane.showMessageDialog(null, "메뉴 추가에 실패하셨습니다. ");

		}

	}
}