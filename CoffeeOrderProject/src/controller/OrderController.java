package controller;

import java.util.ArrayList;

import dto.MenuDto;
import dto.OrderDto;
import service.OrderService;
import service.impl.OrderServiceImpl;
import view.OrderView;
import view.PriceView;

public class OrderController {
	OrderService ordService = new OrderServiceImpl();

	// 주문창 열기
	public void orderView() {
		new OrderView();
	}

	// 메뉴판 뷰 열기
	public void priceView() {
		new PriceView();
	}

	// 장바구니 뷰 열기
	public void bucketView() {
		ordService.bucketView();
	}

	// 메뉴 불러오기
	public ArrayList<MenuDto> getMenu() {
		return ordService.getMenu();
	}

	// 장바구니에 주문 추가하기
	public boolean addBucket(OrderDto dto) {
		return ordService.addBucket(dto);
	}

	// 전체 주문내역 가져오기
	public ArrayList<OrderDto> getAllOrder(String id) {
		return ordService.getAllOrder(id);
	}

	// 주문 번호를 위한 카운트
	public int getOrderCount() {
		return ordService.getOrderCount();
	}

	// 주문 추가하기
	public boolean addOrder(OrderDto dto) {
		return ordService.addOrder(dto);
	}

	// 전체 메뉴내역 가져오기
	public ArrayList<MenuDto> getAllMenu(String id) {
		return ordService.getAllMenu(id);
	}

	// 메뉴 번호를 위한 카운트
	public int getMenuCount() {
		// TODO Auto-generated method stub
		return ordService.getMenuCount();
	}

	// 메뉴 추가하기
	public boolean addMenu(MenuDto dto) {
		return ordService.addMenu(dto);
	}

	// 메뉴 삭제하기
	public MenuDto deleteMenu(String name, int price) {
		// TODO Auto-generated method stub
		return ordService.deleteMenu(name, price);
	}

	// 월별/일별 매출
	public int MonthPrice(String date) {
		return ordService.MonthPrice(date);
	}

}
