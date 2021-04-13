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
}

