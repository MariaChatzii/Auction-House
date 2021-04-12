public class Result {
    private int closeTime;
    private String itemCode;
    private int winnerId;
    private String status;
    private float pricePaid;
    private int totalBidCount;
    private float lowestBid;
    private float highestBid;

    public void printResult(){
        System.out.println("close_time: " + closeTime + "\nitem_code: " + itemCode + "\nwinner_id: " + winnerId + "\nstatus: " + status
                   + "\nprice_paid: " + pricePaid + "\ntotal_bid_count:" + totalBidCount + "\nlowest_bid: " + lowestBid + "\nhighest_bid: "
                   + highestBid);
    }

    public float getPricePaid() {
        return pricePaid;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalBidCount(int totalBidCount) {
        this.totalBidCount = totalBidCount;
    }

    public void setLowestBid(float lowestBid) {
        this.lowestBid = lowestBid;
    }

    public void setHighestBid(float highestBid) {
        this.highestBid = highestBid;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setPricePaid(float pricePaid) {
        this.pricePaid = pricePaid;
    }

}