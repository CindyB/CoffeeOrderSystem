package service;

import java.util.ArrayList;

import dto.MenuDto;
import dto.OrderDto;

public interface OrderService {
	
	public ArrayList<MenuDto> getMenu(); // 메뉴 불러오기

	public boolean addBucket(OrderDto dto); // 장바구니에 추가하기

	public void bucketView(); // 장바구니 뷰 열기

	public ArrayList<OrderDto> getAllOrder(String id); // 모든 주문 내역 가져오기

	public int getOrderCount(); // 주문 번호를 위한 카운트

	public boolean addOrder(OrderDto dto); // 주문 추가하기

	public ArrayList<MenuDto> getAllMenu(String id); // 모든 메뉴 내역 가져오기

	public int getMenuCount(); // 메뉴 번호를 위한 카운트

	public boolean addMenu(MenuDto dto); // 메뉴 추가하기

	public MenuDto deleteMenu(String name, int price); // 메뉴 삭제하기

	public int MonthPrice(String date); // 월별, 일별 매출

}
