import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InputFileReaderTest {
    ArrayList<Item> itemsTest = new ArrayList<>();
    ArrayList<Integer> timestampsTest = new ArrayList<>();

    @BeforeEach
    public void setup() {
        String testFilename = "input1.txt";
        InputStream in = new ByteArrayInputStream(testFilename.getBytes());
        System.setIn(in);

        initializing();
    }

    public void initializing(){
        Item item1 = new Item("wallet_1", new SellAction(10,1, (float) 10.06,20));
        ArrayList<BidAction> bids1 = new ArrayList<>();
        bids1.add(new BidAction(13,4,15));
        bids1.add(new BidAction(16,3, 9.50F));
        item1.setBids(bids1);

        Item item2 = new Item("bag_1", new SellAction(14,1, (float) 30.99,23));
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(18,3,20));
        bids2.add(new BidAction(19,5, 45));
        bids2.add(new BidAction(20,2, 45));
        bids2.add(new BidAction(21,7, 60));
        item2.setBids(bids2);

        itemsTest.add(item1);
        itemsTest.add(item2);
        timestampsTest.add(12);
        timestampsTest.add(13);
        timestampsTest.add(14);
        timestampsTest.add(15);
        timestampsTest.add(16);
        timestampsTest.add(18);
        timestampsTest.add(19);
        timestampsTest.add(20);
        timestampsTest.add(21);
        timestampsTest.add(22);

        item1.getResult().setCloseTime(20);
        item2.getResult().setCloseTime(22);
    }

    @Test
    void testReadActions(){
        InputFileReader inputFileReader = new InputFileReader();
        for(int i = 0; i< itemsTest.size(); i++)
            checkResultEquals(inputFileReader.readActions(), itemsTest.get(i), i);
    }

    public void checkResultEquals(ArrayList<Item> resultItems, Item expectedItem, int pos){
        assertEquals(expectedItem.getCode(), resultItems.get(pos).getCode());
        assertEquals(expectedItem.getSellingData().getTimestamp(), resultItems.get(pos).getSellingData().getTimestamp());
        assertEquals(expectedItem.getSellingData().getUserId(), resultItems.get(pos).getSellingData().getUserId());
        assertEquals(expectedItem.getSellingData().getPrice(), resultItems.get(pos).getSellingData().getPrice());
        assertEquals(expectedItem.getSellingData().getCloseTime(), resultItems.get(pos).getSellingData().getCloseTime());
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
        assertEquals(itemsTest.get(1), inputFileReader.getItemByCode(itemsTest, "bag_1"));
        assertNull(inputFileReader.getItemByCode(itemsTest, "bag_2"));
    }

    @Test
    void testSetItemMaxTimestamp(){
        InputFileReader inputFileReader = new InputFileReader();
        ArrayList<Item> items = inputFileReader.readActions();
        assertEquals(itemsTest.get(0).getResult().getCloseTime(), items.get(0).getResult().getCloseTime());
        assertEquals(itemsTest.get(1).getResult().getCloseTime(), items.get(1).getResult().getCloseTime());
    }

}