package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dto.MenuDto;
import dto.OrderDto;
import singleton.Singleton;

public class OrderView extends JFrame implements ActionListener { // 주문창

	JComboBox<String> menuChoice;

	JPanel option[];
	JLabel opLabel[];
	JRadioButton opButton[][];
	JRadioButton shortS, tall, grande;
	JRadioButton vanila, caramel, hazelnut;

	ButtonGroup groupRd[];
	JCheckBox addOption[];

	JLabel cupsLab;
	JTextField cupTxt;

	JButton showMenu, orderMenu;
	JButton showBasket, ordHistory;
	boolean openMenu = false;

	Singleton s;

	public OrderView() {
		super("Order");

		s = Singleton.getInstance();

		// 메인 프레임
		JPanel frame = new JPanel();
		frame.setBounds(0, 0, 660, 400);
		frame.setBackground(Color.GRAY);
		frame.setLayout(null);
		add(frame);

		JLabel title = new JLabel("COFFEE ORDER");
		title.setBounds(0, 10, 660, 30);
		title.setFont(new Font(null, Font.BOLD, 20));
		title.setHorizontalAlignment(JLabel.CENTER);
		frame.add(title);

		// 메뉴보기 버튼
		showMenu = new JButton("메뉴보기");
		showMenu.setBounds(510, 50, 100, 20);
		showMenu.addActionListener(this); // 버튼을 누르면 가격표창이 뜸
		frame.add(showMenu);

		// 메뉴 콤보박스
		int menuNum = s.ordCtrl.getMenu().size(); // 데이터베이스에 저장된 메뉴의 개수
		String menustr[] = new String[menuNum + 1];
		ArrayList<MenuDto> list = s.ordCtrl.getMenu();
		menustr[0] = "*메뉴를 선택하세요*"; // default값 설정
		for (int i = 1; i < menustr.length; i++) {
			menustr[i] = list.get(i - 1).getName();
		} // 메뉴 콤보박스에 들어갈 메뉴 이름 배열에 저장

		menuChoice = new JComboBox<String>(menustr); // 콤보박스배열에 menustr배열 넣음
		menuChoice.setBounds(30, 50, 460, 20);
		frame.add(menuChoice);

		// 옵션 패널
		option = new JPanel[3];
		opLabel = new JLabel[3];

		String opStr[] = { " 사 이 즈", " 시 럽", " 기 타" }; // 옵션 3가지 - 사이즈, 시럽,기타
		for (int i = 0; i < option.length; i++) { // 옵션 선택하는 칸 3개 생성
			option[i] = new JPanel();
			option[i].setBounds(30 + (i * 200), 100, 180, 200);
			option[i].setBackground(Color.white);
			option[i].setLayout(new GridLayout(5, 0));
			frame.add(option[i]);

			opLabel[i] = new JLabel(opStr[i]);
			opLabel[i].setFont(new Font(null, Font.BOLD, 20));
			option[i].add(opLabel[i]);
		}

		String opbtnStr[][] = { // 옵션 종류-라디오 버튼 생성
				{ "short", "Tall", "Grande" }, // 사이즈
				{ "바닐라", "카라멜", "헤이즐넛", "없음" } // 시럽 종류
		};
		addOption = new JCheckBox[2]; // 체크박스 생성
		addOption[0] = new JCheckBox("샷 추가");
		addOption[0].setBackground(Color.white);
		addOption[1] = new JCheckBox("휘핑크림");
		addOption[1].setBackground(Color.white);

		groupRd = new ButtonGroup[2]; // 버튼그룹
		opButton = new JRadioButton[2][3];
		// 라디오 버튼 추가하기
		for (int i = 0; i < 2; i++) {
			groupRd[i] = new ButtonGroup();
			if (i == 1)
				opButton[i] = new JRadioButton[4]; // 시럽 옵션 생성
			for (int j = 0; j < opButton[i].length; j++) {
				opButton[i][j] = new JRadioButton(opbtnStr[i][j]);
				opButton[i][j].setSelected(true); // 버튼의 선택 활성화
				option[i].add(opButton[i][j]);
				opButton[i][j].setBackground(Color.white);
				groupRd[i].add(opButton[i][j]);
			}

		}

		// 기타 추가사항 (체크박스)
		option[2].add(addOption[0]); // 샷추가
		option[2].add(addOption[1]); // 휘핑크림

		// 잔 수
		cupTxt = new JTextField("1"); // default값
		cupTxt.setHorizontalAlignment(JTextField.CENTER);
		cupTxt.setEditable(false); // 사용자가 텍스트를 수정할 수 없음
		cupTxt.setBounds(350, 320, 35, 30);
		frame.add(cupTxt);
		cupsLab = new JLabel("잔");
		cupsLab.setBounds(390, 320, 30, 30);
		frame.add(cupsLab);

		// +
		JButton plus = new JButton("+"); // 잔 수 증가하는 버튼
		plus.setBounds(250, 320, 40, 30);
		plus.setFont(new Font("null", Font.BOLD, 10));
		frame.add(plus);
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(cupTxt.getText()); // 현재 잔 수
				a++; // 현재 잔수 +1
				cupTxt.setText(a + "");
			}
		});
		// -
		JButton minus = new JButton("-"); // 잔 수 감소하는 버튼
		minus.setBounds(300, 320, 40, 30);
		minus.setFont(new Font("null", Font.BOLD, 12));
		frame.add(minus);
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!cupTxt.getText().equals("0")) { // 현재 잔 수가 0잔이 아니라면
					int a = Integer.parseInt(cupTxt.getText()); // 현재 잔수
					a--; // 현재 잔수 -1
					cupTxt.setText(a + "");
				}
			}
		});

		showBasket = new JButton("장바구니"); // 장바구니 버튼
		showBasket.setBounds(430, 320, 100, 30);
		showBasket.addActionListener(this);
		frame.add(showBasket);

		orderMenu = new JButton("추가"); // 장바구니에 주문하려는 음료 추가하는 버튼
		orderMenu.setBounds(540, 320, 70, 30);
		orderMenu.addActionListener(this);
		frame.add(orderMenu);

		// 로그아웃
		JButton back = new JButton("뒤로 가기"); // 뒤로가기 버튼 생성
		back.setBounds(30, 320, 120, 30);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton nowbtn = (JButton) e.getSource();
				if (nowbtn == back) {
					dispose();
					s.memCtrl.firstView(); // 첫 화면으로 이동
				}
			}

		});
		frame.add(back);

		setVisible(true);
		setBounds(200, 200, 650, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();
		if (btn == showMenu) { // 메뉴보기 버튼을 눌렸을 경우
			if (openMenu) { // 창 닫기
				s.pv.dispose();
				openMenu = false;
				showMenu.setText("메뉴보기");
			} else { // 메뉴 보기
				openMenu = true;
				s.priceView(openMenu);
				showMenu.setText("창닫기");

			}
		}
		// 장바구니에 추가하기
		if (btn == orderMenu) { // 추가 버튼을 눌렸을 경우
			if (menuChoice.getSelectedIndex() == 0) { // 메뉴를 선택하지 않은 경우
				JOptionPane.showMessageDialog(null, "메뉴를 선택해주세요!");
				return;
			}
			int menuNum = menuChoice.getSelectedIndex();
			String menuName = (String) menuChoice.getSelectedItem();
			String id = s.loginId;
			String cupSize = "";
			String syrup = "";
			int shot = 0;
			int whip = 0;
			int cups = Integer.parseInt(cupTxt.getText()); // 잔 수 int형으로
			int price = s.ordCtrl.getMenu().get(menuNum - 1).getPrice(); // db에서 선택한 메뉴 가격 가져오기
			System.out.println("cupTxt.getText():" + cupTxt.getText());
			System.out.println("cups: " + cups);
			for (int i = 0; i < opButton[0].length; i++) {
				if (opButton[0][i].isSelected()) {
					cupSize = opButton[0][i].getText();
					if (cupSize.equals("Tall")) {
						price += 500; // 사이즈가 Tall이면 가져온 가격에서 +500
					} else if (cupSize.equals("Grande")) {
						price += 1000; // 사이즈가 Grade이면 가져온 가격에서 +1000
					}
				}
			}

			for (int i = 0; i < opButton[1].length; i++) {
				if (opButton[1][i].isSelected()) {
					syrup = opButton[1][i].getText();
				}
			}

			// 샷추가 여부
			if (addOption[0].isSelected()) {
				shot++;
			}
			// 휘핑크림 추가?
			if (addOption[1].isSelected()) {
				whip++;
			}
			int total = price * cups; // 총 가격은 가격X 잔 수

			String str = " 주문한 메뉴 : " + menuChoice.getSelectedItem() + "\n" + " 사이즈 : " + cupSize + "\n";
			str += " 시럽 : " + syrup + "\n" + " 추가 샷 : " + shot + "\n" + " 휘핑크림 추가 : " + whip + "\n" + " 총 : " + cups
					+ " 잔\n" + " Total : " + total;
			int result = JOptionPane.showConfirmDialog(null, str);
			if (result == JOptionPane.YES_NO_OPTION) {
				OrderDto dto = new OrderDto();
				dto.setSequence(s.ordCtrl.getOrderCount()+1);
				dto.setId(id);
				dto.setMenuName(menuName);
				dto.setCupSize(cupSize);
				dto.setSyrup(syrup);
				dto.setShot(shot);
				dto.setWhip(whip);
				dto.setCups(cups);
				dto.setTotalPrice(total);
				dto.setoDate("");

				boolean b = s.ordCtrl.addBucket(dto); // 메뉴 선택 후 추가 버튼을 놀렸을 때
				if (b)
					JOptionPane.showMessageDialog(null, "메뉴 추가가 완료되었습니다!"); // yes버튼 눌렸을 때
				else
					JOptionPane.showMessageDialog(null, "실패!");
				// 초기화부분
				menuChoice.setSelectedIndex(0);
				addOption[0].setSelected(false);
				addOption[1].setSelected(false);
				cupTxt.setText("1");
				opButton[0][0].setSelected(true);
				opButton[1][0].setSelected(true);
				s.priceView(false);
			}
		}

		if (btn == showBasket) { // 장바구니 버튼을 눌렸을 때
			// 장바구니 보기
			if (openMenu)
				s.pv.dispose();

			s.ordCtrl.bucketView(); // 장바구니 창으로 넘어감
			dispose(); // 원래 창은 사라짐
		}

	}
}