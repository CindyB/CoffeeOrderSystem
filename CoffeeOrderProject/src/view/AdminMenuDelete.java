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

public class AdminMenuDelete extends Frame implements WindowListener, ActionListener {

	JLabel title;
	JPanel p[];
	JButton back, deleteBtn;
	JLabel l[];
	TextField txt[];

	public AdminMenuDelete() {
		super("메뉴 삭제");
		setLayout(new GridLayout(6, 0));

		p = new JPanel[6];
		for (int i = 0; i < p.length; i++) {
			p[i] = new JPanel();
			p[i].setSize(450, 90);
			p[i].setLayout(null);
			p[i].setBackground(Color.WHITE);
			add(p[i]);
		}

		title = new JLabel("메 뉴 삭 제");
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
		deleteBtn = new JButton("삭제 완료");
		deleteBtn.setBounds(190, 10, 100, 50);
		deleteBtn.addActionListener(this);
		p[5].add(deleteBtn);

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
		JButton nowBtn = (JButton) e.getSource(); // 이벤트가 발생한 버튼정보 받아서
		Singleton s = Singleton.getInstance();

		if (nowBtn == deleteBtn) { // 삭제버튼일 경우

			for (int i = 1; i < txt.length; i++) { // 빈칸있는지 확인하기
				if (txt[i].getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "메뉴 정보들을 모두 입력해주세요!");
					return;
				}
			}
			String name = txt[1].getText(); // 입력된 메뉴 이름과
			int price = Integer.parseInt(txt[2].getText().toString()); // 가격

			System.out.println("name:" + name + "price:" + price);
			MenuDto dto = s.ordCtrl.deleteMenu(name, price); // 입력된 정보에 해당하는 dto가져오기

			if (dto != null) {
				System.out.println(dto + "삭제완료");
				JOptionPane.showMessageDialog(null, "메뉴가 삭제되었습니다! ");

				dispose();
			} else {
				System.out.println(dto + "삭제실패");
				JOptionPane.showMessageDialog(null, "메뉴 삭제에 실패하셨습니다. ");
			}
		}
	}
}