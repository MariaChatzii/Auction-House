import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        ArrayList<Item> inputItems = new InputFileReader().readActions();
        for(Item item: inputItems) {
            item.setResult();
            item.result.printResult();
        }
    }
}
