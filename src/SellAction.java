public class SellAction extends Action {
	private final int closeTime;

	public SellAction(int timestamp, int userId, float reservePrice, int closeTime) {
		super(timestamp, userId, reservePrice);
		this.closeTime = closeTime;
	}

	public int getCloseTime() {
		return closeTime;
	}
}
