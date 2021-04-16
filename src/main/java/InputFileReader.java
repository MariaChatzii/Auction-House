import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFileReader {
	private static final int N_SELL_FIELDS = 4;
	private static final int N_BID_FIELDS = 3;
	private static final String SELL = "SELL";
	private static final String BID = "BID";

	private final String filename;

	public InputFileReader(){
		filename = getInputFileFromUser();
	}

	public String getFilename() {
		return filename;
	}

	public ArrayList<Item> readActions(){
		try {
			ArrayList<Item> itemsForSelling = new ArrayList<>();
			ArrayList<Integer> heartBeatMessages = new ArrayList<>();

			BufferedReader fileReader = new BufferedReader(new FileReader(filename));

			String line;
			while((line = fileReader.readLine())!=null) {
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

					getItemByCode(itemsForSelling, itemCode).getBids().add(new BidAction(timestamp, userId, reservePrice));
				}
				else{ //HeartBeat Messages
					heartBeatMessages.add(Integer.parseInt(lineData[0]));
				}
			}
			setItemHeartBeatMessage(itemsForSelling, heartBeatMessages);
			fileReader.close();
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

	public void setItemHeartBeatMessage(ArrayList<Item> items, ArrayList<Integer> heartBeatMessages){
		//For each item appears throughout the auction, only the highest value of heartbreak message
		//(within the item valid time) is stored.
		int maxHeartBeatMessage = 0;
		for(Item item : items) {
			for (Integer message : heartBeatMessages)
				if (Item.isWithinValidTime(message, item.getSellingData().getTimestamp(), item.getSellingData().getCloseTime()))
					maxHeartBeatMessage = Math.max(message, maxHeartBeatMessage);
			item.setHeartBeatMessage(maxHeartBeatMessage);
		}
	}

	public String getInputFileFromUser() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Give name of input file: ");
			String filename = sc.next();
			File file = new File(filename);

			if (file.exists())
				return filename;

			System.out.println("The file does not exist. Try again!");
		}
	}

}
