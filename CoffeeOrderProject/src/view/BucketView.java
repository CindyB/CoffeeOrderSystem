package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dto.OrderDto;
import singleton.Singleton;

public class BucketView extends JFrame implements ActionListener {
	Singleton s;
	private JLabel title;
	private final Object columnNames[] = { "Espresso Beverages", "시럽", "사이즈", "샷추가", "휘핑크림", "잔", "총액", "chk" };

	private Object rowData[][];
	private DefaultTableModel model; // 테이블의 넓이, 폭 등을 설정하기 위해 필요함
	private JTable jtable;
	private JScrollPane jscrPane;
	private ArrayList<OrderDto> list; // 장바구니 내역을 담을 리스트
	private JButton back;
	private JButton order, delete;
	private JCheckBox box;

	public BucketView() {
		super("BucketList");
		s = Singleton.getInstance();

		JPanel frame = new JPanel();
		frame.setBounds(0, 0, 640, 400);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setLayout(null);
		add(frame);

		title = new JLabel("장 바 구 니");
		title.setBounds(0, 10, 640, 30);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font(null, Font.BOLD, 30));
		frame.add(title);

		s = Singleton.getInstance();
		this.list = s.bucketList;
		rowData = new Object[list.size()][8];

		// list에서 테이블로 데이터를 삽입하기 위한 처리

		for (int i = 0; i < list.size(); i++) {
			OrderDto dto = list.get(i);

			rowData[i][0] = dto.getMenuName(); // 메뉴 이름
			rowData[i][1] = dto.getSyrup(); // 시럽
			rowData[i][2] = dto.getCupSize(); // 사이즈
			rowData[i][3] = dto.getShot(); // 샷추가
			rowData[i][4] = dto.getWhip(); // 휘핑크림
			rowData[i][5] = dto.getCups(); // 잔
			rowData[i][6] = dto.getTotalPrice();
			rowData[i][7] = false;
		}

		// Table 관련
		// 1. 테이블 폭을 설정하기 위한 Model
		model = new DefaultTableModel(rowData, columnNames) { // (폭,높이)
			@Override
			public boolean isCellEditable(int row, int column) { // 수정, 입력 불가
				return false;
			}
		};
		model = new DefaultTableModel(rowData, 0);
		model.setDataVector(rowData, columnNames); // (실제데이터:2차원배열, 범주)

		// Jtable
		jtable = new JTable(model);

		jtable.getColumn("chk").setCellRenderer(dcr);
		box = new JCheckBox();
		box.setHorizontalAlignment(JLabel.CENTER);
		jtable.getColumn("chk").setCellEditor(new DefaultCellEditor(box));

		// column의 폭을 설정
		jtable.getColumnModel().getColumn(0).setMaxWidth(200);// 메뉴 이름
		jtable.getColumnModel().getColumn(1).setMaxWidth(80);// 시럽
		jtable.getColumnModel().getColumn(2).setMaxWidth(80);// 사이즈
		jtable.getColumnModel().getColumn(3).setMaxWidth(50);// 샷추가
		jtable.getColumnModel().getColumn(4).setMaxWidth(60);// 휘핑크림
		jtable.getColumnModel().getColumn(5).setMaxWidth(50);// 잔
		jtable.getColumnModel().getColumn(6).setMaxWidth(100);// 총액
		jtable.getColumnModel().getColumn(7).setMaxWidth(40);//

		// 테이블 크기와 위치 설정
		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 600, 250);
		frame.add(jscrPane);

		DefaultTableCellRenderer dcr2 = new DefaultTableCellRenderer();
		dcr2.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm = jtable.getColumnModel();
		for (int j = 1; j < columnNames.length - 1; j++) {
			tcm.getColumn(j).setCellRenderer(dcr2);
		}

		// 뒤로가기 버튼
		back = new JButton("뒤로가기");
		back.setBounds(10, 310, 100, 40);
		back.addActionListener(this);
		frame.add(back);

		// 주문하기 버튼
		order = new JButton("주문하기");
		order.setBounds(510, 310, 100, 40);
		order.addActionListener(this);
		frame.add(order);

		// 삭제하기 버튼
		delete = new JButton("빼기");
		delete.setBounds(120, 310, 80, 40);
		delete.addActionListener(this);
		frame.add(delete);

		setVisible(true);
		setBounds(350, 200, 640, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		if (btn == back) {
			// 뒤로가기 버튼
			s.ordCtrl.orderView();
			dispose();
		}
		if (btn == order) {
			// 주문하기
			if (list.size() == 0) {
				// 장바구니 내역이 없을 때
				JOptionPane.showMessageDialog(null, "추가한 주문이 없습니다.", "Error", JOptionPane.PLAIN_MESSAGE);
				return;
			}

			// 체크한 것 주문하기
			int orderCount = countChkBox();
			if (orderCount == 0) {
				JOptionPane.showMessageDialog(null, "주문을 선택하세요!", "Error", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			// 박스값 확인하기

			int result = JOptionPane.showConfirmDialog(null, "선택한 " + orderCount + "건을 주문하시겠습니까?", "order",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				OrderDto dto;
				// 주문한 것만 장바구니에서 삭제하기
				int w = 0;
				for (int i = 0; i < jtable.getRowCount(); i++) {
					Boolean isChecked = Boolean.valueOf(jtable.getValueAt(i, 7).toString());

					if (isChecked) {
						dto = list.get(w);
						s.ordCtrl.addOrder(dto);
						s.bucketList.remove(w);
						w--;
					}
					w++;
				}
				JOptionPane.showMessageDialog(null, "주문을 완료했습니다!", "success", JOptionPane.PLAIN_MESSAGE);
				s.ordCtrl.orderView();
				dispose();
			}
		}

		// 체크한 것 장바구니에서 빼기
		if (btn == delete) {
			int orderCount = countChkBox();
			if (orderCount == 0) {
				JOptionPane.showMessageDialog(null, "삭제할 주문을 선택하세요!", "Error", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			int result = JOptionPane.showConfirmDialog(null, "선택한 " + orderCount + "건을 삭제하시겠습니까?", "order",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION) {
				return;
			}
			int w = 0;
			for (int i = 0; i < jtable.getRowCount(); i++) {
				Boolean isChecked = Boolean.valueOf(jtable.getValueAt(i, 7).toString());
				if (isChecked) {
					s.bucketList.remove(w);
					w--;
				}
				w++;
			}
			JOptionPane.showMessageDialog(null, "삭제했습니다!", "success", JOptionPane.PLAIN_MESSAGE);
			dispose();
			s.ordCtrl.bucketView();
		}
	}

	public int countChkBox() {
		int orderCount = 0;
		for (int i = 0; i < jtable.getRowCount(); i++) {
			Boolean isChecked = Boolean.valueOf(jtable.getValueAt(i, 7).toString());
			if (isChecked) {
				orderCount++;
			}
		}
		return orderCount;
	}

}
