import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.ArrayList;

public class InputFileReader {
	private static final int N_SELL_FIELDS = 4;
	private static final int N_BID_FIELDS = 3;
	private static final String SELL = "SELL";
	private static final String BID = "BID";

	public ArrayList<Item> readActions(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
			ArrayList<Item> itemsForSelling = new ArrayList<>();
			ArrayList<Integer> heartBeatMessages = new ArrayList<>();
			String line;
			while((line = reader.readLine())!=null) {
				String[] lineData;
				lineData = line.split("\\|");

				//Input lines with "SELL" status have 6 different characteristics
				//"SellAction" class store only the "N_SELL_FIELDS" of them
				if(lineData.length - 2 == N_SELL_FIELDS && lineData[2].equals(SELL)){ //Sell Action
					int timestamp = Integer.parseInt(lineData[0]);
					int userId = Integer.parseInt(lineData[1]);
				    String itemCode = lineData[3];
				    float reservePrice = Float.parseFloat(lineData[4]);
				    int closeTime = Integer.parseInt(lineData[5]);

				    itemsForSelling.add(new Item(itemCode, new SellAction(timestamp, userId, reservePrice, closeTime)));
				}
				//Input lines with "BID" status have 5 different characteristics
				//"SellAction" class store only the "N_BID_FIELDS" of them
				else if(lineData.length - 2 == N_BID_FIELDS && lineData[2].equals(BID)){ //Bid Action
					int timestamp = Integer.parseInt(lineData[0]);
					int userId = Integer.parseInt(lineData[1]);
					String itemCode = lineData[3];
					float reservePrice = Float.parseFloat(lineData[4]);

					getItemByCode(itemsForSelling, itemCode).add(new BidAction(timestamp, userId, reservePrice));
				}
				else{ //HeartBeat Messages
					heartBeatMessages.add(Integer.parseInt(lineData[0]));
				}
			}
			setItemHeartBeadMessage(itemsForSelling, heartBeatMessages);
			reader.close();
		    return itemsForSelling;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Item getItemByCode(@NotNull ArrayList<Item> items, String itemCode){
		for(Item item : items)
			if (item.getCode().equals(itemCode))
				return item;
		return null;
	}

	public void setItemHeartBeadMessage(ArrayList<Item> items, ArrayList<Integer> heartBreakMessages){
		//For each item appears throughout the auction, only the highest value of heartbreak message
		//(within the item valid time) is stored.
		for(Item item : items)
			for(Integer message : heartBreakMessages)
				if (Item.isWithinValidTime(message, item.getSellingData().getTimestamp(), item.getSellingData().getCloseTime()))
					item.setHeartBeatMessage(message);
	}
		
}
