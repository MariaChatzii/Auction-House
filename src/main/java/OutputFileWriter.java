import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputFileWriter {
    public static final int NO_WINNER_ID = -1;

    public void writeResult(String inputFilename, ArrayList<Item> itemsForSell){
        try {
            String filenameWithoutExtension = inputFilename.substring(0, inputFilename.indexOf('.'));
            FileWriter writer = new FileWriter(filenameWithoutExtension + "Results.txt");
            for(Item item : itemsForSell) {
                Result result = item.getResult();
                writer.write(result.getCloseTime() + "|" + result.getItemCode()+ "|");
                if(result.getWinnerId() == NO_WINNER_ID){
                    writer.write("" + "|");
                }else{
                    writer.write(result.getWinnerId()+"|");
                }
                writer.write(result.getStatus() + "|" + String.format("%.02f", result.getPricePaid()) + "|" + result.getTotalBidCount() + "|" +
                        String.format("%.02f", result.getHighestBid()) + "|" + String.format("%.02f", result.getLowestBid()) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
