import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InputFileReaderTest {
    ArrayList<Item> items1 = new ArrayList<>();
    ArrayList<Integer> heartBeatMessages1 = new ArrayList<>();

    @BeforeEach
    public void setup() {
        String filename = "input1.txt";
        InputStream in = new ByteArrayInputStream(filename.getBytes());
        System.setIn(in);

        initializing();
    }

    public void initializing(){
        Item item1 = new Item("wallet_1", new SellAction(10,1, (float) 10.06,20));
        ArrayList<BidAction> bids1 = new ArrayList<>();
        bids1.add(new BidAction(13,4,15));
        bids1.add(new BidAction(16,3, 9.50F));
        item1.setBids(bids1);
        item1.setHeartBeatMessage(17);

        Item item2 = new Item("bag_1", new SellAction(14,1, (float) 30.99,23));
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(18,3,20));
        bids2.add(new BidAction(19,5, 45));
        bids2.add(new BidAction(20,2, 45));
        bids2.add(new BidAction(21,7, 60));
        item2.setBids(bids2);
        item2.setHeartBeatMessage(22);

        items1.add(item1);
        items1.add(item2);
        heartBeatMessages1.add(12);
        heartBeatMessages1.add(15);
        heartBeatMessages1.add(17);
        heartBeatMessages1.add(22);
    }

    @Test
    void testReadActions(){
        InputFileReader inputFileReader = new InputFileReader();
        for(int i=0; i<items1.size(); i++) {
            checkResultEquals(inputFileReader.readActions(), items1.get(i), i);
        }
    }

    public void checkResultEquals(ArrayList<Item> resultItems, Item expectedItem, int pos){
        assertEquals(expectedItem.getCode(), resultItems.get(pos).getCode());
        assertEquals(expectedItem.getSellingData().getTimestamp(), resultItems.get(pos).getSellingData().getTimestamp());
        assertEquals(expectedItem.getSellingData().getUserId(), resultItems.get(pos).getSellingData().getUserId());
        assertEquals(expectedItem.getSellingData().getPrice(), resultItems.get(pos).getSellingData().getPrice());
        assertEquals(expectedItem.getSellingData().getCloseTime(), resultItems.get(pos).getSellingData().getCloseTime());
        assertEquals(expectedItem.getHeartBeatMessage(), resultItems.get(pos).getHeartBeatMessage());
        assertEquals(expectedItem.getBids(), resultItems.get(pos).getBids());
    }

    @Test
    void testGetInputFileFromUser(){
        InputFileReader inputFileReader = new InputFileReader();
        assertEquals("input1.txt", inputFileReader.getFilename());
    }

    @Test
    void testGetItemByCode(){
        InputFileReader inputFileReader = new InputFileReader();
        inputFileReader.setItemHeartBeatMessage(items1, heartBeatMessages1);
        assertEquals(items1.get(1), inputFileReader.getItemByCode(items1, "bag_1"));
        assertNull(inputFileReader.getItemByCode(items1, "bag_2"));
    }

}