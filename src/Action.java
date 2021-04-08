
abstract public class Action {
    public int timestamp;
    public int userId;
    public float reservePrice;

    public Action(int timestamp, int userId, float reservePrice) {
		this.timestamp = timestamp;
		this.userId = userId;
		this.reservePrice = reservePrice;
	}
	
}
