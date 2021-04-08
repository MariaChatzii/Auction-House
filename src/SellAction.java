public class SellAction extends Action {
	public int closeTime;

	public SellAction(int timestamp, int userId, float reservePrice, int closeTime) {
		super(timestamp, userId, reservePrice);
		this.closeTime = closeTime;
	}
}
