
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class InputFileReader {
	private static final String SELL = "SELL";
	private static final String BID = "BID";

	public ArrayList<Item> readActions(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Input.txt"));
			ArrayList<Item> itemsForSelling = new ArrayList<>();
			ArrayList<Integer> heartBeatMessages = new ArrayList<>();
			String line;
			while((line = reader.readLine())!=null) {
				String[] lineData;
				lineData = line.split("\\|");

				if(lineData.length - 2 == SellAction.class.getFields().length && lineData[2].equals(SELL)){ //Sell Action
					int timestamp = Integer.parseInt(lineData[0]);
					int userId = Integer.parseInt(lineData[1]);
				    String itemCode = lineData[3];
				    float reservePrice = Float.parseFloat(lineData[4]);
				    int closeTime = Integer.parseInt(lineData[5]);

				    itemsForSelling.add(new Item(itemCode, new SellAction(timestamp, userId, reservePrice, closeTime)));
				}
				else if(lineData.length - 1 == BidAction.class.getFields().length && lineData[2].equals(BID)){ //Bid Action
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
		    return itemsForSelling;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Item getItemByCode(@NotNull ArrayList<Item> items, String itemCode){
		for(Item item : items)
			if (item.code.equals(itemCode))
				return item;
		return null;
	}

	public void setItemHeartBeadMessage(ArrayList<Item> items, ArrayList<Integer> heartBreakMessages){
		for(Item item : items)
			for(Integer message : heartBreakMessages)
				if (item.isWithinValidTime(message, item.sellingData.timestamp, item.sellingData.closeTime))
					item.heartBeatMessage = message;
	}
		
}
