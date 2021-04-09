
public class BidAction extends Action{

	public BidAction(int timestamp, int userId, float reservePrice) {
		super(timestamp, userId, reservePrice);
	}

	public boolean isWithinValidTime(int auctionStartedTime, int auctionClosedTime){
		if(timestamp > auctionStartedTime && timestamp <= auctionClosedTime)
			return true;
		return false;
	}
	
	
	

}
