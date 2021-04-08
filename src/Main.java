import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ArrayList<Item> itemsForSelling = new InputFileReader().readActions();
        for(Item item: itemsForSelling)
            System.out.println(item.code);

    }
}
