import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

public class BidAction extends Action{

	public BidAction(int timestamp, int userId, float bidPrice) {
		super(timestamp, userId, bidPrice);
	}

	public static Comparator<BidAction> sortByPrice = new Comparator<BidAction>() {

		@Override
		public int compare(BidAction bid1, BidAction bid2) {
			//sort in ascending order
			int result =  Float.compare(bid1.price, bid2.price);
			if(result == 0)
				result =  Float.compare(bid1.timestamp, bid2.timestamp);
			return result;
		}
	};
}

