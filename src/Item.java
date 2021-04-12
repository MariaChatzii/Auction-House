import java.util.ArrayList;
import java.util.Collections;

public class Item {
	public static final String SOLD = "SOLD";
	public static final String UNSOLD = "UNSOLD";
	public static final int NO_WINNER_ID = -1;

	public String code;
	public SellAction sellingData;
	public ArrayList<BidAction> bids = new ArrayList<>();
	public Result result;
	public int heartBeatMessage;

	public Item(String code, SellAction sellingData) {
		this.code = code;
		this.sellingData = sellingData;
		this.result = new Result();
	}

	public float getHighestBidPrice(){
		return bids.get(bids.size()-1).price;
	}

	public float getLowestBidPrice(){
		return bids.get(0).price;
	}
	public void setResult(){
		result.setItemCode(code);
		if(bids.size()!=0) {
			System.out.println("bids size = "+bids.size());
			Collections.sort(bids, BidAction.sortByPrice);
			for(int i=0;i<bids.size();i++){
				System.out.println(bids.get(i).price);
			}
			removeInvalidTimeBids();
			result.setLowestBid(getHighestBidPrice());
			result.setHighestBid(getLowestBidPrice());
			result.setTotalBidCount(bids.size());
			setResultData();
		}else {
			result.setHighestBid(0);
			result.setLowestBid(0);
			setNoWinnerData();
		}
	}

	public void removeInvalidTimeBids(){
		for(int i=0; i<bids.size();i++){
			if(!isWithinValidTime(bids.get(i).timestamp, sellingData.timestamp, sellingData.closeTime))
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
	}

	public void setResultData(){
		if(areThereValidBids()) {
			if (bids.size() == 1) {
				if(result.getPricePaid() >= sellingData.price) {
					result.setPricePaid(sellingData.price);
					setWinnerData(0);
				}
			} else {
				float maxBidPrice  = bids.get(bids.size()-1).price;
				if ((maxBidPrice ) >= sellingData.price){
					result.setPricePaid(getSecondMaxBidPrice(maxBidPrice));
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

	public float getSecondMaxBidPrice(float maxBidPrice){
		int freqMaxPrice = totalBidsWithSameHighestPrice();
		//N is the number of bids with the same highest price
		//The bid with the second highest price is located on position: size-N-1
		return bids.get(bids.size() - freqMaxPrice - 1).price;
	}

	public void setWinnerData(int winnerPosition){
		BidAction winnerBid = bids.get(winnerPosition);

		result.setWinnerId(winnerBid.userId);
		result.setStatus(SOLD);
		result.setCloseTime(getResultCloseTime(winnerBid.timestamp));
	}

	public int getResultCloseTime(int winnerBidTimestamp){
		if(heartBeatMessage > winnerBidTimestamp)
			return heartBeatMessage;
		return winnerBidTimestamp;
	}

	public boolean isWithinValidTime(int timestampToCheck, int auctionStartedTime, int auctionClosedTime){
		if(timestampToCheck > auctionStartedTime && timestampToCheck <= auctionClosedTime)
			return true;
		return false;
	}

	public int totalBidsWithSameHighestPrice() {
		return Collections.frequency(bids, bids.get(bids.size() - 1));
	}


	public void add(BidAction bidAction) {
		bids.add(bidAction);
	}
}
