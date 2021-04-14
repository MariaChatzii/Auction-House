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
        assertEquals(180, item1.getPaidPrice());

        bids2.add(new BidAction(10,8, 200));

        item1.setBids(bids2);
        assertEquals(180, item1.getPaidPrice());

        ArrayList<BidAction> bids5 = new ArrayList<>();
        bids5.add(new BidAction(8,3, 100));
        bids5.add(new BidAction(8,7,100));
        bids5.add(new BidAction(9, 5,100));
        bids5.add(new BidAction(7,3, 100));

        item1.setBids(bids5);
        assertEquals(100, item1.getPaidPrice());
    }

    @Test
    void setWinnerData(){
        Result result1 = item1.getResult();

        item1.setWinnerData(2);
        assertEquals(5, result1.getWinnerId());
        assertEquals("SOLD", result1.getStatus());
        assertEquals(11, result1.getCloseTime());

        item1.setHeartBeatMessage(18);
        item1.setWinnerData(2);
        assertEquals(18, result1.getCloseTime());
    }

    @Test
    void setNoWinnerData(){
        item1.setNoWinnerData();
        checkResultEquals(item1, -1, "UNSOLD", 0, 0);
    }

    @Test
    void setResultData(){
        ArrayList<BidAction> bidsTemp = new ArrayList<>();
        bidsTemp.add(new BidAction(16,3, 100));
        bidsTemp.add(new BidAction(12,9, 180));
        bidsTemp.add(new BidAction(10,7,300));
        bidsTemp.add(new BidAction(8, 5,420));

        SellAction sellingDataTemp = new SellAction(7,4,200,19);
        Item itemTemp = new Item("tv_02", sellingDataTemp);
        itemTemp.setBids(bidsTemp);

        //Case1
        //There are more than one bid within valid time
        //and almost one of them has also valid price
        itemTemp.setResultData();

        assertEquals(5, itemTemp.getResult().getWinnerId());
        assertEquals(300, itemTemp.getResult().getPricePaid());
        assertEquals("SOLD", itemTemp.getResult().getStatus());
        assertEquals(8, itemTemp.getResult().getCloseTime());

        //Case2
        //There are more than one bid within valid time
        //but no one of them has valid price
        itemTemp.getBids().remove(itemTemp.getBids().size()-1);
        itemTemp.getBids().remove(itemTemp.getBids().size()-1);

        itemTemp.setResultData();
        checkResultEquals(itemTemp, -1, "UNSOLD", 0, 0);

        //Case3
        //There is only a bid within valid time
        //which does not have valid price
        itemTemp.getBids().remove(itemTemp.getBids().size()-1);

        itemTemp.setResultData();
        checkResultEquals(itemTemp, -1, "UNSOLD", 0, 0);

        //Case4
        //There are not bids within valid time
        itemTemp.getBids().remove(itemTemp.getBids().size()-1);

        itemTemp.setResultData();
        checkResultEquals(itemTemp, -1, "UNSOLD", 0, 0);

        //Case5
        //There is only one bid within valid time
        //which has also valid price
        itemTemp.getBids().add(new BidAction(13,1, 350));

        itemTemp.setResultData();
        checkResultEquals(itemTemp, 1, "SOLD", 200, 13);
    }

    @Test
    void setResult(){

    }

    void checkResultEquals(Item item, int expectedWinnerId, String expectedStatus, float expectedPrice, int expectedCloseTime){
        assertEquals(expectedWinnerId, item.getResult().getWinnerId());
        assertEquals(expectedStatus, item.getResult().getStatus());
        assertEquals(expectedPrice, item.getResult().getPricePaid());
        assertEquals(expectedCloseTime, item.getResult().getCloseTime());
    }
}