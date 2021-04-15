import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OutputFileWriterTest {
    ArrayList<Item> items1 = new ArrayList<>();
    ArrayList<Integer> heartBeatMessages1 = new ArrayList<>();

    @Test
    void testWriteResult() throws IOException {
        initializing();

        OutputFileWriter outputWriter = new OutputFileWriter();
        outputWriter.writeResult("testFile1.txt", items1);

        File testFile1 = new File("testFile1Results.txt");
        assertTrue(hasSameContent(testFile1, new File("testFile1Expected.txt")));
    }

    public void initializing(){
        Item item1 = new Item("wallet_1", new SellAction(10,1, (float) 10.06,20));
        ArrayList<BidAction> bids1 = new ArrayList<>();
        bids1.add(new BidAction(13,4,15));
        bids1.add(new BidAction(16,3, 9.50F));
        item1.setBids(bids1);
        item1.setHeartBeatMessage(17);
        item1.setResult();

        Item item2 = new Item("bag_1", new SellAction(14,1, (float) 30.99,23));
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(18,3,20));
        bids2.add(new BidAction(19,5, 45));
        bids2.add(new BidAction(20,2, 45));
        bids2.add(new BidAction(21,7, 60));
        item2.setBids(bids2);
        item2.setHeartBeatMessage(22);
        item2.setResult();

        items1.add(item1);
        items1.add(item2);
        heartBeatMessages1.add(12);
        heartBeatMessages1.add(15);
        heartBeatMessages1.add(17);
        heartBeatMessages1.add(22);
    }

    public boolean hasSameContent(File testFile, File expectedFile) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(testFile));
        BufferedReader reader2 = new BufferedReader(new FileReader(expectedFile));

        String lineFile1;
        String lineFile2;

        while(true){
            lineFile1 = reader1.readLine();
            lineFile2 = reader2.readLine();
            if(lineFile1 != null && lineFile2 != null)
                return true;
            if(lineFile1.equals(lineFile2))
                return false;
        }
    }
}