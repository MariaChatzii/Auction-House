
abstract public class Action {
    public int timestamp;
    public int userId;
    public float price;

    public Action(int timestamp, int userId, float price) {
		this.timestamp = timestamp;
		this.userId = userId;
		this.price = price;
	}
	
}
