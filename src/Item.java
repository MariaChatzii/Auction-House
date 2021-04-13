import java.util.ArrayList;
import java.util.Collections;

public class Item {
	public static final String SOLD = "SOLD";
	public static final String UNSOLD = "UNSOLD";
	public static final int NO_WINNER_ID = -1;

	private final String code;
	private final SellAction sellingData;
	private final ArrayList<BidAction> bids = new ArrayList<>();
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
		for(int i=0; i<bids.size();i++){
			if(!isWithinValidTime(bids.get(i).getTimestamp(), sellingData.getTimestamp(), sellingData.getCloseTime()))
				bids.remove(bids.get(i));
		}
	}

	public boolean areThereValidBids(){
		return !bids.isEmpty();
	}

	public void setNoWinnerData(){
		result.setWinnerId(NO_WINNER_ID);
		result.setStatus(UNSOLD);
		result.setPricePaid(0);
		result.setCloseTime(getResultCloseTime(0));
	}

	public void setResultData(){
		if(areThereValidBids()) {
			if (bids.size() == 1) {
				if(result.getPricePaid() >= sellingData.getPrice()) {
					result.setPricePaid(sellingData.getPrice());
					setWinnerData(0);
				}
			} else {
				float maxBidPrice  = bids.get(bids.size()-1).getPrice();
				if ((maxBidPrice ) >= sellingData.getPrice()){
					result.setPricePaid(getSecondMaxBidPrice());
					int maxBidPricePos = bids.size()-1;
					setWinnerData(maxBidPricePos);
				}else{
					setNoWinnerData();
				}
			}
		}
		else {
			setNoWinnerData();
		}
	}

	public float getSecondMaxBidPrice(){
		int freqMaxPrice = totalBidsWithSameHighestPrice();
		//N is the number of bids with the same highest price
		//The bid with the second highest price is located on position: size-N-1
		return bids.get(bids.size() - freqMaxPrice - 1).getPrice();
	}

	public void setWinnerData(int winnerPosition){
		BidAction winnerBid = bids.get(winnerPosition);

		result.setWinnerId(winnerBid.getUserId());
		result.setStatus(SOLD);
		result.setCloseTime(getResultCloseTime(winnerBid.getTimestamp()));
	}

	public int getResultCloseTime(int winnerBidTimestamp){
		return Math.max(heartBeatMessage, winnerBidTimestamp);
	}

	public boolean isWithinValidTime(int timestampToCheck, int auctionStartedTime, int auctionClosedTime){
		return timestampToCheck > auctionStartedTime && timestampToCheck <= auctionClosedTime;
	}

	public int totalBidsWithSameHighestPrice() {
		return Collections.frequency(bids, bids.get(bids.size() - 1));
	}


	public void add(BidAction bidAction) {
		bids.add(bidAction);
	}
}
