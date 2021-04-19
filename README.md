# Auction-House

This project is about auctions in which people can put items up for sale, and others can bid to buy them.
For each item (upon the auction closing) user will be given a result which includes the winning bid, the final price to be paid, and the user who has won the item as well as some basic stats about the auction.

Requirements
The input file should be located in the same directory with source code folder (src).
The input file should have exactly the format mentioned above and be strictly in-order of timestamp. (Program does not check the correctness of the format)

Input
Program asks from user to type the name of input file to Standard Input.
A pipe-delimited input text file representing the started auctions, and bids. The file will be strictly in-order of timestamp.
There are three types of rows found in this file:

A. Users listing items for sale
This appears in the format:

timestamp|user_id|action|item|reserve_price|close_time

timestamp will be an integer representing a unix epoch time and is the auction start time
user_id is an integer user id
action will be the string "SELL"
item is a unique string code for that item.
reserve_price is a decimal representing the item reserve price in the site's local currency.
close_time will be an integer representing a unix epoch time

B. Bids on items
This will appear in the format:

timestamp|user_id|action|item|bid_amount

timestamp will be an integer representing a unix epoch time and is the time of the bid
user_id is an integer user id 
action will be the string "BID"
item is a unique string code for that item.
bid_amount is a decimal representing a bid in the auction site's local currency.

C. Heartbeat messages
These messages may appear periodically in the input to ensure that auctions can be closed in the absence of bids, they take the format:

timestamp

timestamp will be an integer representing a unix epoch time.


Output
The program produce the following output as a text file in which each line represents the outcome of a completed auction.
File's name consists of input file name followed by the word "Results".
Output File is stored in the same directory with source code folder (src).

Output file is delimited with the following format:

close_time|item|user_id|status|price_paid|total_bid_count|highest_bid|lowest_bid

close_time should be a unix epoch of the time the auction finished
item is the unique string item code.
user_id is the integer id of the winning user, or blank if the item did not sell.
status should contain either "SOLD" or "UNSOLD" depending on the auction outcome.
price_paid should be the price paid by the auction winner (0.00 if the item is UNSOLD), as a
number to two decimal places
total_bid_count should be the number of valid bids received for the item.
*highest_bid the highest bid received for the item as a number to two decimal places
*lowest_bid the lowest bid placed on the item as a number to two decimal places

*These values refer to bids valid only in the terms of time.

Close Time
Close time of an item indicated in the output file is the timestamp when auction for this item finally closed.
For each item, this close time value is the highest timestamp included in the input file within valid time for this item.

Finding Winner
There are only 2 cases in which there is not winner for an item:

1. There are not bids for this item -> No Winner
2. There are not bids for this item both placed within valid time and having a valid price -> No Winner

In all other cases, for an item:

1. Winner is the one which placed the bid with the highest valid price within valid time.
2. If valid bids with highest price are more than one, the winner is the more recent bid.
3. Winner will pay the reserve price if there are not other bids valid in terms of time and price. Otherwise, they will pay the price of the second highest valid bid if it.

Assumptions about winner
1. There are not valid bids for the same item having the same price and timestamp.

Valid Bid
A valid bid has valid both price and timestamp.

Bid with Valid Price
Bid's price is considered as valid if it is meeting or in excess of the reserve price.

Bid with Valid Timestamp
Bid's timestamp is considered as valid if it arrives after the start time of the auction of the item, which bid refers to, and before or on the closing time.


Examples
Files with name format being "input{number}.txt" are some examples of input files and files with name format being "input{number}Results.txt" are their output files.

