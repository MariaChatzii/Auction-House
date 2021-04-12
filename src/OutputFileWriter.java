import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputFileWriter {

    public static final int NO_WINNER_ID = -1;

    public void writeResult(ArrayList<Item> itemsForSell){
        try {
            FileWriter writer = new FileWriter(new File("auctionResult.txt"));
            for(Item item : itemsForSell) {
                Result result = item.result;
                writer.write(result.getCloseTime() + "|" + item.getCode()+ "|");
                if(result.getWinnerId() == NO_WINNER_ID){
                    writer.write("" + "|");
                }else{
                    writer.write(result.getWinnerId()+"|");
                }
                writer.write(result.getStatus() + "|" + result.getPricePaid() + "|" + result.getTotalBidCount() + "|" +
                        result.getHighestBid() + "|" + result.getLowestBid() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
