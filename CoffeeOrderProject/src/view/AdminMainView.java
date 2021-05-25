package view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dto.MenuDto;
import dto.OrderDto;
import singleton.Singleton;

import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdminMainView extends JFrame { // 관리자 메인 화면

	JFrame frame; // 메인 프레임
	JTabbedPane tab; // 탭

	JMenuBar bar;
	JMenu systemMenu;
	JMenuItem logoutItem, refresh; // 로그아웃, 새로고침 메뉴

	Singleton s;

	OrderHistoryPanel orderPanel; // 탭에 붙일 각 패널들
	monthTotalPanel monthPanel;
	dayTotalPanel dayPanel;
	MenuPanel menuPanel;

	public AdminMainView() {
		super("AdminMain");
		s = Singleton.getInstance();
		// 메인 프레임

		frame = new JFrame("AdminMain");
		tab = new JTabbedPane();

		menuPanel = new MenuPanel();
		orderPanel = new OrderHistoryPanel();
		monthPanel = new monthTotalPanel();
		dayPanel = new dayTotalPanel();
		tab.add("전체 주문내역", orderPanel);
		tab.add("월별 매출", monthPanel);
		tab.add("일별 매출", dayPanel);
		tab.add("메뉴 조회", menuPanel);

		createMenubar();
		frame.setLocation(300, 200); // 창의 위치 결정
		frame.setSize(660, 550); // 창의 크기
		frame.add(tab);
		frame.setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void createMenubar() { // 메뉴바 생성함수
		bar = new JMenuBar();
		systemMenu = new JMenu("System");

		logoutItem = new JMenuItem("Logout");
		refresh = new JMenuItem("새로고침");
		systemMenu.add(logoutItem); // 로그아웃 추가
		systemMenu.add(refresh); // 새로고침 추가
		bar.add(systemMenu);
		frame.setJMenuBar(bar); // 프레임에 메뉴바 설정

		logoutItem.addActionListener(new ActionListener() { // 로그아웃 기능
			@Override
			public void actionPerformed(ActionEvent e) { // 로그아웃 이벤트 발생 시

				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "logout", JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.YES_OPTION) { // 메세지 답변이 yse일 경우
					s.setLoginDto(null); // MemberDto에 null값을 줘서 초기화한다.
					s.memCtrl.loginView(); // 로그인창으로 돌아간다
					frame.dispose(); // 현재 창(관리자 뷰)는 지운다
				}
			}
		});

		refresh.addActionListener(new ActionListener() { // 새로고침 기능
			@Override
			public void actionPerformed(ActionEvent e) { // 새로고침 이벤트 발생 시

				if (tab.getSelectedIndex() == 3) { // 현재 선택된 탭이 3번째(메뉴조회)항목일 때 발생
					tab.remove(3); // 메뉴조회 탭을 지웠다가
					menuPanel = new MenuPanel(); // 수정된 메뉴를 다시 조회 후
					tab.add("메뉴 조회", menuPanel); // 탭 추가
				}
			}
		});
	}
}

class OrderHistoryPanel extends JPanel {

	JPanel jp1;

	private JTable jtable;
	private JScrollPane jscrPane; // 테이블의 스크롤 기능
	private DefaultTableModel model; // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
	private Object rowData[][]; // 데이터를 담을 변수
	private ArrayList<OrderDto> list;
	private String columnNames[] = { "Espresso Beverages", "주문날짜", "사이즈", "잔", "총액" }; // 컬럼
	Singleton s;

