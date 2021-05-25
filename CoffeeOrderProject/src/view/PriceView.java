package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.MenuDto;
import singleton.Singleton;

public class PriceView extends JFrame implements ActionListener {
	JLabel title;
	JPanel frame;
	private String columnNames[] = { "Espresso Beverages", "Short", "Tall", "Grande" };

	private Object rowData[][];
	private DefaultTableModel model; // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
	private JTable jtable;
	private JScrollPane jscrPane;
	private ArrayList<MenuDto> list;
	Singleton s;

	public PriceView() {
		super("메뉴 가격표");

		frame = new JPanel();
		frame.setBounds(0, 0, 640, 400);
		frame.setLocation(100, 200);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setLayout(null);
		add(frame);

		title = new JLabel("가 격 표");
		title.setBounds(0, 10, 640, 30);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font(null, Font.BOLD, 30));
		frame.add(title);

		s = Singleton.getInstance();
		this.list = s.ordCtrl.getMenu();
		rowData = new Object[list.size()][4];

		// list에서 테이블로 데이터를 삽입하기 위한 처리
		for (int i = 0; i < list.size(); i++) {
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
		jtable.getColumnModel().getColumn(0).setMaxWidth(300);// 메뉴 이름
		jtable.getColumnModel().getColumn(1).setMaxWidth(200);// 숏
		jtable.getColumnModel().getColumn(2).setMaxWidth(200);// 톨
		jtable.getColumnModel().getColumn(3).setMaxWidth(200);// 그란데

		// 테이블 크기와 위치 설정
		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 600, 300);
		frame.add(jscrPane);

		setVisible(false);
		setBounds(850, 200, 640, 400);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
