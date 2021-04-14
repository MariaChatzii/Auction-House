import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private final Item item1, item2;

    public ItemTest(){
        ArrayList<BidAction> bids1 = new ArrayList<>();
        bids1.add(new BidAction(8,3, 3));
        bids1.add(new BidAction(9,7,5));
        bids1.add(new BidAction(11, 5,10));

        SellAction sellingData1 = new SellAction(2,4,15,19);
        item1 = new Item("radio_01", sellingData1);
        item1.setBids(bids1);

        SellAction sellingData2 = new SellAction(4,4,100,10);
        item2 = new Item("tv_01", sellingData2);
    }

    @Test
    void getHighestBidPrice(){
        assertEquals(10, item1.getHighestBidPrice());
    }

    @Test
    void getLowestBidPrice(){
        assertEquals(3, item1.getLowestBidPrice());
    }

    @Test
    void isWithinValidTime(){
        assertFalse(Item.isWithinValidTime(7, 10, 12));
        assertFalse(Item.isWithinValidTime(10, 10, 12));
        assertFalse(Item.isWithinValidTime(15, 10, 12));
        assertTrue(Item.isWithinValidTime(12, 10, 12));
        assertTrue(Item.isWithinValidTime(11, 10, 12));
    }

    @Test
    void removeInvalidTimeBids(){
        ArrayList<BidAction> bids4 = new ArrayList<>();
        BidAction bid1 = new BidAction(19,6, 70);
        BidAction bid2 = new BidAction(1,6,90);
        BidAction bid3 = new BidAction(20, 5,100);
        BidAction bid4 = new BidAction(15, 5,100);

        bids4.add(bid1);
        bids4.add(bid2);
        bids4.add(bid3);
        bids4.add(bid4);

        item1.setBids(bids4);

        ArrayList<BidAction> bidsTrue = new ArrayList<>();
        bidsTrue.add(bid1);
        bidsTrue.add(bid4);

        item1.removeInvalidTimeBids();
        assertEquals(bidsTrue, item1.getBids());
    }

    @Test
    void totalBidsWithSameHighestPrice(){
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(8,3, 124));
        bids2.add(new BidAction(8,7,130));
        bids2.add(new BidAction(9, 5,200));
        bids2.add(new BidAction(10,3, 200));

        item2.setBids(bids2);
        assertEquals(2,item2.totalBidsWithSameHighestPrice());

        ArrayList<BidAction> bids3 = new ArrayList<>();
        bids3.add(new BidAction(1,3, 30));
        bids3.add(new BidAction(2,7,50));
        bids3.add(new BidAction(5, 5,100));
        bids3.add(new BidAction(10,3, 600));

        item2.setBids(bids3);
        assertEquals(1,item2.totalBidsWithSameHighestPrice());
    }

    @Test
    void getMaxBidPrice(){
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(8,3, 100));
        bids2.add(new BidAction(8,7,150));
        bids2.add(new BidAction(9, 5,180));
        bids2.add(new BidAction(7,3, 200));

        item1.setBids(bids2);
        assertEquals(180, item1.getMaxBidPrice());

        bids2.add(new BidAction(10,8, 200));

        item1.setBids(bids2);
        assertEquals(180, item1.getMaxBidPrice());

        ArrayList<BidAction> bids5 = new ArrayList<>();
        bids5.add(new BidAction(8,3, 100));
        bids5.add(new BidAction(8,7,100));
        bids5.add(new BidAction(9, 5,100));
        bids5.add(new BidAction(7,3, 100));

        item1.setBids(bids5);
        assertEquals(100, item1.getMaxBidPrice());
    }

    @Test
    void setResultData(){
        Result result1 = item1.getResult();
        item1.setResultData();

        assertEquals(-1, result1.getWinnerId());
        assertEquals("UNSOLD", result1.getStatus());
        assertEquals(0.00, result1.getPricePaid());
        assertEquals(0, result1.getCloseTime());
    }

    @Test
    void setWinnerData(){

    }

    @Test
    void setNoWinnerData(){

    }

    @Test
    void setResult(){

    }
}