	public OrderHistoryPanel() {

		s = Singleton.getInstance();

		this.setLayout(null);
		jp1 = new JPanel();
		jp1.setBounds(0, 0, 660, 500);
		jp1.setBackground(Color.white);
		this.add(jp1);

		this.list = s.ordCtrl.getAllOrder(s.loginId); // 현재 로그인된 관리자의 전체 주문내역을 받아온다.
		rowData = new Object[list.size()][5];

		for (int i = 0; i < list.size(); i++) { // list에서 테이블로 데이터를 삽입하기 위한 처리
			OrderDto dto = list.get(i);
			rowData[i][0] = dto.getMenuName(); // 메뉴 이름
			rowData[i][1] = dto.getoDate(); // 주문날짜
			rowData[i][2] = dto.getCupSize(); // 사이즈
			rowData[i][3] = dto.getCups(); // 잔수
			rowData[i][4] = dto.getTotalPrice();// 총액
		}

		// Table 관련
		// 1. 테이블 폭을 설정하기 위한 Model
		model = new DefaultTableModel(columnNames, 0) { // (폭,높이)
			@Override
			public boolean isCellEditable(int row, int column) { // 수정, 입력 불가
				return false;
			}
		};
		model.setDataVector(rowData, columnNames); // (실제데이터:2차원배열, 범주)

		// Jtable
		jtable = new JTable(model);

		// column의 폭을 설정
		jtable.getColumnModel().getColumn(0).setMaxWidth(200); // 메뉴 이름
		jtable.getColumnModel().getColumn(1).setMaxWidth(150); // 주문날짜
		jtable.getColumnModel().getColumn(2).setMaxWidth(80); // 사이즈
		jtable.getColumnModel().getColumn(3).setMaxWidth(80); // 잔수
		jtable.getColumnModel().getColumn(4).setMaxWidth(100); // 총액

		// 테이블 크기와 위치 설정
		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 660, 500);
		jp1.add(jscrPane); // 패널에 붙이기

	}
}

class monthTotalPanel extends JPanel {

	JPanel jpm; // 메인 패널
	JPanel combo; // 콤보박스를 위한 패널
	JComboBox<String> cyear, cmonth, cday;
	JLabel jl1;
	JLabel jl2;
	JLabel sumLabel; // 매출 총액 라벨
	JButton js; // 매출 조회 버튼

	String selected_year, selected_month; // 선택된 년도, 월을 담을 변수
	String date; // 쿼리문에 전달할 변수
	int result; // 월 매출
	ArrayList<String> yearList;
	ArrayList<String> monthList;

	Singleton s;
	Calendar ci = Calendar.getInstance();

	public monthTotalPanel() {

		this.setLayout(null);

		jpm = new JPanel(); // 메인 패널
		jpm.setLayout(null);
		jpm.setBounds(30, 20, 580, 200);
		jpm.setBackground(Color.white);
		jpm.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 2), " 월별 매출 "));
		this.add(jpm);

		jl1 = new JLabel("『날짜 선택』");
		jl1.setFont(new Font("null", Font.BOLD, 15));
		jl1.setBounds(60, 50, 100, 40);
		jpm.add(jl1);

		jl2 = new JLabel("『매출 총 액』 ");
		jl2.setFont(new Font("null", Font.BOLD, 15));
		jl2.setBounds(60, 130, 110, 50);
		jpm.add(jl2);

		js = new JButton("매출 조회");
		js.setBounds(350, 53, 100, 30);
		jpm.add(js);

		sumLabel = new JLabel(); // 매출 총액 라벨
		sumLabel.setFont(new Font("null", Font.BOLD, 15));
		sumLabel.setBounds(200, 80, 150, 150);
		jpm.add(sumLabel);

		int toyear = ci.get(Calendar.YEAR) + 1; // 현재 년도 받아오기
		int tomonth = ci.get(Calendar.MONTH) + 1; // 현재 월 받아오기

		yearList = new ArrayList<String>();
		for (int j = toyear; j >= toyear - 10; j--) { // 최근 10년
			if (j == toyear) {
				yearList.add("--"); // 년도 기본 값 --
			} else {
				yearList.add(String.valueOf(j));
			}
		}
		cyear = new JComboBox<String>(yearList.toArray(new String[yearList.size()]));
		cyear.setBounds(5, 5, 70, 30);
		cyear.setSelectedItem(String.valueOf(toyear));
		cyear.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) { // 년도 콤보박스 선택 시
					selected_year = cyear.getSelectedItem().toString();
				}
			}
		});

		monthList = new ArrayList<String>();
		for (int i = 0; i <= 12; i++) {
			if (i == 0) { // 월 기본값 --
				monthList.add("--");
			} else {
				monthList.add(addZeroString(i));
			}
		}
		cmonth = new JComboBox<String>(monthList.toArray(new String[monthList.size()]));
		cmonth.setBounds(80, 5, 70, 30);
		cmonth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) { // 월 콤보박스 선택 시
					selected_month = cmonth.getSelectedItem().toString();
				}
			}
		});

		combo = new JPanel(); // 콤보박스 패널
		combo.add(cyear);
		combo.add(cmonth);
		combo.setBackground(Color.white);
		combo.setBounds(160, 50, 200, 60);
		jpm.add(combo);

		js.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date = selected_year + "-" + selected_month + "%"; // 쿼리문에 넣을 조건문
				s = Singleton.getInstance();
				result = s.ordCtrl.MonthPrice(date);
				sumLabel.setText(Integer.toString(result) + "원"); // 출력
				System.out.println("선택된 날짜 : " + date);
				System.out.println("매출 총액 : " + result);
			}
		});
	}

	private String addZeroString(int k) { // 1~9월을 01~09월로 맞춰주기 위해 + 정수형->String형으로 변환
		String value = Integer.toString(k);

		if (value.length() == 1) {
			value = "0" + value;
		} else
			value = value;

		return value;
	}
}

