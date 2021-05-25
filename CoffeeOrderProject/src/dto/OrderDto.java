package dto;

public class OrderDto {

	private int sequence; // 주문번호
	private String id; // 주문아이디
	private String menuName; // 주문한 메뉴번호
	private String cupSize; // 사이즈
	private String syrup; // 시럽추가 몇개
	private int shot; // 샷추가 몇개
	private int whip; // 휘핑크림 추가
	private int cups; // 잔 수
	private int totalPrice; // 총 금액
	private String oDate; // 날짜

	public OrderDto() {
		// TODO Auto-generated constructor stub
	}

	public OrderDto(int sequence, String id, String menuName, String cupSize, String syrup, int shot, int whip,
			int cups, int totalPrice, String oDate) {
		super();
		this.sequence = sequence;
		this.id = id;
		this.menuName = menuName;
		this.cupSize = cupSize;
		this.syrup = syrup;
		this.shot = shot;
		this.whip = whip;
		this.cups = cups;
		this.totalPrice = totalPrice;
		this.oDate = oDate;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCupSize() {
		return cupSize;
	}

	public void setCupSize(String cupSize) {
		this.cupSize = cupSize;
	}

	public String getSyrup() {
		return syrup;
	}

	public void setSyrup(String syrup) {
		this.syrup = syrup;
	}

	public int getShot() {
		return shot;
	}

	public void setShot(int shot) {
		this.shot = shot;
	}

	public int getWhip() {
		return whip;
	}

	public void setWhip(int whip) {
		this.whip = whip;
	}

	public int getCups() {
		return cups;
	}

	public void setCups(int cups) {
		this.cups = cups;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getoDate() {
		return oDate;
	}

	public void setoDate(String oDate) {
		this.oDate = oDate;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String toString() {
		return "OrderDto [sequence=" + sequence + ", id=" + id + ", menuName=" + menuName + ", cupSize=" + cupSize
				+ ", syrup=" + syrup + ", shot=" + shot + ", whip=" + whip + ", cups=" + cups + ", totalPrice="
				+ totalPrice + ", oDate=" + oDate + "]";
	}

}
