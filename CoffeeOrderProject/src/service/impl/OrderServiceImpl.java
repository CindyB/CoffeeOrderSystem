package service.impl;

import java.util.ArrayList;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import dto.MenuDto;
import dto.OrderDto;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
	OrderDao dao = new OrderDaoImpl();
	
	// 메뉴 불러오기
	@Override
	public ArrayList<MenuDto> getMenu() {
		return dao.getMenu();
	}

	// 장바구니에 추가하기
	@Override
	public boolean addBucket(OrderDto dto) {

		return dao.addBucket(dto);
	}
	
	 // 장바구니 뷰 열기
	@Override
	public void bucketView() {
		dao.bucketView();
	}

	// 모든 주문 내역 가져오기
	@Override
	public ArrayList<OrderDto> getAllOrder(String id) {
		return dao.getAllOrder(id);
	}
	
	// 주문 번호를 위한 카운트
	@Override
	public int getOrderCount() {
		return dao.getOrderCount();
	}
	
	// 주문 추가하기
	@Override
	public boolean addOrder(OrderDto dto) {
		return dao.addOrder(dto);
	}
	
	// 모든 메뉴 내역 가져오기
	
	@Override
	public ArrayList<MenuDto> getAllMenu(String id) {
		return dao.getAllMenu(id);
	}

	// 메뉴 번호를 위한 카운트
	@Override
	public int getMenuCount() {
		return dao.getMenuCount();
	}

	// 메뉴 추가하기
	@Override
	public boolean addMenu(MenuDto dto) {
		return dao.addMenu(dto);
	}
	
	// 메뉴 삭제하기
	@Override
	public MenuDto deleteMenu(String name, int price) {
		return dao.deleteMenu(name,price);
	}

	// 월별, 일별 매출
	@Override
	public int MonthPrice(String date) {
		return dao.MonthPrice(date);
	}
}
