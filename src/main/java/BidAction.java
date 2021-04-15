import java.util.Comparator;

public class BidAction extends Action{

	public BidAction(int timestamp, int userId, float bidPrice) {
		super(timestamp, userId, bidPrice);
	}

	public static Comparator<BidAction> sortByPrice = (bid1, bid2) -> {
		//sort in ascending order
		int result =  Float.compare(bid1.getPrice(), bid2.getPrice());
		if(result == 0)
			result =  Float.compare(bid1.getTimestamp(), bid2.getTimestamp());
		return result;
	};

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BidAction))
			return false;
		BidAction otherBid = (BidAction) obj;
		return((getTimestamp() == otherBid.getTimestamp() && getPrice() == otherBid.getPrice() && getUserId() == otherBid.getUserId()));
	}
}