class dayTotalPanel extends JPanel {

	JPanel jpd; // 메인 패널
	JPanel combo; // 콤보박스를 위한 패널
	JComboBox<String> cyear, cmonth, cday;
	JLabel jl1;
	JLabel jl2;
	JLabel sumLabel; // 매출 총액 라벨
	JButton js; // 매출 조회 버튼

	String selected_year, selected_month, selected_day; // 선택된 년도, 월, 일을 담을 변수
	String date; // 쿼리문에 전달할 변수
	int result; // 일 매출
	ArrayList<String> yearList;
	ArrayList<String> monthList;
	ArrayList<String> dayList;

	Calendar ci = Calendar.getInstance();
	Singleton s;

	public dayTotalPanel() {

		this.setLayout(null);

		jpd = new JPanel();
		jpd.setLayout(null);
		jpd.setBounds(30, 20, 580, 200);
		jpd.setBackground(Color.white);
		jpd.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 2), " 일별 매출 "));
		this.add(jpd);

		jl1 = new JLabel("『날짜 선택』");
		jl1.setFont(new Font("null", Font.BOLD, 15));
		jl1.setBounds(60, 50, 100, 40);
		jpd.add(jl1);

		jl2 = new JLabel("『매출 총 액』 ");
		jl2.setFont(new Font("null", Font.BOLD, 15));
		jl2.setBounds(60, 130, 110, 50);
		jpd.add(jl2);

		js = new JButton("매출 조회");
		js.setFont(new Font("null", Font.BOLD, 15));
		js.setBounds(370, 53, 100, 30);
		jpd.add(js);

		sumLabel = new JLabel("");
		sumLabel.setFont(new Font("null", Font.BOLD, 15));
		sumLabel.setBounds(200, 80, 150, 150);
		jpd.add(sumLabel);

		int toyear = ci.get(Calendar.YEAR) + 1;
		int tomonth = ci.get(Calendar.MONTH) + 1;
		int today = ci.get(Calendar.DAY_OF_MONTH);

		yearList = new ArrayList<String>();
		for (int j = toyear; j >= toyear - 10; j--) {
			if (j == toyear) {
				yearList.add("--");
			} else {
				yearList.add(String.valueOf(j));
			}
		}
		cyear = new JComboBox<String>(yearList.toArray(new String[yearList.size() + 1]));
		cyear.setBounds(5, 5, 70, 30);
		cyear.setSelectedItem(String.valueOf(toyear));
		cyear.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) { // 선택된 년 구하기
					selected_year = cyear.getSelectedItem().toString();
					System.out.println(Integer.parseInt(selected_year));
				}
			}
		});

		monthList = new ArrayList<String>();
		for (int i = 0; i <= 12; i++) {
			if (i == 0) {
				monthList.add("--"); // 기본값 --
			} else {
				monthList.add(addZeroString(i));
			}
		}
		cmonth = new JComboBox<String>(monthList.toArray(new String[monthList.size()]));
		cmonth.setBounds(80, 5, 70, 30);
		cmonth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) { // 선택된 월 구하기
					selected_month = cmonth.getSelectedItem().toString();
					System.out.println(Integer.parseInt(selected_month));
				}
			}
		});

		dayList = new ArrayList<String>();
		for (int k = 0; k <= 31; k++) {
			if (k == 0) {
				dayList.add("--");
			} else {
				dayList.add(addZeroString(k));
			}
		}
		cday = new JComboBox<String>(dayList.toArray(new String[dayList.size()]));
		cday.setBounds(160, 5, 70, 30);
		cday.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) { // 선택된 날 구하기
					selected_day = cday.getSelectedItem().toString();
					System.out.println(Integer.parseInt(selected_day));
				}
			}
		});

		combo = new JPanel();
		combo.add(cyear);
		combo.add(cmonth);
		combo.add(cday);
		combo.setBackground(Color.white);
		combo.setBounds(160, 50, 200, 60);
		jpd.add(combo);

		js.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				date = selected_year + "-" + selected_month + "-" + selected_day + "%";
				s = Singleton.getInstance();
				result = s.ordCtrl.MonthPrice(date);
				sumLabel.setText(Integer.toString(result) + "원");
				System.out.println("선택된 날짜 : " + date);
				System.out.println("매출 총액 : " + result);
			}
		});
	}

	private String addZeroString(int k) { // 1~9월을 01~09월로 만들기 위한 함수
		String value = Integer.toString(k);

		if (value.length() == 1) {
			value = "0" + value;
		} else
			value = value;

		return value;
	}
}

