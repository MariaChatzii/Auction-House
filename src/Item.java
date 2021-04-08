import java.util.ArrayList;

public class Item {
	public String code;
	public SellAction sellingData;
	public ArrayList<BidAction> bids = new ArrayList<>();

	public Item(String code, SellAction sellingData) {
		this.code = code;
		this.sellingData = sellingData;
	}

	public void addBid(BidAction bid) {
		bids.add(bid);
	}



	

}
