
abstract public class Action {
    private final int timestamp;
    private final int userId;
    private final float price;

    public Action(int timestamp, int userId, float price) {
		this.timestamp = timestamp;
		this.userId = userId;
		this.price = price;
	}

    public int getTimestamp() {
        return timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public float getPrice() {
        return price;
    }
}
