public class Result {
    private int closeTime;
    private String itemCode;
    private int winnerId;
    private String status;
    private float pricePaid;
    private int totalBidCount;
    private float lowestBid;
    private float highestBid;

    public int getCloseTime() {
        return closeTime;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public String getStatus() {
        return status;
    }

    public float getPricePaid() {
        return pricePaid;
    }

    public int getTotalBidCount() {
        return totalBidCount;
    }

    public float getLowestBid() {
        return lowestBid;
    }

    public float getHighestBid() {
        return highestBid;
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