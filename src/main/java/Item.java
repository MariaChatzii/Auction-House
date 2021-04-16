import java.util.ArrayList;

public class Item {
	public static final String SOLD = "SOLD";
	public static final String UNSOLD = "UNSOLD";
	public static final int NO_WINNER_ID = -1;

	private final String code;
	private final SellAction sellingData;
	private ArrayList<BidAction> bids = new ArrayList<>();
	private final Result result;
	private int heartBeatMessage;

	public Item(String code, SellAction sellingData) {
		this.code = code;
		this.sellingData = sellingData;
		this.result = new Result();
	}

	public String getCode() {
		return code;
	}

	public SellAction getSellingData() {
		return sellingData;
	}

	public Result getResult() {
		return result;
	}

	public ArrayList<BidAction> getBids(){
		return bids;
	}

	public int getHeartBeatMessage() {
		return heartBeatMessage;
	}

	public void setBids(ArrayList<BidAction> bids){
		this.bids = bids;
	}

	public void setHeartBeatMessage(int heartBeatMessage) {
		this.heartBeatMessage = heartBeatMessage;
	}

	public void setResult(){
		result.setItemCode(code);
		if(bids.size()!=0) {
			bids.sort(BidAction.sortByPrice);
			removeInvalidTimeBids();

			result.setLowestBid(getLowestBidPrice());
			result.setHighestBid(getHighestBidPrice());
			result.setTotalBidCount(bids.size());

			//the winner bid price and the price paid must be valid, namely higher than the reserve_price)
			removeInvalidPriceBids();
			setResultData();
		}else {
			result.setHighestBid(0);
			result.setLowestBid(0);
			setNoWinnerData();
		}
	}

	public float getHighestBidPrice(){
		return bids.get(bids.size()-1).getPrice();
	}

	public float getLowestBidPrice(){
		return bids.get(0).getPrice();
	}

	public void removeInvalidTimeBids(){
		ArrayList<BidAction> bidsTemp = new ArrayList<>(bids);
		for (BidAction bid : bidsTemp) {
			if (!isWithinValidTime(bid.getTimestamp(), sellingData.getTimestamp(), sellingData.getCloseTime()))
				bids.remove(bid);
		}
	}

	public void removeInvalidPriceBids(){
		ArrayList<BidAction> bidsTemp = new ArrayList<>(bids);
		for (BidAction bid : bidsTemp) {
			if (bid.getPrice() >= sellingData.getPrice())
				bids.remove(bid);
		}
	}

	public void setNoWinnerData(){
		result.setWinnerId(NO_WINNER_ID);
		result.setStatus(UNSOLD);
		result.setPricePaid(0);
		result.setCloseTime(getResultCloseTime(0));
	}

	public void setResultData(){
		if(!bids.isEmpty()) { //There are valid bids as to time and price
			if(bids.size() == 1)
				result.setPricePaid(sellingData.getPrice());
			else
				result.setPricePaid(getPaidPrice());
			setWinnerData();
		}
		else { //There are not valid bids as to time and price
			setNoWinnerData();
		}
	}

	public float getPaidPrice(){
		int freqMaxPrice = totalBidsWithSameHighestPrice();
		if(freqMaxPrice == bids.size())
			//all bids have the same price
			return bids.get(0).getPrice();
		//N is the number of bids with the same highest price
		//The bid with the second highest price is located on position: size-N-1
		return bids.get(bids.size() - freqMaxPrice - 1).getPrice();
	}

	public void setWinnerData(){
		// Bids are already sorted by price and time
		// Thus, bid with the highest price and most recent time is located at the end of "bids" list.
		BidAction winnerBid = bids.get(bids.size()-1);

		result.setWinnerId(winnerBid.getUserId());
		result.setStatus(SOLD);
		result.setCloseTime(getResultCloseTime(winnerBid.getTimestamp()));
	}

	public int getResultCloseTime(int winnerBidTimestamp){
		return Math.max(heartBeatMessage, winnerBidTimestamp);
	}

	public static boolean isWithinValidTime(int timestampToCheck, int auctionStartedTime, int auctionClosedTime){
		return timestampToCheck > auctionStartedTime && timestampToCheck <= auctionClosedTime;
	}

	public int totalBidsWithSameHighestPrice() {
		return (int) bids.stream().filter(bid -> (bid.getPrice() == bids.get(bids.size()-1).getPrice())).count();
	}

	public void add(BidAction bidAction) {
		bids.add(bidAction);
	}

}
