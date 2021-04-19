import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item item1, item2, item3, item4, item5, item6, item7;

    @BeforeEach
    public void setUp(){
        //Test item1
        item1 = new Item("bag_2", new SellAction(5, 8, (float) 50.16, 9));
        ArrayList<BidAction> bids1 = new ArrayList<>();
        bids1.add(new BidAction(7, 6, (float) 45));
        item1.setBids(bids1);
        item1.getResult().setCloseTime(9);

        //Test item2
        item2 = new Item("toaster_2", new SellAction(11, 4, (float) 25.30, 17));
        ArrayList<BidAction> bids2 = new ArrayList<>();
        bids2.add(new BidAction(12, 3, (float) 35));
        bids2.add(new BidAction(13, 1, (float) 35));
        bids2.add(new BidAction(14, 6, (float) 35));
        bids2.add(new BidAction(16, 8, (float) 20));
        item2.setBids(bids2);
        item2.getResult().setCloseTime(16);

        //Test item3
        item3 = new Item("toaster_1", new SellAction(10,1, (float) 10.00,20));
        ArrayList<BidAction> bids3 = new ArrayList<>();
        bids3.add(new BidAction(12,8,(float) 7.50));
        bids3.add(new BidAction(13,5, (float) 12.50));
        bids3.add(new BidAction(17,8, (float) 20));
        item3.setBids(bids3);
        item3.getResult().setCloseTime(20);

        //Test item4
        item4 = new Item("tv_1", new SellAction(15,8, (float) 250.00,20));
        ArrayList<BidAction> bids4 = new ArrayList<>();
        bids4.add(new BidAction(18,1,(float) 150.00));
        bids4.add(new BidAction(19,3, (float) 200.00));
        bids4.add(new BidAction(21,3, (float) 300.00));
        item4.setBids(bids4);
        item4.getResult().setCloseTime(20);

        //Test item5
        item5 = new Item("wallet_1", new SellAction(10,1, (float) 10.06,20));
        ArrayList<BidAction> bids5 = new ArrayList<>();
        bids5.add(new BidAction(13,4,15));
        bids5.add(new BidAction(16,3, (float) 9.50));
        item5.setBids(bids5);
        item5.getResult().setCloseTime(20);

        //Test item6
        item6 = new Item("bag_1", new SellAction(14,1, (float) 30.99,23));
        ArrayList<BidAction> bids6 = new ArrayList<>();
        bids6.add(new BidAction(18,3,20));
        bids6.add(new BidAction(19,5, 45));
        bids6.add(new BidAction(20,2, 45));
        bids6.add(new BidAction(21,7, 60));
        item6.setBids(bids6);
        item6.getResult().setCloseTime(22);

        //Test item7
        item7 = new Item("laptop_1", new SellAction(14,4, (float) 650,17));
        ArrayList<BidAction> bids7 = new ArrayList<>();
        bids7.add(new BidAction(16,7,(float) 730));
        bids7.add(new BidAction(17,8, (float) 730));
        item7.setBids(bids7);
        item7.getResult().setCloseTime(17);
    }

    @Test
    void removeInvalidTimeBids(){
        item4.removeInvalidTimeBids();

        ArrayList<BidAction> bidsExpected = new ArrayList<>();
        bidsExpected.add(item4.getBids().get(0));
        bidsExpected.add(item4.getBids().get(1));

        assertEquals(bidsExpected, item4.getBids());
    }

    @Test
    void testRemoveInvalidPriceBids(){
        item3.removeInvalidTimeBids();
        item4.removeInvalidTimeBids();

        //Expected results
        ArrayList<BidAction> bidsExpected = new ArrayList<>();
        bidsExpected.add(item3.getBids().get(1));
        bidsExpected.add(item3.getBids().get(2));

        item3.removeInvalidPriceBids();
        item4.removeInvalidPriceBids();

        assertEquals(bidsExpected, item3.getBids());
        assertEquals(0, item4.getBids().size());
    }

    @Test
    void getHighestBidPrice(){
        //This method works correctly only with sorted by price bids
        ascendingSortingByPrice();

        assertEquals(45, item1.getHighestBidPrice());
        assertEquals(35, item2.getHighestBidPrice());
        assertEquals(20, item3.getHighestBidPrice());
        assertEquals(300, item4.getHighestBidPrice());
        assertEquals(15, item5.getHighestBidPrice());
        assertEquals(60, item6.getHighestBidPrice());
        assertEquals(730, item7.getLowestBidPrice());
    }

    @Test
    void getLowestBidPrice(){
        //This method works correctly only with sorted by price bids
        ascendingSortingByPrice();

        assertEquals(45, item1.getLowestBidPrice());
        assertEquals(20, item2.getLowestBidPrice());
        assertEquals(7.50, item3.getLowestBidPrice());
        assertEquals(150, item4.getLowestBidPrice());
        assertEquals(9.50, item5.getLowestBidPrice());
        assertEquals(20, item6.getLowestBidPrice());
        assertEquals(730, item7.getLowestBidPrice());
    }

    @Test
    void isWithinValidTime(){
        //Testing for bids of item4
        assertTrue(Item.isWithinValidTime(item4.getBids().get(0).getTimestamp(), item4.getSellingData().getTimestamp(), item4.getSellingData().getCloseTime()));
        assertTrue(Item.isWithinValidTime(item4.getBids().get(1).getTimestamp(), item4.getSellingData().getTimestamp(), item4.getSellingData().getCloseTime()));
        assertFalse(Item.isWithinValidTime(item4.getBids().get(2).getTimestamp(), item4.getSellingData().getTimestamp(), item4.getSellingData().getCloseTime()));
    }

    @Test
    void totalBidsWithSameHighestPrice(){
        item6.getBids().sort(BidAction.sortByPrice);
        item2.getBids().sort(BidAction.sortByPrice);

        item6.removeInvalidTimeBids();
        item2.removeInvalidTimeBids();

        item6.removeInvalidPriceBids();
        item2.removeInvalidPriceBids();

        assertEquals(1, item6.totalBidsWithSameHighestPrice());
        assertEquals(3, item2.totalBidsWithSameHighestPrice());
    }

    @Test
    void testGetPaidPrice(){
        //This method works correctly only with sorted by price bids
        ascendingSortingByPrice();

        item3.removeInvalidTimeBids();
        item6.removeInvalidTimeBids();
        item2.removeInvalidTimeBids();
        item7.removeInvalidTimeBids();

        item3.removeInvalidPriceBids();
        item6.removeInvalidPriceBids();
        item2.removeInvalidPriceBids();
        item7.removeInvalidPriceBids();

        //only for sold items with more than 1 valid bids
        assertEquals(12.50, item3.getPaidPrice());
        assertEquals(45, item6.getPaidPrice());
        assertEquals(35, item2.getPaidPrice());
        assertEquals(730, item7.getPaidPrice());
    }

    @Test
    void setWinnerData(){
        //winner is between valid bids only
        item6.getBids().sort(BidAction.sortByPrice);
        item6.removeInvalidTimeBids();
        item6.removeInvalidPriceBids();

        Result result1 = item6.getResult();
        item6.setWinnerData();

        assertEquals(7, result1.getWinnerId());
        assertEquals("SOLD", result1.getStatus());

        //winner is between valid bids only
        item5.getBids().sort(BidAction.sortByPrice);
        item5.removeInvalidTimeBids();
        item5.removeInvalidPriceBids();

        Result result2 = item5.getResult();
        item5.setWinnerData();

        assertEquals(4, result2.getWinnerId());
        assertEquals("SOLD", result2.getStatus());
    }

    @Test
    void setNoWinnerData(){
        item1.setNoWinnerData();
        checkResultDataEquals(item1, -1, "UNSOLD", 0, 9);
    }

    @Test
    void setResultData(){
        //Case1
        //There is only one bid with valid time and valid price
        item5.removeInvalidTimeBids();
        item5.removeInvalidPriceBids();

        item5.setResultData();
        checkResultDataEquals(item5, 4, "SOLD", (float) 10.06, 17);

        //Case2
        //There are more than one bid with valid time and valid price
        //Test1
        item6.removeInvalidTimeBids();
        item6.removeInvalidPriceBids();

        item6.setResultData();
        checkResultDataEquals(item6, 7, "SOLD", 45, 22);

        //Test2
        item7.removeInvalidTimeBids();
        item7.removeInvalidPriceBids();

        item7.setResultData();
        checkResultDataEquals(item7, 8, "SOLD", 730, 17);

        //Case3
        //There are not bids within valid time
        Item itemTemp = new Item("tempCode_1", new SellAction(2, 6, (float) 10.50, 3));

        itemTemp.setBids(new ArrayList<>());
        itemTemp.removeInvalidTimeBids();

        itemTemp.setResultData();
        checkResultDataEquals(itemTemp, -1, "UNSOLD", 0, 3);
    }

    @Test
    void setResult(){
        //Case1
        //Test1
        item7.setResult();
        assertEquals("laptop_1", item7.getResult().getItemCode());
        assertEquals(730, item7.getResult().getLowestBid());
        assertEquals(730, item7.getResult().getHighestBid());
        checkResultDataEquals(item7, 8,"SOLD", 730, 17);

        //Test2
        item2.setResult();
        assertEquals("toaster_2", item2.getResult().getItemCode());
        assertEquals(20, item2.getResult().getLowestBid());
        assertEquals(35, item2.getResult().getHighestBid());
        checkResultDataEquals(item2, 6,"SOLD", 35, 15);

        //Test3
        item5.setResult();
        assertEquals("wallet_1", item5.getResult().getItemCode());
        assertEquals(9.50, item5.getResult().getLowestBid());
        assertEquals(15, item5.getResult().getHighestBid());
        checkResultDataEquals(item5, 4,"SOLD", (float) 10.06, 17);

        //Case2
        //There are not (neither valid nor invalid) bids for this item
        SellAction sellingDataTemp = new SellAction(6,2,150,15);
        Item itemTemp = new Item("tv_03", sellingDataTemp);
        itemTemp.setBids(new ArrayList<>());

        itemTemp.setResult();

        assertEquals("tv_03", itemTemp.getResult().getItemCode());
        assertEquals(0, itemTemp.getResult().getLowestBid());
        assertEquals(0, itemTemp.getResult().getHighestBid());
        checkResultDataEquals(itemTemp, -1,"UNSOLD", 0, 18);

    }

    void checkResultDataEquals(Item item, int expectedWinnerId, String expectedStatus, float expectedPrice, int expectedCloseTime){
        assertEquals(expectedWinnerId, item.getResult().getWinnerId());
        assertEquals(expectedStatus, item.getResult().getStatus());
        assertEquals(expectedPrice, item.getResult().getPricePaid());
        assertEquals(expectedCloseTime, item.getResult().getCloseTime());
    }

    void ascendingSortingByPrice(){
        item1.getBids().sort(BidAction.sortByPrice);
        item2.getBids().sort(BidAction.sortByPrice);
        item3.getBids().sort(BidAction.sortByPrice);
        item4.getBids().sort(BidAction.sortByPrice);
        item5.getBids().sort(BidAction.sortByPrice);
        item6.getBids().sort(BidAction.sortByPrice);
        item7.getBids().sort(BidAction.sortByPrice);
    }
}