class MenuPanel extends JPanel {

	JPanel jp1; // 메인 패널
	JButton addBtn; // 메뉴 추가 버튼
	JButton deleteBtn; // 메뉴 삭제 버튼

	JTable jtable;
	JScrollPane jscrPane; // 테이블 스크롤
	DefaultTableModel model; // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
	private Object rowData[][];
	private String columnNames[] = { "Espresso Beverages", "Short", "Tall", "Grande" };

	Singleton s;
	private ArrayList<MenuDto> list;

	public MenuPanel() {
		this.setLayout(null);

		jp1 = new JPanel();
		jp1.setBounds(0, 0, 660, 500);
		jp1.setBackground(Color.white);
		this.add(jp1);

		s = Singleton.getInstance();
		this.list = s.ordCtrl.getAllMenu(s.loginId);
		rowData = new Object[list.size()][4];

		for (int i = 0; i < list.size(); i++) { // list에서 테이블로 데이터를 삽입하기 위한 처리
			MenuDto dto = list.get(i);
			rowData[i][0] = dto.getName(); // 메뉴 이름
			rowData[i][1] = dto.getPrice(); // 숏 가격
			rowData[i][2] = dto.getPrice() + 500; // 톨 가격
			rowData[i][3] = dto.getPrice() + 1000; // 그란데 가격
		}

		// Table 관련
		// 1. 테이블 폭을 설정하기 위한 Model
		model = new DefaultTableModel(columnNames, 0) { // (폭,높이)
			@Override
			public boolean isCellEditable(int row, int column) { // 수정, 입력 불가
				return false;
			}
		};
		model.setDataVector(rowData, columnNames); // (실제데이터:2차원배열, 범주)

		// Jtable
		jtable = new JTable(model);

		// column의 폭을 설정
		jtable.getColumnModel().getColumn(0).setMaxWidth(300); // 메뉴 이름
		jtable.getColumnModel().getColumn(1).setMaxWidth(200); // 숏
		jtable.getColumnModel().getColumn(2).setMaxWidth(200); // 톨
		jtable.getColumnModel().getColumn(3).setMaxWidth(200); // 그란데

		// 테이블 크기와 위치 설정
		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 660, 500);
		jp1.add(jscrPane); // 패널에 붙이기
		addMenu();
		deleteMenu();
	}

	private void addMenu() { // 메뉴 추가
		addBtn = new JButton("추가");
		addBtn.setBounds(550, 0, 70, 30);
		addBtn.setFont(new Font("null", Font.BOLD, 10));
		jp1.add(addBtn);

		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.memCtrl.adminMenuAdd(); // 메뉴 추가 관리
			}
		});
	}

	private void deleteMenu() { // 메뉴 삭제
		deleteBtn = new JButton("삭제");
		deleteBtn.setFont(new Font("null", Font.BOLD, 10));
		jp1.add(deleteBtn);

		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.memCtrl.adminMenuDelete(); // 메뉴 추가 관리
			}
		});
	}

}
