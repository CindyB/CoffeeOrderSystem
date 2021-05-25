package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.OrderDao;
import db.DBConnection;
import dto.MenuDto;
import dto.OrderDto;
import singleton.Singleton;
import view.BucketView;

public class OrderDaoImpl implements OrderDao {
	int seq;

	// 메뉴 불러오기
	@Override
	public ArrayList<MenuDto> getMenu() {
		int count = 0;
		String sql = " SELECT MENUNUM, MENUNAME, PRICE " + " FROM COFFEEMENU " + " ORDER BY MENUNUM ASC ";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<MenuDto> list = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			// 쿼리 실행
			rs = psmt.executeQuery();
			list = new ArrayList<MenuDto>();

			while (rs.next()) {
				MenuDto dto = new MenuDto();
				dto.setSeq(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setPrice(rs.getInt(3));

				list.add(dto);
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 장바구니에 추가하기
	@Override
	public boolean addBucket(OrderDto dto) {
		Singleton s = Singleton.getInstance();
		if (dto != null) {
			s.bucketList.add(dto);
			return true;
		}
		return false;
	}

	// 장바구니 뷰 열기
	@Override
	public void bucketView() {
		new BucketView();
	}

	// 모든 주문 내역 가져오기
	@Override
	public ArrayList<OrderDto> getAllOrder(String id) {

		ArrayList<OrderDto> list = null;
		String sql = " SELECT SEQ, MENUNAME, CUPSIZE, SYRUP, SHOT, WHIP, CUPS, TOTAL, ODATE  " + " FROM C_ORDER "
				+ " WHERE ID = ? " + " ORDER BY SEQ DESC ";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			// 쿼리 실행
			rs = psmt.executeQuery();
			list = new ArrayList<OrderDto>();

			while (rs.next()) {
				OrderDto dto = new OrderDto();

				dto.setSequence(rs.getInt(1));
				dto.setMenuName(rs.getString(2));
				dto.setCupSize(rs.getString(3));
				dto.setSyrup(rs.getString(4));
				dto.setShot(rs.getInt(5));
				dto.setWhip(rs.getInt(6));
				dto.setCups(rs.getInt(7));
				dto.setTotalPrice(rs.getInt(8));
				dto.setoDate(rs.getString(9));

				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 주문 번호를 위한 카운트
	@Override
	public int getOrderCount() {
		int result = 0;
		String sql = "SELECT MAX(SEQ) FROM C_ORDER";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			// 쿼리 실행
			rs = psmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	// 주문 추가하기
	@Override
	public boolean addOrder(OrderDto dto) {

		String sql = " INSERT INTO C_ORDER ( SEQ, ID, MENUNAME, CUPSIZE, SYRUP, SHOT, WHIP, CUPS, TOTAL, ODATE ) "
				+ " VALUES ( ?, '2021', ?, ?, ?, ?, ?, ?, ?, NOW() ) ";

		int sequence = dto.getSequence();
		String id = dto.getId();
		String menuName = dto.getMenuName();
		String cupSize = dto.getCupSize();
		String syrup = dto.getSyrup();
		int shot = dto.getShot();
		int whip = dto.getWhip();
		int cups = dto.getCups();
		int total = dto.getTotalPrice();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		boolean b = false;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, sequence);
			psmt.setString(2, menuName);
			psmt.setString(3, cupSize);
			psmt.setString(4, syrup);
			psmt.setInt(5, shot);
			psmt.setInt(6, whip);
			psmt.setInt(7, cups);
			psmt.setInt(8, total);

			// 쿼리 실행
			psmt.executeUpdate();
			b = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	// 모든 메뉴 내역 가져오기
	@Override
	public ArrayList<MenuDto> getAllMenu(String id) {

		ArrayList<MenuDto> list = null;
		String sql = " SELECT MENUNUM, MENUNAME, PRICE " + " FROM COFFEEMENU " + " WHERE ID = ? "
				+ " ORDER BY MENUNUM ASC ";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			// 쿼리 실행
			rs = psmt.executeQuery();
			list = new ArrayList<MenuDto>();

			while (rs.next()) {
				MenuDto dto = new MenuDto();
				dto.setSeq(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setPrice(rs.getInt(3));

				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 메뉴 번호를 위한 카운트
	@Override
	public int getMenuCount() {
		int result = 0;
		String sql = "SELECT MAX(MENUNUM) FROM COFFEEMENU";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			// 쿼리 실행
			rs = psmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 메뉴 추가하기
	@Override
	public boolean addMenu(MenuDto dto) {

		String sql = " INSERT INTO COFFEEMENU ( MENUNUM, MENUNAME, PRICE, ID ) " + " VALUES ( ?, ?, ?, '2021' ) ";
		System.out.println("sql : " + sql);

		int count = 0;
		seq = dto.getSeq();
		String name = dto.getName();
		int price = dto.getPrice();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		boolean b = false;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, seq);
			psmt.setString(2, name);
			psmt.setInt(3, price);

			// 쿼리 실행하기
			psmt.executeUpdate();
			b = true;
			seq += 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			seq += 10;
		}
		return b;
	}

	// 메뉴 삭제하기
	@Override
	public MenuDto deleteMenu(String name, int price) {
		System.out.println(name + " : 이름" + price + " : 가격");
		int count = 0;
		String sql = "DELETE" + " FROM COFFEEMENU " + "WHERE MENUNAME = ? AND PRICE = ?";

		System.out.println("sql : " + sql);

		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean b = false;
		MenuDto deletemenu = null;
		try {
			conn = DBConnection.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			psmt.setInt(2, price);
			System.out.println(psmt + " 쿼리 값 저장");
			// 쿼리 실행하기
			psmt.executeUpdate();

			return deletemenu = new MenuDto(name, price);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deletemenu;
	}

	// 월별, 일별 매출
	public int MonthPrice(String date) {

		int result = 0;
		String sql = " SELECT SUM(TOTAL)" + " FROM C_ORDER " + " WHERE ODATE like ?";
		System.out.println("sql : " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, date); // date : 2021-01%

			// 쿼리 실행
			rs = psmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
